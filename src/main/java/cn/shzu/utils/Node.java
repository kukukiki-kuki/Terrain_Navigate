package cn.shzu.utils;

import cn.shzu.utils.Coordinate;

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
    private Double araWeight = 1.0;
    private Node parentNode;
    private Double fCost = 0.0;
    private Double terrainCost = 0.0;

    public Node(Coordinate coordinate, Double heuristicCost, Double goalCost, Double araWeight, Node parentNode, Double fCost) {
        this.coordinate = coordinate;
        this.heuristicCost = heuristicCost;
        this.goalCost = goalCost;
        this.araWeight = araWeight;
        this.parentNode = parentNode;
        this.fCost = fCost;
    }
    public Node(Coordinate coordinate, Double heuristicCost, Double goalCost, Double araWeight, Node parentNode) {
        this.coordinate = coordinate;
        this.heuristicCost = heuristicCost;
        this.goalCost = goalCost;
        this.araWeight = araWeight;
        this.parentNode = parentNode;
    }
    public Node(Coordinate coordinate, Double heuristicCost, Double goalCost, Double araWeight, Node parentNode, Double[][] map) {
        this.coordinate = coordinate;
        this.heuristicCost = heuristicCost;
        this.goalCost = goalCost;
        this.araWeight = araWeight;
        this.parentNode = parentNode;
        this.terrainCost = map[coordinate.getX()][coordinate.getY()];
    }

    public Double getFCost() {
        return fCost;
    }

    public void setFCost() {
        this.fCost = this.goalCost+this.araWeight*this.heuristicCost;
    }

    public Double getAraWeight() {
        return araWeight;
    }

    public void setAraWeight(Double araWeight) {
        this.araWeight = araWeight;
    }

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


    public Node(int x_position, int y_position){
        this.coordinate = new Coordinate(x_position,y_position);
    }



    @Override
    public int compareTo(Node o) {
        if (o == null) return -1;
        return Double.compare(this.getFCost(), o.getFCost());
    }

}
