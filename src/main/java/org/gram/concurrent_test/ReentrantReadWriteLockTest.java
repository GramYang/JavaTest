package org.gram.concurrent_test;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockTest {
    //读写锁并发读写测试，这里读和最终结果都是固定的，和go不一样。我猜测因为java的thread新建起来没有go的协程快导致写线程优先于读线程执行。
    public void t1(){
        ConMap cm = new ConMap();
        CountDownLatch cdl=new CountDownLatch(6);
        for(int i=0;i<3;i++)
        {
            int finalI = i;
            new Thread(){
                public void run(){
                    cm.put(finalI +"", finalI +"");
                    cdl.countDown();
                }
            }.start();
        }
        for(int i=0;i<3;i++)
        {
            int finalI = i;
            new Thread(){
                public void run(){
                    cm.get(finalI +"");
                    cdl.countDown();
                }
            }.start();

        }
        try{
            cdl.await();
            if(!cdl.await(10, TimeUnit.SECONDS)){
                System.out.println("countdownlatch阻塞超时");
            }
            System.out.println(cm.map);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    static class ConMap{
        private final ReentrantReadWriteLock lock=new ReentrantReadWriteLock();//这就是读写锁
        private final HashMap<String,String> map=new HashMap<>();

        public void get(String key){
            lock.readLock().lock();
            try {
                Thread.sleep((long)(Math.random()*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "--read--"+map.get(key));
            lock.readLock().unlock();
        }

        public void put(String key,String value){
            lock.writeLock().lock();
            try {
                Thread.sleep((long)(Math.random()*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key,value);
            lock.writeLock().unlock();
        }

        public void writeInRead(String key){
            lock.readLock().lock();
            String v=map.get(key);
            System.out.println(Thread.currentThread().getName() + "--readlock--"+map.get(key));
            put(key,v+1);//这样会阻塞，只有相同类型的锁才能可重入
            lock.readLock().unlock();
        }

        public void readInWrite(String key){
            lock.writeLock().lock();
            map.put(key,"x");
            get(key);
            lock.writeLock().unlock();
        }
    }

    //读锁里面获得写锁，阻塞，在读锁里面不能获取写锁。在写锁里面可以再次获得写锁，但是在读锁里面获得写锁是不行的。
    //写锁里面获取读锁，可行。
    public void t2(){
        ConMap cm = new ConMap();
        cm.map.put("1","1");
        cm.map.put("2","2");
        cm.map.put("3","3");
        CountDownLatch cdl=new CountDownLatch(3);
        for(int i=1;i<=3;i++)
        {
            int finalI = i;
            new Thread(){
                public void run(){
//                    cm.writeInRead(finalI+"");//阻塞
                    cm.readInWrite(finalI+"");//不阻塞
                    cdl.countDown();
                }
            }.start();
        }
        try{
            cdl.await();
            if(!cdl.await(10, TimeUnit.SECONDS)){
                System.out.println("countdownlatch阻塞超时");
            }
            System.out.println(cm.map);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
