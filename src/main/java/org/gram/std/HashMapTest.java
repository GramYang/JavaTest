package org.gram.std;

import java.util.HashMap;

public class HashMapTest {
    //基本使用
    public void t1(){
        //测试hashmap的空值
        HashMap<String,String> m1=new HashMap<>();
        m1.put("defined","x");
        System.out.println(m1.containsKey("undefined"));//false
        System.out.println(m1.get("undefined"));//null
        System.out.println(m1.getOrDefault("undefined","default"));//default

    }

    //所有的map的entry遍历时不能指定下标
    public void t2(){
        HashMap<String,String> m1=new HashMap<>();
        //按顺序遍历entry，hashmap不能指定下标遍历entry
        m1.put("a","1");
        m1.put("b","2");
        m1.put("c","3");
        for(HashMap.Entry<String,String> entry: m1.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
    }
    static class Value1{
        public int a;

        public Value1(int a) {
            this.a = a;
        }
    }

    //hashmap套容器的修改测试，两种方式都可以而且不报错
    public void t3(){
        HashMap<Integer,Value1> m1=new HashMap<>();
        m1.put(1,new Value1(10));
        m1.get(1).a+=10;
        Value1 v1=m1.get(1);
        v1.a+=10;
        System.out.println(m1.get(1).a);//30
    }
}
