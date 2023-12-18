package cn.shzu.a_star;
import java.util.*;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * &#064;description  AStar algorithm detail
 * {@code @date} 2023/11/14 22:47:19
 */
public class AStar
{
    public final static int BAR = 1; // 障碍值
    public final static int PATH = 2; // 路径
    public final static double DIRECT_VALUE = 1; // 横竖移动代价
    public final static double OBLIQUE_VALUE = 1.4; // 斜移动代价


    Queue<Node> openList = new PriorityQueue<>(); // 优先队列(升序)
    List<Node> closeList = new ArrayList<>();

    /**
     * 开始算法
     */
    public void start(MapInformation mapInfo)
    {
        if(mapInfo==null) return;
        if (isObs(mapInfo.getGoal(),mapInfo)){
            System.out.println("目标点不可达");
            return;
        }
        // clean
        openList.clear();
        closeList.clear();
        // 开始搜索
        openList.add(mapInfo.getStart());
        moveNodes(mapInfo,mapInfo.getGoal());
        displayPath(mapInfo.getGoal());
    }
    public ArrayList<Node> start(MapInformation mapInfo, int n){
        if(mapInfo==null) return null;
        if (isObs(mapInfo.getGoal(),mapInfo)){
            System.out.println("目标点不可达");
            return null;
        }
        openList.clear();
        closeList.clear();
        //开始搜索
        openList.add(mapInfo.getStart());
        moveNodes(mapInfo,mapInfo.getGoal());
        ArrayList<Node> path= new ArrayList<>();
        Node node = mapInfo.getGoal();
        while (node.getParentNode()!=null){
            path.add(node);
            node = node.getParentNode();
        }
        return path;
    }

    public double start(MapInformation mapInfo,int n,int k){
        if(mapInfo==null) return -1;
        if (isObs(mapInfo.getGoal(),mapInfo)){
            System.out.println("目标点不可达");
            return -1;
        }
        openList.clear();
        closeList.clear();
        //开始搜索
        openList.add(mapInfo.getStart());
        moveNodes(mapInfo,mapInfo.getGoal());
        return mapInfo.getGoal().getGoalCost();
    }

    public boolean isObs(Node node,MapInformation mapInfo){
        int x = node.getCoordinate().getX();
        int y = node.getCoordinate().getY();
        return mapInfo.getSlopeMap()[x][y] >= 25;
    }

    /**
     * 移动当前结点
     */
    private void moveNodes(MapInformation mapInfo,Node end)
    {
        while (!openList.isEmpty())
        {
            Node current = openList.poll();
            closeList.add(current);
            System.out.println(Arrays.toString(current.getCoordinate().getLocation()));
            addNeighborNodeInOpen(mapInfo,current);
            if (isCoordInClose(end.getCoordinate()))
            {
                System.out.println("到达目标");
                System.out.println("总代价" + end.getGoalCost());
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
    private void addNeighborNodeInOpen(MapInformation mapInfo,Node current)
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
    private void addNeighborNodeInOpen(MapInformation mapInfo,Node current, int x, int y, double value)
    {
        if (canAddNodeToOpen(mapInfo,x, y))
        {
            Node end=mapInfo.getGoal();
            Node start = mapInfo.getStart();
            Coordinate coord = new Coordinate(x, y);
            double G = current.getGoalCost() + value; // 计算邻结点的G值
            Node child = findNodeInOpen(coord);
//            检查新增节点是否访问过
            if (child == null)
            {
                double H=calcH(end.getCoordinate(),coord,start.getCoordinate()); // 计算H值
                if(isEndNode(end.getCoordinate(),coord))
                {
                    child=end;
                    child.setParentNode(current);
                    child.setGoalCost(G);
                    child.setHeuristicCost(H);
                }
                else
                {
                    child = new Node(coord, H,G,current);
                }
                openList.add(child);
            }
            else if (child.getGoalCost()> G)
            {
                child.setGoalCost(G);
                child.setParentNode(current);
                openList.add(child);
            }
        }
    }

    /**
     *
     * @param mapInformation 地图信息
     * @return 返回将道路信息添加后的地图
     */
    public float[][] executeVector2Raster(MapInformation mapInformation){
        float[][] slopeMap = mapInformation.getSlopeMap();
        System.out.println(slopeMap.length);
        for (int i = 0; i < slopeMap.length; i++) {
           slopeMap[500][i] = 100;
           slopeMap[i][500] = 100;
        }
        for (int i = 0; i < slopeMap.length; i++) {
            for (int j = 0; j < slopeMap[0].length; j++) {
                if (i==j){
                    slopeMap[i][j] =100;
                }
                if ((i+j)==slopeMap.length-1){
                    slopeMap[i][j] = 100;
                }
            }
        }
        return slopeMap;
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
     * 计算H的估值：欧式距离
     */
    private double calcH(Coordinate end, Coordinate coord,Coordinate start)
    {
//     return (Math.abs(end.getX() - coord.getX()) + Math.abs(end.getY() - coord.getY())) * DIRECT_VALUE;
//       return Math.sqrt(Math.pow(end.getY()-coord.getY(),2)+Math.pow(end.getX()-coord.getX(),2)) * DIRECT_VALUE;
        //权重计算
        double w = 1 + (calcDiag(end,coord)/calcDiag(end,start));
        return w*(calcDiag(end,coord) + calcCross(start,end,coord)/calcDiag(end,start));
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
    private boolean canAddNodeToOpen(MapInformation mapInfo,int x, int y)
    {
        // 是否在地图中
        if (x < 0 || x >= mapInfo.getWidth() || y < 0 || y >= mapInfo.getHeight()) return false;
        // 判断是否是不可通过的结点
        if (mapInfo.getSlopeMap()[x][y] >= 20) return false;
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
