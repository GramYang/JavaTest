package org.gram.type;

import java.util.Arrays;

public class ArrayTest {
    public void t1(){
        //普通数组
        int[] a1=new int[2];
        a1[0]=10;
//        a1[1]=20;
        System.out.println(Arrays.toString(a1));//[10, 0]
    }
}
