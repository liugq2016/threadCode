package cn.lgq.third;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author lgq
 * @Description 读写锁demo
 * @create 2021-04-13 21:31
 */
public class ReadWriteLockDemo {
    private static Lock lock = new ReentrantLock();
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static Lock readLock = readWriteLock.readLock();
    private static Lock writeLock = readWriteLock.writeLock();
    private int value;

    /**
     *  模拟读操作
     * @param lock 锁
     * @return 读取到的数据
     * @throws InterruptedException
     */
    public Object handleRead(Lock lock) throws InterruptedException{
        try {
            lock.lock();
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return value;
    }

    /**
     *  模拟写操作
     * @param lock 锁
     * @param index 输入的数据
     * @throws InterruptedException
     */
    public void handleWrite(Lock lock,int index) throws InterruptedException{
        try {
            lock.lock();
            Thread.sleep(1000);
            value=index;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        System.out.println("Write:"+value);
    }

    public static void main(String[] args) {
        final ReadWriteLockDemo demo = new ReadWriteLockDemo();
        Runnable readRunnable=new Runnable(){
            @Override
            public void run() {
                try {
                    System.out.println(demo.handleRead(readLock));
//                demo.handleRead(lock);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
       Runnable writeRunnable = new Runnable(){
            @Override
            public void run() {
                try {
                    demo.handleWrite(writeLock,new Random().nextInt());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                demo.handleWrite(lock);
            }
        };
       for (int i = 0;i<18;++i){
           new Thread(readRunnable).start();
       }
       for (int i = 18;i<20;++i){
           new Thread(writeRunnable).start();
       }
    }
}
