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

    @Override
    public String toString() {
        return "M3u8Info{" +
                "segmentFileInfos=" + segmentFileInfos +
                ", baseUrl='" + baseUrl + '\'' +
                '}';
    }
}
