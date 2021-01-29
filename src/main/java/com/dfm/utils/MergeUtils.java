package com.dfm.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

/**
 * @program: test-demo
 * @description:
 * @author: Mr.D
 * @create: 2020-12-25 09:57
 */
public class MergeUtils {
    private static final MergeUtils INSTANCE = new MergeUtils();

    public MergeUtils() {
    }

    public static MergeUtils getINSTANCE() {
        return INSTANCE;
    }

    public static void main(String[] args) {
//        new MergeUtils().merge(new File("/home/bluesky/IdeaProjects/m3u8_project/temp/01-VIP-Redis核心数据结构与高性能原理"), new File("/home/bluesky/Download/01-VIP-Redis核心数据结构与高性能原理.mp4"), false);
        //file:///media/bluesky/Share/null
        File file = new File("/media/bluesky/Share/null");
        File[] files = file.listFiles();
        for (File file1 : files) {
            System.out.println(file1.getName());
        }
    }

    /**
     * @param tempDir
     * @param savePath
     * @param isDeleteTemp
     * @return
     */
    public synchronized boolean merge(File tempDir, File savePath, boolean isDeleteTemp,boolean isCodec) {
        if (tempDir != null && savePath != null)
            if (tempDir.exists()) {
                File[] files = tempDir.listFiles((dir, name) -> name.contains(".obj") ? false : true);
                Arrays.sort(files, (o1, o2) -> {
                            String name1 = o1.getName();
                            String name2 = o2.getName();
                            int n1 = Integer.valueOf(name1.substring(0, name1.lastIndexOf(".")));
                            int n2 = Integer.valueOf(name2.substring(0, name1.lastIndexOf(".")));
                            return n1 - n2;
                        }
                );
                /**
                 * 创建文件上级目录LQZT#012111326414 1065899266
                 */
                if (!savePath.getParentFile().exists())
                    savePath.getParentFile().mkdirs();
                /**
                 * 如果目标文件存在，先删除
                 */
                if (files.length > 0) {
                    if (savePath.exists() && savePath.isFile() && (tempDir.listFiles() != null && tempDir.listFiles().length > 0))
                        savePath.delete();
                    try {
                        if (!savePath.exists())
                            savePath.createNewFile();
                        for (File file : files) {
                            Files.write(savePath.toPath(), Files.readAllBytes(file.toPath()), StandardOpenOption.APPEND);
                            //是否删除缓存文件
                            if (isDeleteTemp)
                                file.delete();

                        }
//                        File file = new File(savePath.getParent() + File.separator + savePath.getName().substring(0, savePath.getName().lastIndexOf(".")) + "_output" + savePath.getName().substring(savePath.getName().lastIndexOf(".")));
//                        if(isCodec)
//                            EncodeVideo.encodeCopy(savePath,file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        return true;
    }
}
