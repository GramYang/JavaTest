package org.gram.cellnet.queue;

public class CellnetQueueTest {
    //不限流
    public void c1(){
        CellnetQueue<CellnetQueue.OnlyCall> cellnetQueue = new CellnetQueue<>();
        cellnetQueue.startLoop();
        for(int i=0;i<10;i++){
            int i1=i;
            cellnetQueue.post(()->System.out.println("thread "+Thread.currentThread().getName()+" value "+i1));
        }
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        cellnetQueue.stopLoop(()->System.out.println("close cellnet queue loop"));
    }

    //限流
    public void c2(){
        CellnetQueue<CellnetQueue.OnlyCall> cellnetQueue = new CellnetQueue<>(5);
        cellnetQueue.startLoopWithRate();
        for(int i=0;i<100;i++){
            int i1=i;
            cellnetQueue.post(()->System.out.println("thread "+Thread.currentThread().getName()+" value "+i1));
        }
        try{
            Thread.sleep(30000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        cellnetQueue.stopLoop(()->System.out.println("close cellnet queue loop"));
    }
}
