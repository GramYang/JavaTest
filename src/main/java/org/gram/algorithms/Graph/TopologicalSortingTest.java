package org.gram.algorithms.Graph;

import java.util.ArrayList;
import java.util.Stack;

public class TopologicalSortingTest {
    public void t1(){
        Node[] ns=new Node[9];
        int[] arr=new int[9];
        ArrayList<ArrayList<Integer>> listArray=new ArrayList<>();
        for(int i=1;i<9;i++){
            ns[i]=new Node(i);
            listArray.add(new ArrayList<>());
        }
        initData(ns, listArray,arr);
        Stack<Node> stack=new Stack<>();
        for(int i=1;i<9;i++){
            if(arr[i]==0) stack.add(ns[i]);
        }
        while(!stack.isEmpty()){
            Node n=stack.pop();
            System.out.println(n.value);
            ArrayList<Integer> next=n.next;
            for (Integer integer : next) {
                arr[integer]--;
                if (arr[integer] == 0) {
                    stack.add(ns[integer]);
                }
            }
        }
    }

    static class Node{
        int value;
        ArrayList<Integer> next;

        Node(int v){
            this.value=v;
            next=new ArrayList<>();
        }
    }

    //画了一个图，参考https://zhuanlan.zhihu.com/p/86557255
    private void initData(Node[] ns,ArrayList<ArrayList<Integer>> listArray,int[] arr){
        listArray.get(1).add(3);
        ns[1].next=listArray.get(1);
        arr[3]++;
        listArray.get(2).add(4);
        listArray.get(2).add(6);
        ns[2].next=listArray.get(2);
        arr[4]++;
        arr[6]++;
        listArray.get(3).add(5);
        ns[3].next=listArray.get(3);
        arr[5]++;
        listArray.get(4).add(5);
        listArray.get(4).add(6);
        ns[4].next=listArray.get(4);
        arr[5]++;
        arr[6]++;
        listArray.get(5).add(7);
        ns[5].next=listArray.get(5);
        arr[7]++;
        listArray.get(6).add(8);
        ns[6].next=listArray.get(6);
        arr[8]++;
        listArray.get(7).add(8);
        ns[7].next=listArray.get(7);
        arr[8]++;
    }
}
