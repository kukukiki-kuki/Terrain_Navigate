package cn.shzu.a_star;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

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
    public final static int DIRECT_VALUE = 10; // 横竖移动代价
    public final static int OBLIQUE_VALUE = 14; // 斜移动代价


    Queue<Node> openList = new PriorityQueue<>(); // 优先队列(升序)
    List<Node> closeList = new ArrayList<>();

    /**
     * 开始算法
     */
    public void start(MapInformation mapInfo)
    {
        if(mapInfo==null) return;
        // clean
        openList.clear();
        closeList.clear();
        // 开始搜索
        openList.add(mapInfo.getStart());
        moveNodes(mapInfo,mapInfo.getGoal());
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
            addNeighborNodeInOpen(mapInfo,current);
            if (isCoordInClose(end.getCoordinate()))
            {
                drawPath(mapInfo.getMap(), end);
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
    private void addNeighborNodeInOpen(MapInformation mapInfo,Node current, int x, int y, int value)
    {
        if (canAddNodeToOpen(mapInfo,x, y))
        {
            Node end=mapInfo.getGoal();
            Coordinate coord = new Coordinate(x, y);
            int G = current.getGoalCost() + value; // 计算邻结点的G值
            Node child = findNodeInOpen(coord);
            if (child == null)
            {
                int H=calcH(end.getCoordinate(),coord); // 计算H值
                if(isEndNode(end.getCoordinate(),coord))
                {
                    child=end;
                    child.setParentNode(current);
                    child.setGoalCost(G);
                    child.setHeuristicCost(H);
                }
                else
                {
                    child = new Node(coord, G,H,current);
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


    /**
     * 计算H的估值：“曼哈顿”法，坐标分别取差值相加
     */
    private int calcH(Coordinate end, Coordinate coord)
    {
        return (Math.abs(end.getX() - coord.getX()) + Math.abs(end.getY() - coord.getY())) * DIRECT_VALUE;
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
        if (mapInfo.getMap()[y][x] == BAR) return false;
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
