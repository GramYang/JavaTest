package org.gram.type;

public class VarTest {

    private static byte b4;//静态变量可以不初始化就有默认值
    //基本类型
    public void TypeTest(){
        byte b1= (byte) 128;
        byte b2=(byte)-129;
        byte b3 = 0;//局部变量必须初始化
        System.out.printf("%d %d %d %d\n",b1,b2,b3,b4);//-128 127 0 0
        int v1=1;
        System.out.println(((Object) v1).getClass().getName());//java.lang.Integer，基本变量不能打印类型，只能用Object装箱才能用反射打印类型
    }

    public void BitOpt(){
        int a=10;
        System.out.printf("%d %d\n",~a,~(~a));//-11 10
        int b=-10;
        System.out.printf("%d %d %d\n",a<<2,a>>2,b>>>2);//40 2 1073741821
    }
}
