package cn.shzu.utils;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description atestss
 * @date 2023/12/10 19:24:07
 */
import cn.shzu.a_star.AStar;
import cn.shzu.a_star.Coordinate;
import cn.shzu.a_star.MapInformation;
import cn.shzu.a_star.Node;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MapVisualization extends JFrame {

    private static final int ROWS = 300;
    private static final int COLS = 300;
    private static final int CELL_SIZE = 4;
    private float [][] map;
    private final String demFilePath="D:\\ArcGis\\Project\\Big\\p10627dsm_Resample1_Clip.tif";
    private final String slopeFilePath="D:\\ArcGis\\Project\\Big\\Slope_p106272.tif";

    private int[][] grid;

    public MapVisualization() {
        grid = new int[ROWS][COLS];
        // 初始化地图数据，这里用 0 表示空格，1 表示障碍物
        Coordinate start = new Coordinate(23, 45);
        Coordinate end = new Coordinate(237, 287);

        map = initializeMap(start, end);
//        setTitle("165高原 Grid Map Visualization");
        setSize(COLS * CELL_SIZE, ROWS * CELL_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public boolean isExpandRoad(Coordinate start,int i,int j){
        for (Node node : expandRoad(start)) {
            if ((node.getCoordinate().getX()==i)&&(node.getCoordinate().getY()==j)){
               return true;
            }
        }
        return false;
    }
    private float[][] initializeMap(Coordinate start,Coordinate end) {
        float[][] slopeMaps = DEMReader.readDEM(slopeFilePath);
        float[][] demMaps = DEMReader.readDEM(demFilePath);
        float[][] maps= new float[ROWS][COLS];

        // 在这里初始化地图数据，可以根据实际情况设置不同的值
        // 这里简单地示范了一个随机生成障碍物的例子
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if ((i==j)||(i+j==ROWS-1)||(i==ROWS/2)||(j==COLS/2)){
                    grid[i][j]=100;
                }
                else if (slopeMaps[i][j]<=30){
                    grid[i][j] = 0;
                }
                else{
                    grid[i][j] = 1;
                }
//                grid[i][j] = Math.random() < 0.2 ? 1 : 0; // 20% 的概率设置为障碍物
               maps[i][j] = slopeMaps[i][j];
            }
        }
        grid[start.getX()][start.getY()] = 2;
        grid[end.getX()][end.getY()] = 3;
//        for (int i = 0; i < COLS; i++) {
//            for (int j = 0; j < ROWS; j++) {
//                if (isExpandRoad(start,i,j)){
//                    grid[i][j]=101;
//                }
//            }
//        }
        return maps;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                // 根据地图数据绘制不同的颜色，例如，0表示空格，绘制白色；1表示障碍物，绘制黑色，2表示起点，绘制绿色，3表示终点，绘制红色
                if (grid[i][j] == 0) {
                    g.setColor(Color.WHITE);
                }else if (grid[i][j] == 1){
                    g.setColor(Color.BLACK);
                }else if (grid[i][j] == 100){
                    g.setColor(Color.BLUE);
                }else if (grid[i][j] == 101){
                        g.setColor(Color.CYAN);
                }else if (grid[i][j] == 2){
                    g.setColor(Color.GREEN);
                }else if (grid[i][j] ==3){
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

    public ArrayList<Node> expandRoad(Coordinate coordinate){
        ArrayList<Node> path;
        HashMap<Double,ArrayList<Node>> hashMap = null;
        double minCost = Double.MAX_VALUE;
        for (Coordinate coordinate1 : expandDirection(coordinate)) {
            Node sNode = new Node(coordinate.getX(), coordinate.getY());
            Node eNode = new Node(coordinate1.getX(), coordinate1.getY());
            MapInformation mapInformation = new MapInformation(demFilePath, slopeFilePath, sNode, eNode);
            hashMap= new HashMap<>();
            path = new AStar().start(mapInformation, 1);
            double goalCost = path.get(0).getGoalCost();
            hashMap.put(goalCost,path);
//            double goalCost = new AStar().start(mapInformation,1,1);
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

