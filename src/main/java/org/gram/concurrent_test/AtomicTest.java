package org.gram.concurrent_test;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {
    public void at1(){
        AtomicInteger ai=new AtomicInteger();
        System.out.printf("%d %d %d %d %d %d\n",ai.get(),ai.getAndSet(1),ai.getAndIncrement(),ai.getAndDecrement(),ai.getAndAdd(2),ai.get());//0 0 1 2 1 3
        System.out.printf("%b %d\n",ai.compareAndSet(3,5),ai.get());//true 5
    }
}
