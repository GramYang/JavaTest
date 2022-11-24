package org.gram.concurrent_test;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {
    //concurrenthashmap使用
    public void chmt1(){
        ConcurrentHashMap<Integer,String> cmap=new ConcurrentHashMap<>();
        cmap.put(1,"a");
        System.out.printf("%s %s\n",cmap.putIfAbsent(1,"aa"),cmap.putIfAbsent(2,"b"));//a null
        System.out.printf("%s %s\n",cmap.get(2),cmap.getOrDefault(3,"c"));//b c
    }
}
