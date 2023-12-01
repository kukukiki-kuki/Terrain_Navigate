package cn.shzu.a_star;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description point in the map
 * @date 2023/11/14 22:49:31
 */
public class Node {
    private Coordinate coordinate;
    private Integer heuristicCost;
    private Integer goalCost;
    private Node parentNode;

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Integer getHeuristicCost() {
        return heuristicCost;
    }

    public void setHeuristicCost(Integer heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    public Integer getGoalCost() {
        return goalCost;
    }

    public void setGoalCost(Integer goalCost) {
        this.goalCost = goalCost;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public Node(Coordinate coordinate, Integer heuristicCost, Integer goalCost, Node parentNode) {
        this.coordinate = coordinate;
        this.heuristicCost = heuristicCost;
        this.goalCost = goalCost;
        this.parentNode = parentNode;
    }
    public Node(int x_position,int y_position){
        this.coordinate = new Coordinate(x_position,y_position);
    }



}
