package cn.shzu.function;

import cn.shzu.utils.Coordinate;
import cn.shzu.utils.Coordinate;

import java.util.ArrayList;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description point to line
 * @date 2023/12/20 18:23:21
 */
public class Point2LineDistance {
    /**
     *
     * @param sCoord 起始点
     * @param eCoord 目标点
     * @return 返回两个节点的欧式距离
     */

    public static double distBetweenPoints(Coordinate sCoord, Coordinate eCoord){
        int deltaX = Math.abs(sCoord.getX()-eCoord.getX());
        int deltaY = Math.abs(sCoord.getY()-eCoord.getY());
        return Math.sqrt(deltaX*deltaX + deltaY*deltaY);
    }

    /**
     *
     * @param ans 输入地图
     * @param coordinate 起点或者终点
     * @return 返回输入地图中道路点距离给的点最近的点坐标
     */
    public Coordinate getShortestPoint(float[][] ans,Coordinate coordinate){
        double minDistance = Double.MAX_VALUE;
        ArrayList<Coordinate> coordinates = takePathPixel(ans);
        Coordinate nearCoordinate = coordinates.get(0);
        for (Coordinate node : coordinates) {
            double distance = distBetweenPoints(coordinate, node);
            if (distance<minDistance){
                minDistance = distance;
                nearCoordinate = node;
            }
        }
        return nearCoordinate;
    }
    /**
     *
     * @param ans 赋值后的环境地图
     * @return 返回是道路的栅格坐标点
     */
    public ArrayList<Coordinate> takePathPixel(float[][] ans){
        return getCoordinates(ans);
    }

    static ArrayList<Coordinate> getCoordinates(float[][] ans) {
        ArrayList<Coordinate> arrayList = new ArrayList<>();
        for (int i = 0; i < ans.length; i++) {
            for (int j = 0; j < ans[0].length; j++) {
                if (ans[i][j] == 1){
                    arrayList.add(new Coordinate(i,j));
                }
            }
        }
        return arrayList;
    }
}
