package cn.lgq.third;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lgq
 * @Description 可重入锁锁申请等待限时，无等待时间演示,这个案例需要耐性等待 仅有两个线程在争夺资源我就等了4m39s264ms才出死锁，所以理论上可行，耗时太长了。
 * @create 2021-01-28 21:53
 */
public class TryLock implements Runnable{

    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();

    int lock;

    /**
     * 控制加锁顺序，构造死锁场景
     * @param lock
     */
    public TryLock(int lock){
        this.lock = lock;
    }

    @Override
    public void run() {
        if (lock == 1){
            while (true){
                try {
                    lock1.lock();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    if (lock2.tryLock()){
                        try {
                            System.out.println(Thread.currentThread().getId()+":My Job done");
                            return;
                        } finally {
                            lock2.unlock();
                        }
                    }
                } finally {
                    lock1.unlock();
                }
            }
        }else {
            while (true){
                try {
                    lock2.lock();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    if (lock1.tryLock()){
                        try {
                            System.out.println(Thread.currentThread().getId()+":My job done");
                            return;
                        } finally {
                            lock1.unlock();
                        }
                    }
                } finally {
                    lock2.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {

        TryLock r1 = new TryLock(1);
        TryLock r2 = new TryLock(2);

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t1.start();
        t2.start();



    }


}
