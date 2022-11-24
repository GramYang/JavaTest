package org.gram.cellnet.queue;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.*;

public class CellnetQueue<T extends CellnetQueue.OnlyCall> {
    private final LinkedBlockingQueue<T> queue;
    private final CountDownLatch cdl;
    private final ExecutorService exec;
    private boolean stop;
    private RateLimiter rateLimiter;

    public CellnetQueue(int r){
        queue=new LinkedBlockingQueue<>();
        cdl=new CountDownLatch(1);
        exec= Executors.newCachedThreadPool();
        stop=true;
        rateLimiter=RateLimiter.create(r);
    }

    public CellnetQueue(){
        queue=new LinkedBlockingQueue<>();
        cdl=new CountDownLatch(1);
        exec= Executors.newCachedThreadPool();
        stop=true;
        rateLimiter=RateLimiter.create(5);
    }

    public void startLoop(){
        new Thread(()->{
            while (stop){
                try {
                    T t = queue.take();
                    exec.submit(t::call);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            exec.shutdown();
            try{
                boolean ok = exec.awaitTermination(1, TimeUnit.DAYS);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            cdl.countDown();
        }).start();
    }

    public void startLoopWithRate(){
        new Thread(()->{
            while (stop){
                try {
                    T t = queue.take();
                    rateLimiter.acquire();
                    exec.submit(t::call);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            exec.shutdown();
            try{
                boolean ok = exec.awaitTermination(1, TimeUnit.DAYS);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            cdl.countDown();
        }).start();
    }

    public void stopLoop(T t){
        stop=false;
        queue.add(t);
    }

    //此方法用来阻塞当前线程
    public void waitLoop(){
        try{
            cdl.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void post(T t){
        if(t !=null){
            queue.add(t);
        }
    }

    public interface OnlyCall{
        void call();
    }
}
