package cn.shzu.rrt;

import cn.shzu.a_star.Coordinate;

import javax.swing.*;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description tree struct
 * @date 2023/12/8 16:37:52
 */
public class TreeStruct {
    private SearchSpace searchSpace;
    private ArrayList<TreeNode> nodeList;
    private TreeNode startNode, goalNode;
    private int nodeCount,r,maxNodes;
    private double prc;
    private int [][] dimension;

    public TreeStruct(TreeNode startNode,TreeNode goalNode){
        this.nodeCount = 0;
        nodeList = new ArrayList<>();
        this.searchSpace = new SearchSpace();
        float[][] map = searchSpace.getMap();
        this.dimension = new int[][]{{0,map.length},{0,map[0].length}};
        this.startNode = startNode;
        this.goalNode = goalNode;
        this.r = 1;
        this.prc = 0.1;
        this.maxNodes = 1024;

    }
    /*
    添加节点
     */
    public void addNode(TreeNode newNode,TreeNode parentNode){
        newNode.setParentNode(parentNode);
        nodeList.add(newNode);
        nodeCount++;
    }

    /**
     * 添加节点、边集
     * @param childNode 新增节点
     * @param parentNode 新增节点的父节点
     */
    public void addVertex( TreeNode childNode,TreeNode parentNode){
        childNode.setParentNode(parentNode);
        nodeList.add(childNode);
        nodeCount++;
    }

    private float calcDistance(int nearestX,int nearestY,int nodeX, int nodeY){
        return (float) Math.sqrt(Math.pow(nodeX - nearestX,2)+Math.pow(nodeY-nearestY,2));
    }

    /**
     * 获取最近节点
     * @param node 根据node计算最近节点
     * @return 返回离node最近的节点
     */
    public TreeNode getNearestNode(TreeNode node){
        TreeNode nearestNode = startNode;
        float shortestDistance = calcDistance(nearestNode.getCoordinate().getX(), nearestNode.getCoordinate().getY(), node.getCoordinate().getX(), node.getCoordinate().getY());
        for(TreeNode currentNode : nodeList){
            float temp = calcDistance(currentNode.getCoordinate().getX(), currentNode.getCoordinate().getY(), node.getCoordinate().getX(), node.getCoordinate().getY());
            if(temp < shortestDistance){
                nearestNode = currentNode;
                shortestDistance = temp;
            }
        }
        return nearestNode;
    }

    /**
     *
     * @param node 元节点
     * @param n 返回的元节点最近的节点个数
     * @return 返回离元节点最近的n个节点
     */
    public ArrayList<TreeNode> nearBy(TreeNode node,int n){
        ArrayList<NodeDistance> distance = new ArrayList<>();
        TreeNode nearestNode = startNode;
        for ( TreeNode treeNode:nodeList) {
            float dis = calcDistance(treeNode.getCoordinate().getX(),treeNode.getCoordinate().getY(), node.getCoordinate().getX(),node.getCoordinate().getY());
            NodeDistance nodeDistance = new NodeDistance(treeNode, dis);
            distance.add(nodeDistance);
        }
        distance.sort(Comparator.comparingDouble(NodeDistance::getDistance));
        ArrayList<TreeNode> nearList = new ArrayList<>();
        for (int i = 0; i < Math.min(n,distance.size()); i++) {
            nearList.add(distance.get(i).getNode());
        }
        return nearList;
    }

    public boolean isInNodeList(TreeNode node){
        for (TreeNode n: nodeList) {
            if (node.getCoordinate()==n.getCoordinate()){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param q 步长
     * @return 返回newNode 和 nearestNode
     */
    public ArrayList<TreeNode> getNewAndNear(int q){
        TreeNode randNode = searchSpace.sampleFree();
        TreeNode nearestNode =  getNearestNode(randNode);
        TreeNode newNode = boundPoint(steer(nearestNode,randNode,q));
        //验证newNode是否已经存在树中
        if (isInNodeList(newNode)||searchSpace.isObstacle(newNode.getCoordinate())){
            return null;
        }
        nodeCount++;
        ArrayList<TreeNode> treeNodes = new ArrayList<>();
        treeNodes.add(newNode);
        treeNodes.add(nearestNode);
        return  treeNodes;
    }

    /**
     *
     * @param node 节点
     * @param node1 节点
     * @return 返回是否能连接节点
     */
    public boolean connectToPoint(TreeNode node,TreeNode node1){

        if (!isInNodeList(node1)&& searchSpace.collisionFree(node,node1,r)){
            addNode(node1,node);
            return  true;
        }else
            return false;
    }

    /**
     *
     * @return 返回最终节点是否被连接
     */
    public boolean canConnectToGoal(){
        TreeNode nearestNode = getNearestNode(goalNode);
        if (isInNodeList(goalNode)&& goalNode.getParentNode()==nearestNode){
            return true;
        }
        if (searchSpace.collisionFree(nearestNode,goalNode,r)){
            return true;
        }
        return false;
    }

    /**
     *
     * @return 路径是否可行
     */
    public ArrayList<TreeNode> getPath(){
        if (canConnectToGoal()){
            System.out.println("连接终点");
            connectToGoal();
            return reconstructPath(startNode,goalNode);
        }else {
            System.out.println("无法连接终点");
        }
        return null;
    }

    /**
     *
     * @param initNode 初始节点
     * @param goalNode 目标节点
     * @return 返回路径
     */
    public ArrayList<TreeNode> reconstructPath(TreeNode initNode, TreeNode goalNode){
        ArrayList<TreeNode> pathNodes = new ArrayList<>();
        pathNodes.add(goalNode);
        TreeNode current = goalNode;
        if (initNode==goalNode){
            return pathNodes;
        }
        while (!(current.getParentNode()==initNode)){
            pathNodes.add(current.getParentNode());
            current = current.getParentNode();
        }
        pathNodes.add(initNode);
        return pathNodes;

    }

    /**
     * 将目标节点最近的节点设置为目标节点的父节点
     */
    public void connectToGoal(){
        TreeNode nearestNode = getNearestNode(goalNode);
        goalNode.setParentNode(nearestNode);
    }

    public ArrayList<TreeNode> checkSolution(){
        Random random = new Random();

        if (prc>0 && random.nextDouble()<prc) {
            System.out.println("查验是否可以连接到目标节点" + " 节点数为" + nodeCount);
            ArrayList<TreeNode> path = getPath();
            if (path != null) {
                return path;
            }
        }
        if (nodeCount>=maxNodes){
            return getPath();
        }
        return null;
    }

    /**
     * 检查是否在边界 范围内
     * @param coordinate 坐标点
     * @return 检查过后的坐标点位置的新建节点
     */
    public TreeNode boundPoint(Coordinate coordinate){
        //如果点超过边界设置为边界
       coordinate.setX(Math.max(coordinate.getX(),dimension[0][0]));
       coordinate.setX(Math.min(coordinate.getX(),dimension[0][1]));
       coordinate.setY(Math.max(coordinate.getY(),dimension[1][0]));
       coordinate.setY(Math.min(coordinate.getY(),dimension[1][1]));
       return new TreeNode(coordinate);
    }

    /**
     *
     * @param startNode 起始点
     * @param goalNode 终点
     * @param distance 步长
     * @return 生成沿着目标方向的单位距离点
     */
    public static Coordinate  steer(TreeNode startNode,TreeNode goalNode,double distance){
        int deltaX = goalNode.getCoordinate().getX() - startNode.getCoordinate().getX();
        int deltaY = goalNode.getCoordinate().getY() - startNode.getCoordinate().getY();
        double magnitude = Math.sqrt(deltaX*deltaX + deltaY*deltaY);
        double unitX = deltaX/magnitude;
        double unitY = deltaY/magnitude;
        int steerX = (int)(startNode.getCoordinate().getX()+unitX*distance);
        int steerY = (int)(startNode.getCoordinate().getY()+unitY*distance);
        return new Coordinate(steerX,steerY);
    }





    public TreeNode getStartNode() {
        return startNode;
    }

    public void setStartNode(TreeNode startNode) {
        this.startNode = startNode;
    }

    public TreeNode getGoalNode() {
        return goalNode;
    }

    public void setGoalNode(TreeNode goalNode) {
        this.goalNode = goalNode;
    }

    public ArrayList<TreeNode> getNodeList() {
        return nodeList;
    }

    public void setNodeList(ArrayList<TreeNode> nodeList) {
        this.nodeList = nodeList;
    }
}
