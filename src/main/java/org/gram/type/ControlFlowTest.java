package org.gram.type;

public class ControlFlowTest {
    //break测试，break只会退出外面最近一层的循环
    public void t1(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(i==1){
                    break;
                }
                System.out.println(i+"/"+j);
            }
        }
    }

    //switch使用
    //switch不支持long！！
    public void t2(){
        int i1=0;
        switch (i1){
            case 0:
                System.out.println("i1 is 0");
                break;
            case 1:
                System.out.println("i1 is 1");
                break;
            case 2:
                System.out.println("i1 is 2");
                break;
        }
        System.out.println(switchReturn(5));
    }

    private int switchReturn(int v){
        switch (v){
            case 0:
                return 10;
            case 1:
                return 11;
            case 2:
                return 12;
        }
        return 100;
    }
}
