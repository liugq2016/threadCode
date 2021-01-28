package cn.lgq.third;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lgq
 * @Description 可重入锁的简单中断响应演示
 * @create 2021-01-28 21:21
 */
public class IntLock implements Runnable{

    public static ReentrantLock lock1 = new ReentrantLock();
    public static ReentrantLock lock2 = new ReentrantLock();

    int lock;

    /**
     * 控制加锁顺序，方便构造死锁
     * @param lock
     */
    public IntLock(int lock){
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            if (lock == 1){
                lock1.lockInterruptibly();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("lock1:我被中断了");
                }
                lock2.lockInterruptibly();
            }else {
                lock2.lockInterruptibly();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("lock2:我被中断了");
                }
                lock1.lockInterruptibly();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock1.isHeldByCurrentThread()){
                lock1.unlock();
            }
            if (lock2.isHeldByCurrentThread()){
                lock2.unlock();
            }
            System.out.println("线程退出");
        }
    }

    public static void main(String[] args) {

        IntLock r1 = new IntLock(1);
        IntLock r2 = new IntLock(2);

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t1.start();
        t2.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //中断t2线程，让其释放锁，解决死锁的局面
        t2.interrupt();


    }

}
