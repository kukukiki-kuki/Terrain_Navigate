package cn.shzu.rrt;

import cn.shzu.utils.geometry;

import java.util.*;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description rrt star
 * @date 2023/12/11 18:20:13
 */
public class RRTStar {
    private float cBest;
    private int rewireCount;
    private TreeStruct treeStruct;
    private int [] Q = {8,4};
    private SearchSpace searchSpace;
    private int r =1;
    private TreeNode startNode,goalNode;

    public RRTStar(TreeNode startNode, TreeNode goalNode) {
        this.treeStruct = new TreeStruct(startNode,goalNode);
        this.cBest = Float.MAX_VALUE;
        this.searchSpace = new SearchSpace();
        this.startNode = startNode;
        this.goalNode = goalNode;
    }

    /**
     *
     * @return 返回重布线顶点数量
     */
    public int  currentRewireCount(){
        if (rewireCount==0){
            return treeStruct.getNodeList().size();
        }else {
            return Math.min(treeStruct.getNodeList().size(),rewireCount);
        }
    }

    /**
     *
     * @param startNode 起点
     * @param newNode 新节点
     * @return 返回newNode的周围距离最近的n个节点的通行代价
     */
    public TreeMap<TreeNode,Float> getNearByVertices(TreeNode startNode, TreeNode newNode){
        ArrayList<TreeNode> treeNodes = treeStruct.nearBy(newNode,currentRewireCount());
        Map<TreeNode,Float> nodeTreeMap= new HashMap<>();
        for (TreeNode node:treeNodes) {
            float dis =  Heuristics.pathCost(startNode,node) + Heuristics.segmentCost(node,newNode);
            nodeTreeMap.put(node,dis);
        }
        Comparator<TreeNode> distComparator = Comparator.comparingDouble(nodeTreeMap::get);
        //创建TreeMap并将键值放入其中，使用Comparator作为比较器
        TreeMap<TreeNode, Float> treeNodeFloatTreeMap = new TreeMap<>(distComparator);
        treeNodeFloatTreeMap.putAll(nodeTreeMap);
        //验证是否排序
        for (Map.Entry<TreeNode,Float> entry : treeNodeFloatTreeMap.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
        return treeNodeFloatTreeMap;
    }

    /**
     *
     * @param startNode 起点
     * @param newNode 新点
     * @param treeMap 一系列点
     */
    public void rewire(TreeNode startNode,TreeNode newNode,TreeMap<TreeNode,Float> treeMap){
        for(Map.Entry<TreeNode,Float> entry: treeMap.entrySet()){
            float currentCost = Heuristics.pathCost(startNode,entry.getKey());
            float tentCost = Heuristics.pathCost(startNode,newNode) + Heuristics.segmentCost(newNode,entry.getKey());
            if ((tentCost<currentCost) && searchSpace.collisionFree(entry.getKey(),newNode,r)){
                treeStruct.addNode(newNode,entry.getKey());
            }
        }
    }

    /**
     *
     * @param newNode 新节点
     * @param goalNode 终点
     * @param treeMap 一系列点
     */
    public void connectShortestValid(TreeNode newNode,TreeNode goalNode,TreeMap<TreeNode,Float> treeMap){
        for (Map.Entry<TreeNode,Float> entry: treeMap.entrySet()){
            if ((entry.getValue() + Heuristics.costTogo(entry.getKey(),goalNode))< cBest && treeStruct.connectToPoint(entry.getKey(),newNode)){
                break;
            }
        }
    }

    public void rrtStarSearch(){
        long startTime = System.currentTimeMillis();
        treeStruct.addNode(startNode,null);
        ArrayList<TreeNode> nodes ;
        while (true){
            ArrayList<TreeNode> newAndNear = treeStruct.getNewAndNear(Q[0]);
            if (newAndNear ==null){
                continue;
            }
            TreeNode newNode = newAndNear.get(0);
            TreeMap<TreeNode, Float> nearByVertices = getNearByVertices(startNode,newNode);
            connectShortestValid(newNode,goalNode,nearByVertices);
            if (treeStruct.isInNodeList(newNode)){
                rewire(startNode,newNode,nearByVertices);
            }
            ArrayList<TreeNode> path = treeStruct.checkSolution();
            nodes = path;
            if (path != null){
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

