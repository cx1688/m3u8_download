package com.dfm.main;

import com.dfm.beans.ParamInfo;
import com.dfm.listener.DownListener;
import com.dfm.utils.JsonUtils;
import com.dfm.utils.ThreadFacotryImpl;
import javafx.util.Builder;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * @program: m3u8_project
 * @description:
 * @author: Mr.D
 * @create: 2020-12-24 16:58
 */
public class App {
    //    private static final String KEY = "0x4A, 0x4b, 0x36, 0x6D, 0x54, 0x4C, 0x64, 0x74, 0x47, 0x63, 0x78, 0x38, 0x6D, 0x62, 0x71, 0x67";
//    private static final String KEY = "74, 75, 54, 109, 84, 76, 100, 116, 71, 99, 120, 56, 109, 98, 113, 103";
    private static final String KEY_26 = "102, 97, 51, 54, 52, 55, 52, 49, 49, 50, 53, 52, 50, 99, 98, 56";
    private static final String KEY_30 = "100, 97, 97, 101, 98, 51, 51, 55, 49, 99, 56, 51, 56, 97, 56, 101";
    private static final String KEY_31 = "50, 101, 102, 102, 99, 100, 50, 49, 53, 98, 55, 49, 51, 102, 52, 53";
    private static final String KEY_32 = "54, 102, 49, 48, 100, 101, 51, 54, 49, 51, 99, 57, 54, 57, 50, 54";
    private static final String KEY_33 = "50, 54, 48, 50, 52, 50, 55, 53, 98, 102, 52, 102, 97, 99, 56, 48";
    private static final String KEY_34 = "97, 55, 98, 50, 97, 51, 55, 55, 102, 97, 53, 55, 102, 98, 50, 49";

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFacotryImpl("taskName", new ThreadGroup("task")));


    public static void main(String[] args) throws IOException {
//        System.out.println(hexToStr(KEY_31));
//        System.out.println(hexToStr(KEY_32));
//        System.out.println(hexToStr(KEY_33));
//        System.out.println(hexToStr(KEY_34));
        File file = new File("2.json");
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        StringBuilder sb = new StringBuilder();
        bufferedReader.lines().forEach(sb::append);
        String content = sb.toString().replaceAll(" ", "");
        List<ParamInfo> paramInfos = JsonUtils.parseJsonList(content, LinkedList.class, ParamInfo.class);
        paramInfos.stream().forEach(paramInfo -> {
          threadPoolExecutor.execute(()->{
//              paramInfo.setKey(hexToStr(KEY_30));
              Download download = new Download(paramInfo);
              download.start();
          });
        });

        while (true) {
            if (threadPoolExecutor.getCompletedTaskCount() == paramInfos.size()) {
                threadPoolExecutor.shutdown();
                break;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
