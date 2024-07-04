package cn.shzu.a_star;
import cn.shzu.utils.Coordinate;
import cn.shzu.utils.MapConstruction;
import cn.shzu.utils.Node;

import java.util.*;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * &#064;description  ARAStar algorithm detail
 * {@code @date} 2023/11/14 22:47:19
 */
public class AStar
{
    public final static int PATH = 2; // 路径
    public final static double DIRECT_VALUE = 1; // 横竖移动代价
    public final static double OBLIQUE_VALUE = 1.4; // 斜移动代价


    Queue<Node> openList = new PriorityQueue<>(); // 优先队列(升序)
    Set<Coordinate> closeList = new HashSet<>();

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
        // 开始搜索
        openList.add(mapInfo.getStartNode());
        moveNodes(mapInfo,mapInfo.getGoalNode());
        displayPath(mapInfo.getGoalNode());
    }



    public void startVectorAndRaster(MapConstruction map){
        if (map==null) return;
        Node goalNode = map.getGoalNode();
        Node startNode = map.getStartNode();
        if (isObs(map.getGoalNode(),map)){
            System.out.println("目标点不可达");
            return;
        }
        openList.clear();
        closeList.clear();
        //开始搜索
        openList.add(startNode);
        moveNodes(map,goalNode);
    }

    public ArrayList<Node> start(MapConstruction mapInfo, int n){
        if(mapInfo==null) return null;
        if (isObs(mapInfo.getGoalNode(),mapInfo)){
            System.out.println("目标点不可达");
            return null;
        }
        openList.clear();
        closeList.clear();
        //开始搜索
        openList.add(mapInfo.getStartNode());
        moveNodes(mapInfo,mapInfo.getGoalNode());
        ArrayList<Node> path= new ArrayList<>();
        Node node = mapInfo.getGoalNode();
        while (node.getParentNode()!=null){
            path.add(node);
            node = node.getParentNode();
        }
        return path;
    }

    public double start(MapConstruction mapInfo,int n,int k){
        if(mapInfo==null) return -1;
        if (isObs(mapInfo.getGoalNode(),mapInfo)){
            System.out.println("目标点不可达");
            return -1;
        }
        openList.clear();
        closeList.clear();
        //开始搜索
        openList.add(mapInfo.getStartNode());
        moveNodes(mapInfo,mapInfo.getGoalNode());
        return mapInfo.getGoalNode().getGoalCost();
    }



    public boolean isObs(Node node,MapConstruction map){
        int x = node.getCoordinate().getX();
        int y = node.getCoordinate().getY();
        return map.getMapInfo()[x][y]==1;
    }
    /**
     * 移动当前结点
     */
    private void moveNodes(MapConstruction map,Node end)
    {
        while (!openList.isEmpty())
        {
            Node current = openList.poll();
            closeList.add(current.getCoordinate());
//            System.out.println("节点为 :"+Arrays.toString(current.getCoordinate().getLocation()));
            addNeighborNodeInOpen(map,current);
            if (isCoordInClose(end.getCoordinate()))
            {
                end = current;
                map.setGoalNode(end);
                System.out.println("到达目标");
                System.out.println("总代价:" + end.getGoalCost());
                break;
            }
        }
    }

    /**
     * 在二维数组中绘制路径
     */
    private void drawPath(float[][] maps, Node end)
    {
        if(end==null||maps==null) return;
        System.out.println("总代价：" + end.getGoalCost());
        while (end != null)
        {
            Coordinate c = end.getCoordinate();
            maps[c.getY()][c.getX()] = PATH;
            end = end.getParentNode();
        }
    }

    /**
     * 读取路径
      */
    private void displayPath (Node end){
        ArrayList<int []> paths = new ArrayList<>();
        Node node = end;
        int count = 0;
        while (node.getParentNode()!=null){
            paths.add(node.getCoordinate().getLocation());
            node = node.getParentNode();
        }
        for (int [] i:paths) {
            System.out.print(Arrays.toString(i));
            count++;
        }
        System.out.println();
        System.out.println("节点数为："+count);
    }


    /**
     * 添加所有邻结点到open表
     */
    private void addNeighborNodeInOpen(MapConstruction mapInfo,Node current)
    {

        Coordinate coordinate = current.getCoordinate();
        int x = coordinate.getX();
        int y = coordinate.getY();
        // 左
        Coordinate coordinateLeft = new Coordinate(x - 1, y);
        addNeighborNodeInOpen(mapInfo,current,  coordinateLeft, DIRECT_VALUE);
        // 上
        Coordinate coordinateUp = new Coordinate(x , y-1);
        addNeighborNodeInOpen(mapInfo,current, coordinateUp, DIRECT_VALUE);
        // 右
        Coordinate coordinateRight = new Coordinate(x+1 , y);
        addNeighborNodeInOpen(mapInfo,current,coordinateRight, DIRECT_VALUE);
        // 下
        Coordinate coordinateDown = new Coordinate(x , y+1);
        addNeighborNodeInOpen(mapInfo,current,coordinateDown, DIRECT_VALUE);
        // 左上
        Coordinate coordinateLU = new Coordinate(x-1 , y-1);
        addNeighborNodeInOpen(mapInfo,current, coordinateLU, OBLIQUE_VALUE);
        // 右上
        Coordinate coordinateRU = new Coordinate(x+1 , y-1);
        addNeighborNodeInOpen(mapInfo,current, coordinateRU, OBLIQUE_VALUE);
        // 右下
        Coordinate coordinateRD = new Coordinate(x+1 , y+1);
        addNeighborNodeInOpen(mapInfo,current, coordinateRD, OBLIQUE_VALUE);
        // 左下
        Coordinate coordinateLD = new Coordinate(x-1 , y+1);
        addNeighborNodeInOpen(mapInfo,current, coordinateLD, OBLIQUE_VALUE);


    }
    /**
     * 添加一个邻结点到open表
     */
    private void addNeighborNodeInOpen(MapConstruction mapInfo,Node current, Coordinate coordinate, double value)
    {
        if (canAddNodeToOpen(mapInfo, coordinate))
        {
            Node end = mapInfo.getGoalNode();
            double G = current.getGoalCost() + value; // 计算邻结点的G值
            Node child = findNodeInOpen(coordinate);
//            检查新增节点是否访问过
            if (child == null) {
                double H = calcH(end.getCoordinate(), coordinate, mapInfo.getStartNode().getCoordinate());
                child = new Node(coordinate, H, G, 1.0, current);
                child.setFCost();
                openList.add(child);
            } else if (child.getGoalCost() > G) {
                openList.remove(child); // 移除旧的节点
                child.setGoalCost(G);
                child.setParentNode(current);
                child.setFCost();
                openList.add(child); // 重新插入更新后的节点
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
     *
     * @param end 终点
     * @param coord 现有点
     * @param start 起点
     * @return 返回权重改变的欧式距离
     */
    public double weightDistanceImprove(Coordinate end,Coordinate coord,Coordinate start){
        double w = 1 + (calcDiag(end,coord)/calcDiag(end,start));
        return w*(calcDiag(end,coord) + calcCross(start,end,coord)/calcDiag(end,start));
    }

    /**
     *
     * @param end 终点
     * @param coord 当前点
     * @param map 权重地图
     * @return 返回H值
     */
    public double weightMapImprove(Coordinate end,Coordinate coord,double [][] map){
        return (Math.sqrt(Math.pow(end.getY()-coord.getY(),2)+Math.pow(end.getX()-coord.getX(),2)) * DIRECT_VALUE ) + map[coord.getX()][coord.getY()];
    }

    public double ChebyshevDistance(Coordinate end, Coordinate coord){
        int dx = Math.abs(coord.getX()-end.getX());
        int dy = Math.abs(coord.getY()-end.getY());
        return Math.max(dx,dy);
    }
    public double NormalDistributionOfConstants(Coordinate end, Coordinate coord,Coordinate start){
        double mean,stdDev,x,Q;
        mean = Math.sqrt(Math.pow(end.getY()-start.getY(),2)+Math.pow(end.getX()-start.getX(),2))* DIRECT_VALUE;
        x = Math.sqrt(Math.pow(end.getY()-coord.getY(),2)+Math.pow(end.getX()-coord.getX(),2)) * DIRECT_VALUE;
        stdDev = 1.0;
        Q = Math.exp(-0.5 * Math.pow((x - mean) / stdDev, 2)) / (stdDev * Math.sqrt(2 * Math.PI));
        return (0.9+Q)*x;
    }
    /**
     * 计算H的估值：欧式距离
     */
    private double calcH(Coordinate end, Coordinate coord,Coordinate start)
    {
        //1、曼哈顿距离
//     return (Math.abs(end.getX() - coord.getX()) + Math.abs(end.getY() - coord.getY())) * DIRECT_VALUE;
        //2、传统欧式距离
       return Math.sqrt(Math.pow(end.getY()-coord.getY(),2)+Math.pow(end.getX()-coord.getX(),2)) * DIRECT_VALUE;
//       3、 步长权重欧式距离
//        return weightDistanceImprove(end,coord,start);
        //4、切比雪夫距离 无效
//        return ChebyshevDistance(end,coord);
        //5、常数补偿与正态分布思想。
//        return NormalDistributionOfConstants(end,coord,start);
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
    private boolean canAddNodeToOpen(MapConstruction mapInfo,Coordinate coord)
    {
        int x = coord.getX();
        int y = coord.getY();
        // 是否在地图中
        if (x < 0 || x >= mapInfo.getHeight() || y < 0 || y >= mapInfo.getWidth()) return false;
        // 判断是否是不可通过的结点
        if (mapInfo.getMapInfo()[x][y]==1) return false;
        // 判断结点是否存在close表
        return !isCoordInClose(coord);
    }

    /**
     * 判断坐标是否在close表中
     */
    private boolean isCoordInClose(Coordinate coord) {
        return closeList.contains(coord);
    }



}
