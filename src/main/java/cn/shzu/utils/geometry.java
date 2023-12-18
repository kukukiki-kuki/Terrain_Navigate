package cn.shzu.utils;

import cn.shzu.a_star.Coordinate;
import cn.shzu.rrt.TreeNode;
import cn.shzu.rrt.TreeStruct;

import java.util.Collection;
import java.util.Collections;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description some geometry function
 * @date 2023/12/9 12:43:10
 */


public class geometry {
    private static TreeStruct treeStruct;
    /**
     *
     * @param sNode 起始点
     * @param eNode 目标点
     * @return 返回两个节点的欧式距离
     */

    public static double distBetweenPoints(TreeNode sNode,TreeNode eNode){
        int deltaX = Math.abs(eNode.getCoordinate().getX()-sNode.getCoordinate().getX());
        int deltaY = Math.abs(eNode.getCoordinate().getY()-sNode.getCoordinate().getY());
        return Math.sqrt(deltaX*deltaX + deltaY*deltaY);
    }

    /**
     *
     * @param sNode 起始点
     * @param eNode 目标点
     * @param r 最大距离限制
     * @return
     */
    public static Iterable<TreeNode> esPointsAlongLine(TreeNode sNode,TreeNode eNode,int r){
        double distance = distBetweenPoints(sNode,eNode);
        int nPoints = (int) Math.ceil(distance/r);
        if (nPoints>1){
            double step = distance/(nPoints-1);
            return() -> new java.util.Iterator<TreeNode>(){
                int currentStep = 0;
                @Override
                public boolean hasNext() {
                    return currentStep<nPoints;
                }

                @Override
                public TreeNode next() {
                    Coordinate coordinate = TreeStruct.steer(sNode,eNode,currentStep*step);
                    TreeNode treeNode = new TreeNode(coordinate);
                    currentStep++;
                    return treeNode;
                }
            };
        }else {
            return Collections::emptyIterator;
        }
    }

    /**
     *
     * @param sNode 起始点
     * @param eNode 目标点
     * @return 返回两点的欧式距离
     */
    public static float distanceBetweenNode(TreeNode sNode, TreeNode eNode){
        Coordinate sCoord = sNode.getCoordinate();
        Coordinate eCoord = eNode.getCoordinate();
        return (float) Math.sqrt(Math.pow(sCoord.getX()-eCoord.getX(),2)+Math.pow(sCoord.getY() - eCoord.getY(),2));
    }
}
