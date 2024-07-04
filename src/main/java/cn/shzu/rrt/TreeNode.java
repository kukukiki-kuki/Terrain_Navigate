package cn.shzu.rrt;

import cn.shzu.utils.Coordinate;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description tree node for search
 * @date 2023/12/8 16:38:50
 */
public class TreeNode {
    private Coordinate coordinate;
    private TreeNode parentNode;

    public TreeNode(Coordinate coordinate, TreeNode parentNode) {
        this.coordinate = coordinate;
        this.parentNode = parentNode;
    }

    public TreeNode(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public TreeNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(TreeNode parentNode) {
        this.parentNode = parentNode;
    }

}
