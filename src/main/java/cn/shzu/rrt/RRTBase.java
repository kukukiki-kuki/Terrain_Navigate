package cn.shzu.rrt;

import cn.shzu.a_star.Coordinate;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description RRTBase main
 * @date 2023/12/8 16:02:59
 */
public class RRTBase {
    private TreeStruct treeStruct;
    private TreeNode startNode, goalNode;
    private int [] Q = {1,4};

    public RRTBase(TreeNode startNode,TreeNode goalNode){
        this.goalNode = goalNode;
        this.startNode = startNode;
        this.treeStruct = new TreeStruct(startNode,goalNode);

    }


    public void rrtSearch(){
        treeStruct.addNode(startNode,null);
        long startTime = System.currentTimeMillis();
        ArrayList<TreeNode> nodes ;
        while (true){
                ArrayList<TreeNode> treeNodes= treeStruct.getNewAndNear(Q[0]);
                if (treeNodes == null){
                    continue;
                }
                TreeNode newNode = treeNodes.get(0);
                TreeNode nearestNode = treeNodes.get(1);
                treeStruct.connectToPoint(nearestNode,newNode);
                ArrayList<TreeNode> path = treeStruct.checkSolution();
                nodes = path;
                if (path!=null){
                    for (TreeNode node : path) {
                        System.out.println(Arrays.toString(node.getCoordinate().getLocation()));
                    }
                    break;
                }else {
                    System.out.println("无法找到路径");
                }
        }
        System.out.println(System.currentTimeMillis() - startTime);
        System.out.println(nodes.size());
    }
}
