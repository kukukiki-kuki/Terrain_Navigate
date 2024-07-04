package cn.shzu.function;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description atestss
 * @date 2023/12/10 19:24:07
 */
import cn.shzu.a_star.AStar;

import cn.shzu.utils.Coordinate;
import cn.shzu.utils.MapConstruction;
import cn.shzu.utils.Node;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MapVisualization extends JFrame {

    private static final int ROWS = 500;
    private static final int COLS = 500;
    private static final int CELL_SIZE = 2;
    private float [][] map;
    private final String demFilePath="D:\\ArcGis\\Project\\Big\\p10627dsm_Resample1_Clip.tif";
    private final String slopeFilePath="D:\\ArcGis\\Project\\Big\\Slope_p106273.tif";

    private float [][] grid;

    public MapVisualization() {
//        grid = new int[ROWS][COLS];
        // 初始化地图数据，这里用 0 表示空格，1 表示障碍物
        Coordinate start = new Coordinate(234, 245);
        Coordinate end = new Coordinate(200, 200);

        map = initializeMap(start, end);
//        setTitle("165高原 Grid Map Visualization");
        setSize(COLS * CELL_SIZE, ROWS * CELL_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }


    private float [][] initializeMap(Coordinate start,Coordinate end) {
        float[][] answer = DEMReader.slopeMap(slopeFilePath);
        // 在这里初始化地图数据，可以根据实际情况设置不同的值


        answer[start.getX()][start.getY()] = 999;
        answer[end.getX()][end.getY()] = 998;
        Coordinate shortestPointOnRoad = new Point2LineDistance().getShortestPoint(answer, start);
        System.out.println(Arrays.toString(shortestPointOnRoad.getLocation()));
        answer[shortestPointOnRoad.getX()][shortestPointOnRoad.getY()] = 10;
        grid = answer;
        return answer;
    }



    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                // 根据地图数据绘制不同的颜色，例如，0表示空格，绘制白色；1表示障碍物，绘制黑色，2表示起点，绘制绿色，3表示终点，绘制红色
                if (grid[i][j] == 0) {
                    g.setColor(Color.WHITE);
                }else if (grid[i][j] == 2){
                    g.setColor(Color.BLACK);
                }else if (grid[i][j] == 1) {
                    g.setColor(Color.BLUE);
                }else if (grid[i][j] == 10){
                    g.setColor(Color.ORANGE);
                }else if (grid[i][j] == 999){
                    g.setColor(Color.GREEN);
                }else if (grid[i][j] ==998){
                    g.setColor(Color.RED);
                }
                // 绘制单元格
                g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                // 绘制边框
                g.setColor(Color.GRAY);
                g.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    /**
     *
     * @param start 起点坐标
     * @param i x坐标
     * @param j y坐标
     * @return 返回是否是道路
     */
    public boolean isExpandRoad(Coordinate start,int i,int j){
        for (Node node : expandRoad(start)) {
            if ((node.getCoordinate().getX()==i)&&(node.getCoordinate().getY()==j)){
                return true;
            }
        }
        return false;
    }
    /**
     *
     * @param coordinate 坐标点
     * @return 向八个方向扩展
     */
    public ArrayList<Coordinate> expandDirection(Coordinate coordinate){
        int [][] direction = {{-1,-1},{1,1},{1,-1},{-1,1},{0,1},{0,-1},{1,0},{-1,0}};
        Coordinate sCoord = coordinate;
        int newX = sCoord.getX();
        int newY = sCoord.getY();
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        for (int[] ints : direction) {
            while (true) {
                newX += ints[0];
                newY += ints[1];
                if (newX >= 0 && newX < ROWS && newY >= 0 && newY < COLS) {
                    if (grid[newX][newY] == 100) {
                        Coordinate coordinate1 = new Coordinate(newX, newY);
                        coordinates.add(coordinate1);
                        break;
                    }

                }
            }
        }
        return coordinates;
    }

    /**
     *
     * @param coordinate 开始扩展的坐标位置
     * @return 返回扩展的八个方向的节点
     */
    public ArrayList<Node> expandRoad(Coordinate coordinate){
        ArrayList<Node> path;
        HashMap<Double,ArrayList<Node>> hashMap = null;
        double minCost = Double.MAX_VALUE;
        for (Coordinate coordinate1 : expandDirection(coordinate)) {
            Node sNode = new Node(coordinate.getX(), coordinate.getY());
            Node eNode = new Node(coordinate1.getX(), coordinate1.getY());
            MapConstruction mapInformation = new MapConstruction(demFilePath, sNode, eNode);
            hashMap= new HashMap<>();
            path = new AStar().start(mapInformation, 1);
            double goalCost = path.get(0).getGoalCost();
            hashMap.put(goalCost,path);
//            double goalCost = new ARAStar().start(mapInformation,1,1);
            if (goalCost<minCost){
                minCost = goalCost;
            }
        }

        return hashMap.get(minCost);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MapVisualization mapVisualization = new MapVisualization();
            mapVisualization.setVisible(true);
        });
    }
}

