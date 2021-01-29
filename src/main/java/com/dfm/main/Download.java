package com.dfm.main;

import com.dfm.beans.M3u8Info;
import com.dfm.beans.ParamInfo;
import com.dfm.beans.SegmentFileInfo;
import com.dfm.utils.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: m3u8_project
 * @description:
 * @author: Mr.D
 * @create: 2020-12-24 01:48
 */
@Slf4j
public class Download {
    private ThreadPoolExecutor threadPoolExecutor;
    private Resolve resolve = ResolveM3u8.getINSTANCE();
    //下载缓存目录
    private String tempPath = "/media/bluesky/Share/TuringTutorials/temp";
    private String dataPath = "data";

    private ParamInfo paramInfo;
    private List<SegmentFileInfo> segmentFileInfos;
    private M3u8Info m3u8Info;

    public Download(ParamInfo paramInfo) {
        this.paramInfo = paramInfo;
        init();
    }

    private void init() {
        if (paramInfo != null) {
            //创建分段文件下载线程池
            if (paramInfo.getCore() <= 0) {
                threadPoolExecutor = new ThreadPoolExecutor(8, 8, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFacotryImpl("segmentationTask", new ThreadGroup("segmentationTask")));
            } else {
                threadPoolExecutor = new ThreadPoolExecutor(paramInfo.getCore(), paramInfo.getCore(), 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFacotryImpl("segmentationTask", new ThreadGroup("segmentationTask")));
            }
            //读取缓存的序列化文件
            m3u8Info = getM3u8Info();
            //获取分段文件信息
            segmentFileInfos = m3u8Info.getSegmentFileInfos();
            return;
        }
        throw new NullPointerException("ParamInfo is null,init fail");
    }

    public void start() {
        log.info("开始任务：{}", paramInfo);
        startTask();
    }

    /**
     * 開始任務
     */
    private void startTask() {
        log.info("需要下载的分段文件：{}个;", segmentFileInfos.size());
        segmentFileInfos.stream().filter(t -> !t.isDownload()).forEach(t -> threadPoolExecutor.execute(() -> downAndTry(m3u8Info.getBaseUrl(), t)));
        closeTask(paramInfo, segmentFileInfos);
    }

    /**
     * 解析M3u8
     *
     * @return
     */
    private M3u8Info getM3u8Info() {
        File data = new File(dataPath + File.separator + paramInfo.getName() + ".json");
        if (!data.exists()) {
            data.getParentFile().mkdirs();
        } else {
            String jsonStr = null;
            try {
                jsonStr = FileUtil.readStrByFile(data.getAbsolutePath());
                m3u8Info = JsonUtils.readJson(jsonStr, M3u8Info.class);
                log.info("读取保存的信息：{}", m3u8Info);
            } catch (FileNotFoundException | JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        //解析
        if (m3u8Info == null) {
            m3u8Info = this.resolve.resolveByCommon(paramInfo.getUrl());
            log.info("解析的信息：{}", m3u8Info);
        }
        return m3u8Info;
    }

    /**
     * 关闭任务
     * @param paramInfo
     * @param segmentFileInfos
     */
    public void closeTask(ParamInfo paramInfo, List<SegmentFileInfo> segmentFileInfos) {
        while (true) {
            try {
                if (threadPoolExecutor.getCompletedTaskCount() == segmentFileInfos.size()) {
                    threadPoolExecutor.shutdown();
                    File source = new File(tempPath + File.separator + paramInfo.getName() + File.separator);
                    File target = new File(paramInfo.getPath() + File.separator + paramInfo.getName() + ".mp4");
                    if (segmentFileInfos.size() == source.listFiles().length) {
                        //合并文件
                        MergeUtils.getINSTANCE().merge(source, target, true, true);
                        break;
                    }
                }
                TimeUnit.MILLISECONDS.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取字节流
     * @param baseUrl
     * @param segmentFileInfo
     * @return
     * @throws Exception
     */
    private byte[] getBytes(String baseUrl,SegmentFileInfo segmentFileInfo) throws Exception {
        byte[] bytes = HttpUtils.getBytes(resolve.repleaceUrl(baseUrl + segmentFileInfo.getUrl()));
        if (StringUtils.isNotBlank(paramInfo.getKey()) && "QINIU-PROTECTION-10".equals(segmentFileInfo.getMethod())) {
            bytes = AESUtils.decrypt(bytes, AESUtils.loadSecretKey(paramInfo.getKey()), segmentFileInfo.getIv());
        } else if (StringUtils.isNotBlank(segmentFileInfo.getKey()) && "AES-128".equals(segmentFileInfo.getMethod())) {
            bytes = AESUtils.decode(segmentFileInfo.getKey(), bytes);
        }
        return bytes;
    }

    /**
     * 下载与重试
     * @param baseUrl
     * @param segmentFileInfo
     */
    private void downAndTry(String baseUrl, SegmentFileInfo segmentFileInfo) {
        try {
            byte[] bytes = getBytes(baseUrl,segmentFileInfo);
            if (bytes != null) {
                log.info("缓存路径：{}", tempPath + File.separator + paramInfo.getName() + File.separator + resolve.customFileNameFromIndex(segmentFileInfos.indexOf(segmentFileInfo)) + ".mp4");
                segmentFileInfo.setDownload(resolve.writeFileAsTs(bytes,tempPath + File.separator + paramInfo.getName() + File.separator,resolve.customFileNameFromIndex(segmentFileInfos.indexOf(segmentFileInfo)) + ".mp4"));
                log.info("下载完成：{}", resolve.repleaceUrl(baseUrl + segmentFileInfo.getUrl()));
                resolve.writeString(JsonUtils.parseJsonString(m3u8Info), dataPath + File.separator + paramInfo.getName() + ".json");
            } else if (segmentFileInfo.getTryCount() >= paramInfo.getTryNum()) {
                //重试次数用完关闭当前线程池
                log.info("任務已結束", paramInfo.getName());
                threadPoolExecutor.shutdown();
            }

        } catch (Exception e) {
            if (paramInfo.getTryNum() > segmentFileInfo.getTryCount()) {
                segmentFileInfo.setTryCount(segmentFileInfo.getTryCount() + 1);
                downAndTry(baseUrl, segmentFileInfo);
                log.error("重试url:{},次数{},boolean:{}", resolve.repleaceUrl(baseUrl + segmentFileInfo.getUrl()), segmentFileInfo.getTryCount(), paramInfo.getTryNum() > segmentFileInfo.getTryCount());
                try {
                    TimeUnit.MILLISECONDS.sleep(3000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            } else {
                Thread.currentThread().interrupt();
            }
        }
    }

}
