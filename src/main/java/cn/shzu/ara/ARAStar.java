package cn.shzu.ara;

import cn.shzu.utils.Coordinate;
import cn.shzu.utils.Node;
import cn.shzu.utils.MapConstruction;

import java.util.*;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description ARAstar
 * @date 2024/1/7 21:56:43
 */
public class ARAStar {

    public final static double DIRECT_VALUE = 1; // 横竖移动代价
    public final static double OBLIQUE_VALUE = 1.4; // 斜移动代价
    private double goalNodeGoalCost = Double.MAX_VALUE;
    private double startNodeGoalCost = 0.0;
    Queue<Node> openList = new PriorityQueue<>(); // 优先队列(升序)
    List<Node> closeList = new ArrayList<>();
    List<Node> inconsList = new ArrayList<>();
    /**
     * 开始算法
     */
    public void start(MapConstruction mapInfo)
    {
        if(mapInfo==null) return;
        if (isObs(mapInfo.getGoalNode(),mapInfo)){
            System.out.println("目标点不可达");
            return;
        }
        // clean
        openList.clear();
        closeList.clear();
        inconsList.clear();
        // 开始搜索
        openList.add(mapInfo.getStartNode());
        improvePath(mapInfo);
        displayPath(mapInfo.getGoalNode());
    }
    public boolean isObs(Node node,MapConstruction map){
        int x = node.getCoordinate().getX();
        int y = node.getCoordinate().getY();
        return map.getMapInfo()[x][y]==2;
    }

    private void improvePath(MapConstruction mapInfo){
        if (openList!=null){
            while (mapInfo.getGoalNode().getFCost()>openList.peek().getFCost()){
                Node current = openList.poll();
                closeList.add(current);
                addNeighborNodeInOpen(mapInfo,current);
            }
        }
    }

    private double getMinOpenAndIcons(){

        double minValue = Double.MAX_VALUE;
        for (Node node:openList) {
            if (node.getGoalCost()+node.getHeuristicCost()<minValue){
                minValue = node.getGoalCost()+node.getHeuristicCost();
            }
        }
        for (Node node:inconsList) {
            if (node.getHeuristicCost()+node.getGoalCost()<minValue){
                minValue = node.getGoalCost()+node.getHeuristicCost();
            }
        }
        return minValue;
    }

    private double weightAdjust(double weight){
        return Math.min(weight, goalNodeGoalCost) / getMinOpenAndIcons();
    }
    /**
     * 移动当前结点
     */
//    private void moveNodes(MapConstruction map,Node end)
//    {
//        while (!openList.isEmpty())
//        {
//            Node current = openList.poll();
//            closeList.add(current);
//            System.out.println(Arrays.toString(current.getCoordinate().getLocation()));
//            addNeighborNodeInOpen(map,current);
//            if (isCoordInClose(end.getCoordinate()))
//            {
//                System.out.println("到达目标");
//                System.out.println("总代价" + end.getGoalCost());
//                break;
//            }
//        }
//    }
    /**
     * 读取路径
     */
    private void displayPath (Node end){
        ArrayList<int []> paths = new ArrayList<>();
        Node node = end;
        while (node.getParentNode()!=null){
            paths.add(node.getCoordinate().getLocation());
            node = node.getParentNode();
        }
        for (int [] i:paths) {
            System.out.print(Arrays.toString(i));
        }
    }

    /**
     * 添加所有邻结点到open表
     */
    private void addNeighborNodeInOpen(MapConstruction mapInfo,Node current)
    {
        int x = current.getCoordinate().getX();
        int y = current.getCoordinate().getY();
        // 左
        addNeighborNodeInOpen(mapInfo,current, x - 1, y, DIRECT_VALUE);
        // 上
        addNeighborNodeInOpen(mapInfo,current, x, y - 1, DIRECT_VALUE);
        // 右
        addNeighborNodeInOpen(mapInfo,current, x + 1, y, DIRECT_VALUE);
        // 下
        addNeighborNodeInOpen(mapInfo,current, x, y + 1, DIRECT_VALUE);
        // 左上
        addNeighborNodeInOpen(mapInfo,current, x - 1, y - 1, OBLIQUE_VALUE);
        // 右上
        addNeighborNodeInOpen(mapInfo,current, x + 1, y - 1, OBLIQUE_VALUE);
        // 右下
        addNeighborNodeInOpen(mapInfo,current, x + 1, y + 1, OBLIQUE_VALUE);
        // 左下
        addNeighborNodeInOpen(mapInfo,current, x - 1, y + 1, OBLIQUE_VALUE);
    }
    /**
     * 添加一个邻结点到open表
     */
    private void addNeighborNodeInOpen(MapConstruction mapInfo,Node current, int x, int y, double value)
    {
        if (canAddNodeToOpen(mapInfo,x, y))
        {
            Node end=mapInfo.getGoalNode();
            Node start = mapInfo.getStartNode();
           Coordinate coord = new Coordinate(x, y);
            double G = current.getGoalCost() + value; // 计算邻结点的G值
            Node child = findNodeInOpen(coord);
//            检查新增节点是否访问过
            if (child == null)
            {
//                double H=calcH(end.getCoordinate(),coord,start.getCoordinate()); // 计算H值
//                if(isEndNode(end.getCoordinate(),coord))
//                {
//                    child=end;
//                    child.setParentNode(current);
//                    child.setGoalCost(G);
//                    child.setHeuristicCost(H);
//                }
//                else
//                {
//                    child = new Node(coord, H,G,1.0,current);
//                    child.setGoalCost(Double.MAX_VALUE);
//                }
//                openList.add(child);
                goalNodeGoalCost = Double.MAX_VALUE;
            }
            else if (child.getGoalCost()> G)
            {
                if (findNodeINClose(coord)!=null){
                    child.setGoalCost(G);
                    child.setParentNode(current);
                    openList.add(child);
                }else {
                    inconsList.add(child);
                }
            }
        }
    }
    /**
     * 从Open列表中查找结点
     */
    private Node findNodeInOpen(Coordinate coord)
    {
        if (coord == null || openList.isEmpty()) return null;
        for (Node node : openList)
        {
            if (node.getCoordinate().equals(coord))
            {
                return node;
            }
        }
        return null;
    }
    private Node findNodeINClose(Coordinate coord){
        if (coord == null || closeList.isEmpty()) return null;
        for (Node node: closeList){
            if (node.getCoordinate().equals(coord)){
                return node;
            }
        }
        return null;
    }
    private int calcCross(Coordinate start, Coordinate end, Coordinate current ){
        int [] Vse = {Math.abs(end.getX()-start.getX()),Math.abs(end.getY()-start.getY())};
        int [] Vce = {Math.abs(end.getX()-current.getX()),Math.abs(end.getY()-current.getY())};
        return Vce[0]*Vse[1] - Vce[1]*Vse[0];
    }

    private double calcDiag(Coordinate c1, Coordinate c2){
        double dx = Math.abs(c1.getX() - c2.getX());
        double dy = Math.abs(c1.getY() - c2.getY());
        return dx + dy - ((2-Math.sqrt(2))*Math.min(dx,dy));
    }


    /**
     * 计算H的估值：欧式距离
     */
    private double calcH(Coordinate end, Coordinate coord,Coordinate start)
    {
//     return (Math.abs(end.getX() - coord.getX()) + Math.abs(end.getY() - coord.getY())) * DIRECT_VALUE;
       return Math.sqrt(Math.pow(end.getY()-coord.getY(),2)+Math.pow(end.getX()-coord.getX(),2)) * DIRECT_VALUE;
//        //权重计算
//        double w = 1 + (calcDiag(end,coord)/calcDiag(end,start));
//        return w*(calcDiag(end,coord) + calcCross(start,end,coord)/calcDiag(end,start));
    }

    /**
     * 判断结点是否是最终结点
     */
    private boolean isEndNode(Coordinate end, Coordinate coord)
    {
        return end.equals(coord);
    }

    /**
     * 判断结点能否放入Open列表
     */
    private boolean canAddNodeToOpen(MapConstruction mapInfo,int x, int y)
    {
        // 是否在地图中
        if (x < 0 || x >= mapInfo.getWidth() || y < 0 || y >= mapInfo.getHeight()) return false;
        // 判断是否是不可通过的结点
        if (mapInfo.getMapInfo()[x][y]==2) return false;
        // 判断结点是否存在close表
        return !isCoordInClose(x, y);
    }

    /**
     * 判断坐标是否在close表中
     */
    private boolean isCoordInClose(Coordinate coord)
    {
        return coord!=null&&isCoordInClose(coord.getX(), coord.getY());
    }

    /**
     * 判断坐标是否在close表中
     */
    private boolean isCoordInClose(int x, int y)
    {
        if (closeList.isEmpty()) return false;
        for (Node node : closeList)
        {
            if (node.getCoordinate().getX() == x && node.getCoordinate().getY() == y)
            {
                return true;
            }
        }
        return false;
    }
}
