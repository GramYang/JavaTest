package org.gram.algorithms.Morris;

//https://www.cnblogs.com/AnnieKim/archive/2013/06/15/MorrisTraversal.html
public class MorrisTest {
    public void t1(){
        preTraverse(buildTree());//前序遍历根左右
        System.out.println();
        inTraverse(buildTree());//中序遍历左根右
        System.out.println();
        postTraverse(buildTree());//后序遍历左右根
    }

    static class Node{
        Node left;
        Node right;
        int value;

        Node(int v){
            value=v;
        }
    }

    private Node buildTree(){
        Node n1=new Node(1);
        Node n2=new Node(2);
        Node n3=new Node(3);
        Node n4=new Node(4);
        Node n5=new Node(5);
        Node n6=new Node(6);
        Node n7=new Node(7);
        n1.left=n2;
        n1.right=n3;
        n2.left=n4;
        n2.right=n5;
        n3.left=n6;
        n3.right=n7;
        return n1;
    }

    private void preTraverse(Node head){
        Node cur=head;
        Node mostRight;
        while(cur!=null){
            if(cur.left==null){//遍历右子节点
                System.out.print(cur.value);
                cur=cur.right;
            }else{//遍历左子节点
                mostRight=cur.left;
                //mostRight寻左子树的最右子节点
                while(mostRight.right!=null&&mostRight.right!=cur){
                    mostRight=mostRight.right;
                }
                if(mostRight.right==null){
                    System.out.print(cur.value);
                    mostRight.right=cur;
                    cur=cur.left;
                }else{
                    mostRight.right=null;
                    cur=cur.right;
                }
            }
        }
    }

    private void inTraverse(Node head){
        Node cur=head;
        Node mostRight;
        while(cur!=null){
            if(cur.left==null){
                System.out.print(cur.value);
                cur=cur.right;
            }else{
                mostRight=cur.left;
                while(mostRight.right!=null&&mostRight.right!=cur){
                    mostRight=mostRight.right;
                }
                if(mostRight.right==null){
                    mostRight.right=cur;
                    cur=cur.left;
                }else{
                    System.out.print(cur.value);
                    mostRight.right=null;
                    cur=cur.right;
                }
            }
        }
    }

    private void postTraverse(Node head){
        Node dump=new Node(0);
        dump.left=head;
        Node cur=dump;
        Node prev=null;
        while(cur!=null){
            if(cur.left==null){
                cur=cur.right;
            }else{
                prev=cur.left;
                while(prev.right!=null&&prev.right!=cur) prev=prev.right;
                if(prev.right==null){
                    prev.right=cur;
                    cur=cur.left;
                }else{
                    printReverse(cur.left,prev);
                    prev.right=null;
                    cur=cur.right;
                }
            }
        }
    }

    private void printReverse(Node from,Node to){
        reverse(from,to);
        Node p=to;
        while(true){
            System.out.print(p.value);
            if(p==from) break;
            p=p.right;
        }
        reverse(to,from);
    }

    private void reverse(Node from,Node to){
        if(from==to) return;
        Node x=from;
        Node y=from.right;
        Node z;
        do {
            z = y.right;
            y.right = x;
            x = y;
            y = z;
        } while (x != to);
    }

}
