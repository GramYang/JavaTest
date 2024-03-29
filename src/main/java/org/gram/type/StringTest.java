package org.gram.type;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class StringTest {
    //字符串操作接口
//    printf输出格式
//    %s字符串
//    %d十进制整数
//    %o八进制整数
//    %x十六进制整数
//    %X大写十六进制整数
//    %f浮点数
//    %b布尔值
//    %c字符
//    %d带符号十进制整数
    public void st1(){
        String s="123132123132";
        byte[] bytes=s.getBytes(StandardCharsets.UTF_8);
        System.out.println(Arrays.toString(bytes));//[49, 50, 51, 49, 51, 50, 49, 50, 51, 49, 51, 50]，这里打印的格式和go差不多
        System.out.println(new String(bytes));
        String s1="ASADASD";
        String s2="asdasds";
        System.out.printf("%s %s\n",s1.toLowerCase(Locale.ROOT),s2.toUpperCase(Locale.ROOT));//asadasd ASDASDS
        String s3="嘎子，网络都是虚拟地，这里面的水很深，你把握不住孩子";
        System.out.printf("%d %d %c %c\n",s3.length(),s2.length(),s3.charAt(1),s2.charAt(1));//26 7 子 s
        System.out.printf("%d %s\n",s1.compareTo(s2),s1.concat(s2));//-32 ASADASDasdasds
        String s4="";
        System.out.println(s4.isEmpty());//true
        ArrayList<String> stringList=new ArrayList<>();
        stringList.add("a");
        stringList.add("b");
        stringList.add("c");
        System.out.printf("stringList %s\n",stringList);
        //stringList [a, b, c]
        System.out.printf("stringList %s\n",String.join(",",stringList));
        //stringList a,b,c
    }
}
