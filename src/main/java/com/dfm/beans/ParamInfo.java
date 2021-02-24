package com.dfm.beans;

import java.io.Serializable;

/**
 * @program: m3u8_project
 * @description:
 * @author: Mr.D
 * @create: 2020-12-24 01:51
 */
public class ParamInfo implements Serializable {
    private String name;
    private String url;
    private String path;
    private String key;
    private int core;
    private int tryNum;
    //0,停止，1 开始，2 暂停，3 完成
    private int taskStatus;
    public ParamInfo() {
    }

    public ParamInfo(String name, String url, String path, String key, int core, int tryNum) {
        this.name = name;
        this.url = url;
        this.path = path;
        this.key = key;
        this.core = core;
        this.tryNum = tryNum;
        this.taskStatus=0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getCore() {
        return core;
    }

    public void setCore(int core) {
        this.core = core;
    }

    public int getTryNum() {
        return tryNum;
    }

    public void setTryNum(int tryNum) {
        this.tryNum = tryNum;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString() {
        return "ParamInfo{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", path='" + path + '\'' +
                ", key='" + key + '\'' +
                ", core=" + core +
                ", tryNum=" + tryNum +
                '}';
    }
}
