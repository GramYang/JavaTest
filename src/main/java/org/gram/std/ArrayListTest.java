package org.gram.std;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListTest {
    //基本使用
    public void t1(){
        ArrayList<String> list=new ArrayList<>(4);
//        list.add(3,"c");
//        System.out.println(list);//不能直接指定下标添加元素会报越界
        list.add("a");
//        System.out.println(list.get(1));//还是会报越界
        if(list.size()>=2){
            System.out.println(list.get(1));
        }
    }

    //数组和arraylist转换
    public void t2(){
        //数组转list
        Integer[] ints=new Integer[]{1,2,3,4,5};
        List<Integer> list1= Arrays.asList(ints);
        System.out.println(list1);//这里的list1不能转arraylist
        //数组转arraylist
        ArrayList<Integer> list2=new ArrayList<>(Arrays.asList(ints));
        System.out.println(list2);
        //arraylist转回数组
        Integer[] ints1=list2.toArray(new Integer[0]);
        for(Integer i:ints1){
            System.out.println(i);
        }
    }
}
