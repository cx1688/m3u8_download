package com.dfm.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @program: m3u8_project
 * @description:
 * @author: Mr.D
 * @create: 2020-12-23 16:28
 */
public class SegmentFileInfo implements Serializable {
    /**
     * 方法
     */
    private String method;
    private String iv;
    private byte[] key;
    private String url;
    private double time;
    private boolean isDownload;
    private int tryCount;
    private String resolution;
    private boolean isM3u8;
    /**
     * 保存路径
     */
    private String savePath;
    public SegmentFileInfo() {
    }

    public SegmentFileInfo(String method, String iv, byte[] key, String url, double time) {
        this.method = method;
        this.iv = iv;
        this.key = key;
        this.url = url;
        this.time = time;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }

    public int getTryCount() {
        return tryCount;
    }

    public void setTryCount(int tryCount) {
        this.tryCount = tryCount;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public boolean isM3u8() {
        return isM3u8;
    }

    public void setM3u8(boolean m3u8) {
        isM3u8 = m3u8;
    }

    @Override
    public String toString() {
        return "SegmentFileInfo{" +
                "method='" + method + '\'' +
                ", iv='" + iv + '\'' +
                ", key='" + key + '\'' +
                ", url='" + url + '\'' +
                ", time=" + time +
                ", isDownload=" + isDownload +
                ", tryCount=" + tryCount +
                ", savePath='" + savePath + '\'' +
                '}';
    }
}
