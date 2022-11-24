package org.gram.type;

public class BasicTest {
    //测试Object方法
    public void t1(){
        bag1 b1=new bag1(1,"a");
        System.out.printf("%s %d\n",b1,b1.hashCode());//org.example.type.BasicTest$bag1@61bbe9ba，toString()默认行为
        bag1 b2=new bag1(1,"a");
        System.out.printf("%s %s\n",b1.equals(b2),b1==b2);//false false，equals()的行为相同，比较的都是引用指针是不是一致
        System.out.printf("%d %d\n",b1.hashCode(),b2.hashCode());//1639705018 1304836502
    }

    static class bag1{
        int a;
        String b;
        bag1(int a,String b){
            this.a=a;
            this.b=b;
        }
    }
}
