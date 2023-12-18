package cn.shzu.rrt;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description node distance
 * @date 2023/12/11 21:35:15
 */
public class NodeDistance {
    private TreeNode node;
    private float distance;

    public TreeNode getNode() {
        return node;
    }

    public void setNode(TreeNode node) {
        this.node = node;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public NodeDistance(TreeNode node, float distance) {
        this.node = node;
        this.distance = distance;
    }
}
