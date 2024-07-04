package cn.shzu.rrt;

import cn.shzu.utils.Coordinate;
import cn.shzu.function.geometry;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description heuristics function
 * @date 2023/12/11 21:49:53
 */
public class Heuristics {
    public static float pathCost(TreeNode sNode,TreeNode eNode){
        TreeNode tempNode;
        float cost = 0;
        while (eNode!=sNode){
            tempNode = eNode.getParentNode();
            cost += geometry.distanceBetweenNode(tempNode,eNode);
            eNode = tempNode;

        }
        return cost;
    }

    public static float segmentCost(TreeNode sNode,TreeNode eNode){
        return  geometry.distanceBetweenNode(sNode,eNode);
    }

    public static float costTogo(TreeNode node,TreeNode node1){
        return geometry.distanceBetweenNode(node,node1);
    }
}
