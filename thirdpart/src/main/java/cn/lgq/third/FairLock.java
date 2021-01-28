package cn.lgq.third;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lgq
 * @Description 重入锁中的公平锁示例,在我的执行结果来看，程序正常运行的时候系统是公平的，但是当我强制停止程序时，t2线程重复持有锁
 * @create 2021-01-28 22:15
 */
public class FairLock implements Runnable{

    public static ReentrantLock fairLock = new ReentrantLock(true);

    @Override
    public void run() {
        while (true){
            try {
                fairLock.lock();
                System.out.println(Thread.currentThread().getName()+"获得锁");
            } finally {
                if (fairLock.isHeldByCurrentThread()){
                    fairLock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) {

        FairLock r1 = new FairLock();
        FairLock r2 = new FairLock();

        Thread t1 = new Thread(r1,"t1");
        Thread t2 = new Thread(r2,"t2");

        t1.start();
        t2.start();

    }
}
