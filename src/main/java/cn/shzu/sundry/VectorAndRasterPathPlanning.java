package cn.shzu.sundry;

import cn.shzu.a_star.AStar;
import cn.shzu.utils.Coordinate;
import cn.shzu.utils.Node;
import cn.shzu.utils.MapConstruction;
import cn.shzu.function.Point2LineDistance;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description vector and raster data for path planning
 * @date 2023/12/20 17:55:26
 */
public class VectorAndRasterPathPlanning {
    private static final String filePath;
    private static final Coordinate startCoordinate = new Coordinate(213,245);
    private static final Coordinate goalCoordinate = new Coordinate(879,587);
    private static final Node startNode = new Node(startCoordinate,0.0,0.0,1.0,null);
    private static final Node goalNode = new Node(goalCoordinate,0.0,0.0,1.0,null);

    static {
        filePath = "D:\\ArcGis\\Project\\Big\\Slope_p106273.tif";
    }

    public static void main(String[] args) {

    }
    public static void executePathPlanningMission(){
        //获取地图数据
        MapConstruction map = new MapConstruction(filePath, startNode, goalNode);
        //地图数据基础进行路径规划
        Point2LineDistance point2LineDistance = new Point2LineDistance();
        //获取道路到起点距离最近的点
        Coordinate PointDisShortestFromStart = point2LineDistance.getShortestPoint(map.getMapInfo(), startCoordinate);
        //规划从起点到距离最近点
        new AStar().startVectorAndRaster(map);
        //获取道路到终点距离最近的点
        point2LineDistance.getShortestPoint(map.getMapInfo(), goalCoordinate);



    }
}
