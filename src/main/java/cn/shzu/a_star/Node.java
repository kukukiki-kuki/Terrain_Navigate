package cn.shzu.a_star;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description point in the map
 * @date 2023/11/14 22:49:31
 */
public class Node implements Comparable<Node>{
    private Coordinate coordinate;
    private Double heuristicCost = 0.0;
    private Double goalCost = 0.0;
    private Node parentNode;

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Double getHeuristicCost() {
        return heuristicCost;
    }

    public void setHeuristicCost(Double heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    public Double getGoalCost() {
        return goalCost;
    }

    public void setGoalCost(Double goalCost) {
        this.goalCost = goalCost;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public Node(Coordinate coordinate, Double heuristicCost, Double goalCost, Node parentNode) {
        this.coordinate = coordinate;
        this.heuristicCost = heuristicCost;
        this.goalCost = goalCost;
        this.parentNode = parentNode;
    }
    public Node(int x_position,int y_position){
        this.coordinate = new Coordinate(x_position,y_position);
    }


    @Override
    public int compareTo(Node o) {
        if (o == null) return -1;
        if (this.getHeuristicCost() + this.getGoalCost() > o.getGoalCost()+ o.getHeuristicCost()){
            return 1;
        }
        else if (this.getGoalCost()+ this.getHeuristicCost() < o.getGoalCost()+ o.getHeuristicCost()){
            return -1;
        }
        return 0;
    }
}
