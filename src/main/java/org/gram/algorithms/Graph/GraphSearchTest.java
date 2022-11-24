package org.gram.algorithms.Graph;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class GraphSearchTest {

    private Node buildTree(){
        Node n1=new Node(1);
        Node n2=new Node(2);
        Node n3=new Node(3);
        Node n4=new Node(4);
        Node n5=new Node(5);
        Node n6=new Node(6);
        Node n7=new Node(7);
        n1.rightNode=n3;
        n1.leftNode=n2;
        n2.rightNode=n5;
        n2.leftNode=n4;
        n3.rightNode=n7;
        n3.leftNode=n6;
        return n1;
    }

    public void breadthFirstSearch(){
        Node head=buildTree();
        Queue<Node> queue=new LinkedList<>();
        queue.add(head);
        while(!queue.isEmpty()){
            Node n=queue.poll();
            System.out.println(n.data);
            if(n.leftNode!=null){
                queue.add(n.leftNode);
            }
            if(n.rightNode!=null){
                queue.add(n.rightNode);
            }
        }
    }

    public void depthFirstSearch(){
        Node head=buildTree();
        Stack<Node> stack=new Stack<>();
        stack.add(head);
        while(!stack.isEmpty()){
            Node n=stack.pop();
            System.out.println(n.data);
            if(n.rightNode!=null){
                stack.push(n.rightNode);
            }
            if(n.leftNode!=null){
                stack.push(n.leftNode);
            }
        }
    }

    static class Node{
        int data;
        Node leftNode;
        Node rightNode;

        Node(int d){
            data=d;
        }
    }
}
