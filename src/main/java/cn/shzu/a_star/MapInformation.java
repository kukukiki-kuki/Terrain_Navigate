package cn.shzu.a_star;

import cn.shzu.utils.DEMReader;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description Map generation from dem images
 * @date 2023/11/14 23:19:13
 */
public class MapInformation {
    private float[][] DemMap;
    private float[][] SlopeMap;
    private int width;
    private int height;
    private Node start;
    private Node goal;

    public Node getStart() {
        return start;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public Node getGoal() {
        return goal;
    }

    public void setGoal(Node goal) {
        this.goal = goal;
    }


    public MapInformation(String DemFilePath, String SlopeFilePath,Node start,Node goal) {
        this.DemMap = DEMReader.readDEM(DemFilePath);
        this.SlopeMap = DEMReader.readDEM(SlopeFilePath);
        this.height = DemMap.length;
        this.width = DemMap[0].length;
        this.start = start;
        this.goal = goal;
    }
    public MapInformation(){

    }

    public float[][] getDemMap() {
        return SlopeMap;
    }

    public void setDemMap(float[][] demMap) {
        DemMap = demMap;
    }

    public float[][] getSlopeMap() {
        return SlopeMap;
    }

    public void setSlopeMap(float[][] slopeMap) {
        SlopeMap = slopeMap;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
