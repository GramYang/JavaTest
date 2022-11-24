package org.gram.algorithms.AStar;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

//https://github.com/ClaymanTwinkle/astar
public class AStarTest {
    public void t1(){
        int[][] maps={
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 }
        };
        MapInfo info=new MapInfo(maps,maps[0].length,maps.length,new Node(1,1),new Node(4,5));
        new AStar().start(info);
        printMap(maps);
    }

    static class Coord{
        int x,y;//以矩阵左上角为原点，x轴坐标和y轴坐标
        Coord(int x,int y){
            this.x=x;
            this.y=y;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj==null) return false;
            if(obj instanceof Coord){
                Coord c=(Coord)obj;
                return x==c.x &&y==c.y;
            }
            return false;
        }
    }

    static class Node implements Comparable<Node>{
        Coord coord;//坐标
        Node parent;//父节点
        int G;//准确值，起点到当前结点的代价
        int H;//估值，当前节点到目的节点的代价
        Node(int x,int y){
            this.coord=new Coord(x,y);
        }
        Node(Coord c,Node p,int g,int h){
            coord=c;
            parent=p;
            G=g;
            H=h;
        }

        @Override
        public int compareTo(Node o) {
            if(o==null) return -1;
            if(G+H>o.G+o.H){
                return 1;
            }else if(G+H<o.G+o.H) return -1;
            return 0;
        }
    }

    static class MapInfo{
        int[][] maps;//矩阵
        int width;//矩阵宽度
        int height;//矩阵高度
        Node start;//起始点
        Node end;//目的地

        MapInfo(int[][] m,int w,int h,Node s,Node e){
            maps=m;
            width=w;
            height=h;
            start=s;
            end=e;
        }
    }

    static class AStar{
        int bar=1;//矩阵中表示障碍
        int path=2;//矩阵中表示移动路径
        int directValue=10;//横竖移动代价
        int obliqueValue=14;//斜移动代价

        Queue<Node> openList=new PriorityQueue<>();
        List<Node> closeList=new ArrayList<>();

        public void start(MapInfo mapInfo){
            if(mapInfo==null) return;
            openList.clear();
            closeList.clear();
            openList.add(mapInfo.start);//添加起点
            moveNodes(mapInfo);
        }

        private void moveNodes(MapInfo mapInfo){
            while(!openList.isEmpty()){
                Node current=openList.poll();
                closeList.add(current);
                //从openList取头结点存入closeList
                addNeighborNodeInOpen(mapInfo,current);
                if(isCoordInClose(mapInfo.end.coord)){
                    drawPath(mapInfo.maps,mapInfo.end);
                    break;
                }
            }
        }


        //添加相邻节点到openList，一共8个方向
        private void addNeighborNodeInOpen(MapInfo mapInfo,Node current){
            int x=current.coord.x;
            int y=current.coord.y;
            addNeighborNodeInOpen(mapInfo,current,x-1,y,directValue);//左
            addNeighborNodeInOpen(mapInfo,current,x,y-1,directValue);//上
            addNeighborNodeInOpen(mapInfo,current,x+1,y,directValue);//右
            addNeighborNodeInOpen(mapInfo,current,x,y+1,directValue);//下
            addNeighborNodeInOpen(mapInfo,current,x-1,y-1,obliqueValue);//左上
            addNeighborNodeInOpen(mapInfo,current,x+1,y-1,obliqueValue);//右上
            addNeighborNodeInOpen(mapInfo,current,x+1,y+1,obliqueValue);//右下
            addNeighborNodeInOpen(mapInfo,current,x-1,y+1,obliqueValue);//左下
        }

        //添加相邻节点到openList，某一个方向，计算代价
        private void addNeighborNodeInOpen(MapInfo mapInfo,Node current,int x,int y,int value){
            if(canAddNodeToOpen(mapInfo,x,y)){
                Node end=mapInfo.end;
                Coord coord=new Coord(x,y);
                int g=current.G+value;//这里是g的计算方式，加上该方向的代价
                Node child=findNodeInOpen(coord);//在openList里面查询该坐标
                if(child==null){//没查到
                    int h=calcH(end.coord,coord);
                    if(isEndNode(end.coord,coord)){//结束场景，已经移动到目的节点了
                        child=end;
                        child.parent=current;
                        child.G=g;
                        child.H=h;
                    }else{
                        child=new Node(coord,current,g,h);//普通场景，保存当前坐标，current为父节点
                    }
                    openList.add(child);//存入openList
                }else if(child.G>g){//这里很关键，因为横竖移动的代价是10，斜移动的代价是14，这样就实现了最短路径
                    child.G=g;
                    child.parent=current;
                    openList.add(child);
                }
            }
        }

        //判断这个节点能不能放到openList
        private boolean canAddNodeToOpen(MapInfo mapInfo,int x,int y){
            if(x<0||x>=mapInfo.width||y<0||y>= mapInfo.height) return false;//不能超过矩阵
            if(mapInfo.maps[y][x]==bar) return false;//判断这个节点是否是栅栏
            return !isCoordInClose(x, y);//该节点不能在closeList中
        }

        //判断该坐标是否在closeList中
        private boolean isCoordInClose(Coord coord){
            return isCoordInClose(coord.x,coord.y);
        }

        //遍历closeList查找该坐标
        private boolean isCoordInClose(int x,int y){
            if(closeList.isEmpty()) return false;
            for(Node n:closeList){
                if(n.coord.x==x && n.coord.y==y) return true;
            }
            return false;
        }

        //从openList查询该节点
        private Node findNodeInOpen(Coord coord){
            if(coord==null||openList.isEmpty()) return null;
            for(Node n:openList){
                if(n.coord.equals(coord)) return n;
            }
            return null;
        }

        //估算h，就是x轴差值加y轴差值
        private int calcH(Coord end,Coord coord){
            return Math.abs(end.x-coord.x)+Math.abs(end.y-coord.y);
        }

        //判断是否是目的节点
        private boolean isEndNode(Coord end,Coord coord){
            return end.equals(coord);
        }

        private void drawPath(int[][] maps,Node end){
            if(end==null||maps==null)return;
            System.out.println("总代价："+end.G);
            while(end!=null){
                Coord c=end.coord;
                maps[c.y][c.x]=path;
                end=end.parent;
            }
        }
    }

    private void printMap(int[][] maps){
        for(int i=0;i<maps.length;i++){
            for(int j=0;j<maps[i].length;j++){
                System.out.print(maps[i][j]+" ");
            }
            System.out.println();
        }
    }
}
