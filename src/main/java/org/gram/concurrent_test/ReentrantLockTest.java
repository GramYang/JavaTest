package org.gram.concurrent_test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    public void t1(){
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();

        new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLock.lock();
                System.out.println(Thread.currentThread().getName() + "拿到锁了");
                System.out.println(Thread.currentThread().getName() + "等待信号");
                try {
                    condition.await();//阻塞该线程，不过也会释放锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + "拿到信号");

                reentrantLock.unlock();
            }
        }, "线程1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLock.lock();
                System.out.println(Thread.currentThread().getName() + "拿到锁了");

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + "发出信号");
                condition.signal();

                reentrantLock.unlock();
            }
        }, "线程2").start();

    }

}
