package cn.lgq.third;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lgq
 * @Description:重入锁的简单示例
 * @create 2021-01-07 23:02
 */
public class ReenterLock implements Runnable{

    /**
     * 新建可重入锁实例，这个实例创建的时候有问题，
     */
    public static ReentrantLock lock  = new ReentrantLock();
    /**
     * 临界区资源
     */
    public static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 1000;++j){
            lock.lock();
            try {
                ++i;
                System.out.println(Thread.currentThread()+":"+i);
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException{

        ReenterLock reenterLock = new ReenterLock();

        Thread t1 = new Thread(reenterLock);
        Thread t2 = new Thread(reenterLock);

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
