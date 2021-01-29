package com.dfm.main;

import com.dfm.beans.ParamInfo;
import com.dfm.form.M3u8DownloadTool;
import com.dfm.utils.JsonUtils;
import com.dfm.utils.ThreadFacotryImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: m3u8_project
 * @description:
 * @author: Mr.D
 * @create: 2020-12-24 16:58
 */
public class App {
    private static ThreadPoolExecutor threadPoolExecutor;


    public static void main(String[] args) throws IOException {
        M3u8DownloadTool m3u8DownloadTool = new M3u8DownloadTool();
        m3u8DownloadTool.setVisible(true);
        m3u8DownloadTool.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        File file = new File("2.json");
//        FileInputStream fileInputStream = new FileInputStream(file);
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
//        StringBuilder sb = new StringBuilder();
//        bufferedReader.lines().forEach(sb::append);
//        String content = sb.toString().replaceAll(" ", "");
//        List<ParamInfo> paramInfos = JsonUtils.parseJsonList(content, LinkedList.class, ParamInfo.class);
//        paramInfos.stream().forEach(paramInfo -> {
//          threadPoolExecutor.execute(()->{
//              Download download = new Download(paramInfo);
//              download.start();
//          });
//        });
//
//        while (true) {
//            if (threadPoolExecutor.getCompletedTaskCount() == paramInfos.size()) {
//                threadPoolExecutor.shutdown();
//                break;
//            }
//            try {
//                TimeUnit.MILLISECONDS.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public static void start(String content) throws JsonProcessingException {
        content = content.replace(" ","");
        if (threadPoolExecutor == null || threadPoolExecutor.isShutdown()) {
            threadPoolExecutor = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFacotryImpl("taskName", new ThreadGroup("task")));
            M3u8DownloadTool.state = 1;
        }
        List<ParamInfo> paramInfos = JsonUtils.parseJsonList(content, LinkedList.class, ParamInfo.class);
        try {
            paramInfos.stream().forEach(paramInfo -> {
                threadPoolExecutor.execute(() -> {
                    Download download = new Download(paramInfo);
                    download.start();
                });
            });

            while (true) {
                if (threadPoolExecutor.getCompletedTaskCount() == paramInfos.size()) {
                    threadPoolExecutor.shutdown();
                    M3u8DownloadTool.state = 0;
                    break;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            M3u8DownloadTool.state = 0;
        }
    }

    static String hexToStr(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        boolean flag = false;
        if (key.indexOf("0x") != -1 || key.indexOf("0X") != -1) {
            key = key.replace("0x", "").replace("0X", "");
            flag = true;
        }
        String[] bys = key.split(",");
        byte[] bytes = new byte[bys.length];
        int a = 0;
        for (int i = 0; i < bys.length; i++) {
            if (flag) {
                a = Integer.parseInt(bys[i].trim(), 16);
            } else {
                a = Integer.parseInt(bys[i].trim());
            }
            bytes[i] = (byte) a;
        }
        return new String(bytes);
    }
}
