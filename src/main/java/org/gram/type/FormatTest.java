package org.gram.type;

public class FormatTest {

    //格式化输出测试
    public void Format1(){
        int a=20;
        String b="b";
        System.out.printf("%b %B %d %s %S\n",a,a,a,b,b);//true TRUE 20 b B
        System.out.printf("%x %#x %o %#o\n",a,a,a,a);//14 0x14 24 024
        double c=111.3234;
        float d=11.123f;
        System.out.printf("%f %5.2f %+5.2f %-5.2f %+-5.2f\n",c,c,c,c,c);//111.323400 111.32 +111.32 111.32 +111.32
        System.out.printf("%f %5.2f %+5.2f %-5.2f %+-5.2f\n",d,d,d,d,d);//11.123000 11.12 +11.12 11.12 +11.12
        System.out.printf("%e%n%a\n",c,c);//1.113234e+0 换行 20x1.bd4b295e9e1b1p6
        char e='e';
        System.out.printf("%c %C\n",e,e);//e E
        System.out.printf("%h %H %h %h %h %h\n",a,a,b,c,d,e);//14 14 62 d5b23503 4131f7cf 65
    }
}
