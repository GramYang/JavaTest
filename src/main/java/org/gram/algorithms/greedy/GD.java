package org.gram.algorithms.greedy;

import java.util.ArrayList;
import java.util.Collections;

public class GD {
    //参考https://blog.csdn.net/qcl108/article/details/109435861，配合图一起看
    public void huffmanTree(){
        int[] arr ={13,7,8,3,29,6,1};
        Node root=createTree(arr);
        if(root!=null){
            root.preOrder();//这里输出的是对赫夫曼树的前序遍历
        }else{
            System.out.println("空树");
        }
    }

    private Node createTree(int[] arr){
        ArrayList<Node> nodes=new ArrayList<>();
        for(int v:arr){
            nodes.add(new Node(v));
        }
        while(nodes.size()>1){
            Collections.sort(nodes);
            Node leftNode= nodes.get(0);
            Node rightNode=nodes.get(1);
            Node parent=new Node(leftNode.value+rightNode.value);
            parent.left=leftNode;
            parent.right=rightNode;
            nodes.add(parent);
            nodes.remove(leftNode);
            nodes.remove(rightNode);
        }
        return nodes.get(0);
    }

    static class Node implements Comparable<Node>{
        int value;
        Node left;
        Node right;

        Node(int v){
            this.value=v;
        }

        public void preOrder(){
            System.out.println(this);
            if(this.left!=null){
                this.left.preOrder();
            }
            if(this.right!=null){
                this.right.preOrder();
            }
        }

        @Override
        public int compareTo(Node o) {
            return this.value-o.value;
        }

        @Override
        public String toString() {
            return "Node [value="+value+"]";
        }
    }
}
