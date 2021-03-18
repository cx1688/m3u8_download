package com.dfm.main;

import com.dfm.beans.M3u8Info;
import com.dfm.beans.ParamInfo;
import com.dfm.beans.SegmentFileInfo;
import com.dfm.utils.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Vector;
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
    private String tempPath = "temp";
    private String dataPath = "data";

    private ParamInfo paramInfo;
    private List<SegmentFileInfo> segmentFileInfos;
    private M3u8Info m3u8Info;
    private DefaultTableModel model;
    private Vector<Vector<String>> tableData;
    private Vector<String> coulm;
    private List<ParamInfo> dataList;
    private JTextArea textArea;

    public Download(ParamInfo paramInfo, DefaultTableModel model, Vector<Vector<String>> tableData, Vector<String> coulm, List<ParamInfo> dataList, JTextArea textArea) {
        this.paramInfo = paramInfo;
        this.model = model;
        this.tableData = tableData;
        this.coulm = coulm;
        this.dataList = dataList;
        this.textArea = textArea;
        init();
    }

    private void check(ParamInfo paramInfo) {
        if (paramInfo != null) {
            if (StringUtils.isBlank(paramInfo.getUrl())) {
                JOptionPane.showMessageDialog(null, "url不可为空");
            }
            if (StringUtils.isBlank(paramInfo.getName())) {
                JOptionPane.showMessageDialog(null, "name不可为空");
            }
            if (StringUtils.isBlank(paramInfo.getPath())) {
                JOptionPane.showMessageDialog(null, "path不可为空");
            }
            if (paramInfo.getTryNum() <= 0) {
                paramInfo.setTryNum(255);
            }
        } else {
            JOptionPane.showMessageDialog(null, "参数不可为空");
        }

    }

    private void init() {
        if (paramInfo != null) {
            try {
                String content = JsonUtils.parseJsonString(dataList);
                File file = new File("./data.json");
                if (!file.exists())
                    file.createNewFile();
                Files.write(file.toPath(), content.getBytes(Charset.forName("UTF-8")), StandardOpenOption.WRITE);
            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            paramInfo.setTaskStatus(1);
            //创建分段文件下载线程池
            if (paramInfo.getCore() <= 0) {
                threadPoolExecutor = new ThreadPoolExecutor(32, 32, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFacotryImpl("segmentationTask", new ThreadGroup("segmentationTask")));
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
        textArea.append("开始任务：" + paramInfo.getName() + "\n");
        startTask();
    }

    /**
     * 開始任務
     */
    private void startTask() {
        log.info("需要下载的分段文件：{}个;", segmentFileInfos.size());
        segmentFileInfos.stream().filter(t -> t.isDownload()).forEach(t -> count++);
        //当下载完成数不等于所需下载数时，继续下载
        if (count != segmentFileInfos.size()) {
            segmentFileInfos.stream().filter(t -> !t.isDownload()).forEach(t -> threadPoolExecutor.execute(() -> downAndTry(m3u8Info.getBaseUrl(), t)));
        }
        log.info("下载完成：" + count + "/" + segmentFileInfos.size());
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
//                textArea.append("读取保存的信息：" + m3u8Info + "\n");
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
     *
     * @param paramInfo
     * @param segmentFileInfos
     */
    public void closeTask(ParamInfo paramInfo, List<SegmentFileInfo> segmentFileInfos) {
        while (true) {
            try {
                if (count == segmentFileInfos.size()) {
                    threadPoolExecutor.shutdown();
                    File source = new File(tempPath + File.separator + paramInfo.getName() + File.separator);
                    File target = new File(paramInfo.getPath() + File.separator + paramInfo.getName() + ".mp4");
                    if (segmentFileInfos.size() == source.listFiles().length) {
                        //合并文件
                        textArea.append("**********************开始合并文件**********************\n");
                        MergeUtils.getINSTANCE().merge(source, target, false, true);
                        textArea.append("**********************合 并 完 成**********************\n");
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
     *
     * @param baseUrl
     * @param segmentFileInfo
     * @return
     * @throws Exception
     */
    private byte[] getBytes(String baseUrl, SegmentFileInfo segmentFileInfo) throws Exception {
        byte[] bytes = HttpUtils.getBytes(resolve.repleaceUrl(baseUrl + segmentFileInfo.getUrl()));
        if (StringUtils.isNotBlank(paramInfo.getKey()) && "QINIU-PROTECTION-10".equals(segmentFileInfo.getMethod())) {
            bytes = AESUtils.decrypt(bytes, AESUtils.loadSecretKey(paramInfo.getKey()), segmentFileInfo.getIv());
        } else if (segmentFileInfo.getKey() != null && segmentFileInfo.getKey().length > 0 && "AES-128".equals(segmentFileInfo.getMethod())) {
            bytes = AESUtils.decode(segmentFileInfo.getKey(), bytes);
        }
        return bytes;
    }

    /**
     * 下载与重试
     *
     * @param baseUrl
     * @param segmentFileInfo
     */
    private int count = 0;

    private void downAndTry(String baseUrl, SegmentFileInfo segmentFileInfo) {
        try {

            byte[] bytes = getBytes(baseUrl, segmentFileInfo);
            if (bytes != null) {
//                textArea.setText("");
//                textArea.append("缓存路径：" + tempPath + File.separator + paramInfo.getName() + File.separator + resolve.customFileNameFromIndex(segmentFileInfos.indexOf(segmentFileInfo)) + ".ts\n");
                segmentFileInfo.setDownload(resolve.writeFileAsTs(bytes, tempPath + File.separator + paramInfo.getName() + File.separator + resolve.customFileNameFromIndex(segmentFileInfos.indexOf(segmentFileInfo)) + ".ts"));
//                textArea.append("下载完成:" + resolve.repleaceUrl(baseUrl + segmentFileInfo.getUrl()) + "\n");
                count++;
                synchronized (this) {
                    tableData.stream().forEach(t -> {

                        if (paramInfo.getName().equals(t.get(2))) {
                            model.getValueAt(tableData.indexOf(t),4);
                            model.setValueAt((count / segmentFileInfos.size()) + "%", tableData.indexOf(t), 4);
                        }

                    });

                    textArea.setText("任务名称：" + paramInfo.getName() + "\n下载进度：" + count + "/" + segmentFileInfos.size());
                }
                textArea.setCaretPosition(textArea.getText().length());

                resolve.writeString(JsonUtils.parseJsonString(m3u8Info), dataPath + File.separator + paramInfo.getName() + ".json");
            } else if (segmentFileInfo.getTryCount() >= paramInfo.getTryNum()) {
                //重试次数用完关闭当前线程池
                log.info("任務已結束", paramInfo.getName());
                threadPoolExecutor.shutdown();
            }

        } catch (Exception e) {
//            e.printStackTrace();
            log.error("重试失败原因：{}", e.getMessage());
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
