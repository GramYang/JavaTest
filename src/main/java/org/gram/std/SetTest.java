package org.gram.std;

import java.util.HashSet;
import java.util.TreeSet;

public class SetTest {
    public void hashSetTest(){
        HashSet<String> hs=new HashSet<>();
        hs.add("a");
        hs.add("b");
        hs.add("b");//重复添加元素无效
        System.out.println(hs);//[a, b]
        System.out.printf("%b %d %b\n",hs.contains("a"),hs.size(),hs.remove("a"));//true 2 true
        System.out.println(hs);//[b]
        hs.add("z");
        hs.add("d");
        for(String s:hs){//遍历元素
            System.out.println(s);
        }
    }

    public void treeSetTest(){
        TreeSet<String> ts=new TreeSet<>();
        ts.add("a");
        ts.add("b");
        ts.add("b");//重复添加元素无效
        System.out.println(ts);
        System.out.printf("%b %d %b\n",ts.contains("a"),ts.size(),ts.remove("a"));
        System.out.println(ts);
        ts.add("z");
        ts.add("d");
        for(String s:ts){//遍历元素
            System.out.println(s);
        }
    }
}
