package com.dfm.utils;

import java.util.concurrent.*;

public class TaskThreadPool {

    private static ThreadPoolExecutor threadPoolExecutor;
    TimeUnit unit;
    BlockingQueue workQueue;

    public static void initTreadPool() {
        if (threadPoolExecutor == null || threadPoolExecutor.isShutdown()) {
            ThreadFactory threadFactory;
            threadPoolExecutor = new ThreadPoolExecutor(5, 5, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFacotryImpl("taskName", new ThreadGroup("taskGroup")));
        }
    }

    public static void execute(Runnable runnable) {
        if (threadPoolExecutor != null || !threadPoolExecutor.isShutdown()) {
            threadPoolExecutor.execute(runnable);
        }
    }
}
