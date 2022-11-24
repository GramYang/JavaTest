package org.gram.algorithms.MinimumSpanningTree;

public class KruskalTest {
    public void t1(){
        int[] parent=new int[15];
        Edge[] edges=createMap();
        int sum=0;
        for(Edge e:edges){
            int start=find(parent,e.start);
            int end=find(parent,e.end);
            if(start!=end){
                parent[start]=end;
                System.out.println("访问到了节点：{" + start + "," + end + "}，权值：" + e.weight);
                sum+=e.weight;
            }
        }
        System.out.println("最小生成树的权值总和：" + sum);
    }

    static class Edge{
        int start,end,weight;

        Edge(int s,int e,int w){
            start=s;
            end=e;
            weight=w;
        }
    }

    //该图在https://www.jianshu.com/p/40e6c83df608
    private Edge[] createMap(){
        Edge[] edges=new Edge[15];
        edges[0]=new Edge(4,7,7);
        edges[1]=new Edge(2,8,8);
        edges[2]=new Edge(0,1,10);
        edges[3]=new Edge(0,5,11);
        edges[4]=new Edge(1,8,12);
        edges[5]=new Edge(3,7,16);
        edges[6]=new Edge(1,6,16);
        edges[7]=new Edge(5,6,17);
        edges[8]=new Edge(1,2,18);
        edges[9]=new Edge(6,7,19);
        edges[10]=new Edge(3,4,20);
        edges[11]=new Edge(3,8,21);
        edges[12]=new Edge(2,3,22);
        edges[13]=new Edge(3,6,24);
        edges[14]=new Edge(4,5,26);
        return edges;
    }

    private int find(int[] parent,int index){
        while(parent[index]>0){
            index=parent[index];
        }
        return index;
    }
}
