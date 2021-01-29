import org.jetbrains.annotations.NotNull;


import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: m3u8_project
 * @description:
 * @author: Mr.D
 * @create: 2020-12-29 10:36
 */
public class TestThread {

    static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),new ThreadFactoryImpl("testThread"));

    public static void main(String[] args) {
//        for (int i = 0; i < 100; i++) {
//            int num = i;
//            threadPoolExecutor.execute(()->Test.download(num));
//        }

        }
}


class ThreadFactoryImpl implements ThreadFactory{
    private String threadName;

    public ThreadFactoryImpl(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public Thread newThread(@NotNull Runnable r) {
        return new Thread(r, threadName);
    }
}

class Test{
    public static void download(int i){
        synchronized (Test.class) {
            System.out.println(Thread.currentThread().getName() + "::" + i);
            try {
                TimeUnit.SECONDS.sleep(2L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}