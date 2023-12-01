package cn.shzu.a_star;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description Map generation from dem images
 * @date 2023/11/14 23:19:13
 */
public class MapInformation {
    private float[][] map;
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


    public MapInformation(String filePath,Node start,Node goal) {
        this.map = DEMReader.readDEM(filePath);
        this.height = map.length;
        this.width = map[0].length;
        this.start = start;
        this.goal = goal;
    }
    public MapInformation(){

    }

    public float[][] getMap() {
        return map;
    }

    public void setMap(float[][] map) {
        this.map = map;
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
