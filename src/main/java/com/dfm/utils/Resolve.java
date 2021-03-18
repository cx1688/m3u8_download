package com.dfm.utils;

import com.dfm.beans.M3u8Info;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

public interface Resolve {
    default String valueOf(String url, boolean boolValue) {
        if (StringUtils.isBlank(url)) {
            throw new RuntimeException("url is null!");
        }
        String prefix = url.substring(0, url.indexOf("//") + 2);
        String suffix = url.substring(url.indexOf("//") + 2);
        if (boolValue) {
            return prefix + suffix.substring(0, suffix.indexOf("/") + 1);
        }
        return prefix + suffix.substring(0, suffix.lastIndexOf("/") + 1);
    }

    default <T> void writeObject(T t, String path) throws IOException {
        File file = new File(path);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(t);
        oos.flush();
        oos.close();
    }

    default void writeString(String t, String path) throws IOException {
        File file = new File(path);
        FileOutputStream oos = new FileOutputStream(file);
        oos.write(t.getBytes(StandardCharsets.UTF_8));
        oos.flush();
        oos.close();
    }

    default <T> T readObject(String path) {
        ObjectInputStream ois = null;
        try {
            File file = new File(path);
            ois = new ObjectInputStream(new FileInputStream(file));
            return (T) ois.readObject();
        } catch (Exception e) {
            return null;
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 写出文件并
     * @param bytes
     * @param tempPath
     * @return
     */
    default boolean writeFileAsTs(byte[] bytes, String tempPath) {
        File file = new File(tempPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            if(!file.exists()) {
                Files.write(file.toPath(), bytes, StandardOpenOption.CREATE_NEW);
            }
            Files.write(file.toPath(), bytes, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.exists() ? file.isFile() : file.exists();
    }

    default String getFileNameFromPath(String path) {
        return StringUtils.isNotBlank(path) ? path.substring(path.lastIndexOf("/") + 1) : "";
    }

    default String getRandom() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    default String customFileNameFromIndex(int index) {
        String indexStr = String.valueOf(index);
        switch (indexStr.length()) {
            case 1:
                return "00000" + indexStr;
            case 2:
                return "0000" + indexStr;
            case 3:
                return "000" + indexStr;
            case 4:
                return "00" + indexStr;
            case 5:
                return "0" + indexStr;
            case 6:
                return indexStr;
            default:
                return "0";
        }
    }

    default String repleaceUrl(String content) {
        if (StringUtils.isNotBlank(content)) {
            String prefix = content.indexOf("http://") != -1 ? "http://" : content.indexOf("https://") != -1 ? "https://" : "";
            String subfix = content.substring(prefix.length()).replace("//", "/");
            return prefix + subfix;
        }

        return "";
    }


    M3u8Info resolveByCommon(String m3u8Url);
//    M3u8Info resolve(String m3u8Url,String mainUrl);
}
