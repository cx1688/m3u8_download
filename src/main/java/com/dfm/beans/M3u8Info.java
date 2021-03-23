package com.dfm.beans;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @program: m3u8_project
 * @description:
 * @author: Mr.D
 * @create: 2020-12-23 16:09
 */
public class M3u8Info implements Serializable {
    private List<SegmentFileInfo>segmentFileInfos;
    private String baseUrl="";
    private boolean hasKey;
    private long current;
    private long total;
    public List<SegmentFileInfo> getSegmentFileInfos() {
        return segmentFileInfos;
    }

    public void setSegmentFileInfos(List<SegmentFileInfo> segmentFileInfos) {
        this.segmentFileInfos = segmentFileInfos;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public boolean isHasKey() {
        return hasKey;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "M3u8Info{" +
                "segmentFileInfos=" + segmentFileInfos +
                ", baseUrl='" + baseUrl + '\'' +
                '}';
    }
}
