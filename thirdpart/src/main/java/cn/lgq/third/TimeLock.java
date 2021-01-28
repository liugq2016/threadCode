package cn.lgq.third;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lgq
 * @Description 重入锁中的锁申请等待限时
 * @create 2021-01-28 21:39
 */
public class TimeLock implements Runnable{

    public static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {

        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)){
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("get lock failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }

    }

    public static void main(String[] args) {

        TimeLock r1 = new TimeLock();
        TimeLock r2 = new TimeLock();

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t1.start();
        t2.start();


    }

}
