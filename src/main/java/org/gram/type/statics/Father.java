package org.gram.type.statics;

public class Father {
    public static int n1;
    public String n2;
    static{
        n1=10;
        System.out.println("Father static block"+n1);
    }
    {
        n2="a";
        System.out.println("Father block"+n2);
    }
    Father(){
        System.out.println("Father constructor");
    }

    Father(String s){
        n2=s;
        System.out.println("Father constructor with args");
    }

    static class Son extends Father{
        public static int n1;
        public String n2;
        static{
            n1=20;
            System.out.println("Son static block"+n1);
        }
        {
            n2="b";
            System.out.println("Son block"+n2);
        }

        Son(){
            System.out.println("Son constructor");
        }

        Son(String s){
            super();
            n2=s;
            System.out.println("Son constructor with args");
        }
    }
}
