package cn.shzu.rrt;

import cn.shzu.utils.Coordinate;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description activation class
 * @date 2023/12/10 12:32:22
 */
public class ActivationClass {


    private void doRRT(){
        Coordinate coordinateStart = new Coordinate(23,45);
        Coordinate coordinateGoal = new Coordinate(678,345);
        TreeNode startNode = new TreeNode(coordinateStart);
        TreeNode goalNode = new TreeNode(coordinateGoal);
        RRTBase rrtBase = new RRTBase(startNode, goalNode);
        rrtBase.rrtSearch();
    }

    private void doRRTStar(){
        Coordinate coordinateStart = new Coordinate(23,45);
        Coordinate coordinateGoal = new Coordinate(837,845);
        TreeNode startNode = new TreeNode(coordinateStart);
        TreeNode goalNode = new TreeNode(coordinateGoal);
        RRTStar rrtStar = new RRTStar(startNode, goalNode);
        rrtStar.rrtStarSearch();
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        ActivationClass activationClass = new ActivationClass();
        activationClass.doRRT();
        System.out.println("执行时间"+(System.currentTimeMillis() - startTime));

    }
}
