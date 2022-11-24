package org.gram.type;

import java.util.ArrayList;

public class ObjectTest {
    //Object类型测试
    public void t1(){
        String s1="a";
        Integer i1=10;
        ArrayList<Object> arrayList=new ArrayList<>();
        arrayList.add(s1);
        arrayList.add(i1);
        System.out.println(arrayList.get(0).getClass().getName());//java.lang.String
        System.out.println(arrayList.get(0) instanceof String);//true
        System.out.println(arrayList.get(1).getClass().getName());//java.lang.Integer
        System.out.println(arrayList.get(1) instanceof Integer);//true
    }
}
