package cn.shzu.utils;


import cn.shzu.function.DEMReader;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description map construction
 * @date 2023/12/20 18:04:02
 */
public class MapConstruction {
    private float [][] mapInfo;
    private double [][] weightEnvironmentMap;
    private Node startNode;
    private Node goalNode;
    private int height;
    private int width;

    public MapConstruction(String filePath, Node startNode, Node goalNode) {
        this.mapInfo = DEMReader.slopeMap(filePath);
        this.startNode = startNode;
        this.goalNode = goalNode;
        this.height = mapInfo.length;
        this.width =mapInfo[0].length;
        this.weightEnvironmentMap = ConstructWeightMap();
//        this.startNode.setGoalCost(0.0);
//        this.goalNode.setGoalCost(Double.MAX_VALUE);
    }
    public double [][] ConstructWeightMap(){
        double [][] weightMap = new double[500][500];
        for (int i = 0; i < 500; i++) {
            for (int j = 0; j < 500; j++) {
                if (mapInfo[i][j] == 1){
                    weightMap[i][j] = 1.0;
                }
                else weightMap[i][j] = Math.random();
            }
        }
        return weightMap;
    }

    public float[][] getMapInfo() {
        return mapInfo;
    }

    public void setMapInfo(float[][] mapInfo) {
        this.mapInfo = mapInfo;
    }

    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public Node getGoalNode() {
        return goalNode;
    }

    public void setGoalNode(Node goalNode) {
        this.goalNode = goalNode;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
