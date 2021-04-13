package cn.lgq.third;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lgq
 * @Description 倒计时控制器
 * @create 2021-04-13 22:00
 */
public class CountDownLatchDemo implements Runnable {
    static final CountDownLatch end = new CountDownLatch(10);
    static final CountDownLatchDemo demo = new CountDownLatchDemo();
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("check complete");
//            线程已经准备好
            end.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException{
        ExecutorService exec = Executors.newFixedThreadPool(10);
        for (int i = 0 ;i<10;++i){
            exec.submit(demo);
        }
//        要求主线程跟开启的线程一同结束
        end.await();
        System.out.println("Fire!");
//        CountDownLatch中10个线程已经全部准备好
        exec.shutdown();
    }
}
