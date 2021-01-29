package com.dfm.utils;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;

/**
 * @program: m3u8_project
 * @description:
 * @author: Mr.D
 * @create: 2020-12-29 11:10
 */
public class ThreadFacotryImpl implements ThreadFactory {
    private String threadName;
    private ThreadGroup group;

    public ThreadFacotryImpl(String threadName, ThreadGroup group) {
        this.threadName = threadName;
        this.group = group;
    }

    @Override
    public Thread newThread(@NotNull Runnable r) {
        return new Thread(group, r, threadName);
    }
}
