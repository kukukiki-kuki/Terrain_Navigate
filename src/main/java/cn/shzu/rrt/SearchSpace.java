package cn.shzu.rrt;

import cn.shzu.a_star.Coordinate;
import cn.shzu.utils.DEMReader;
import cn.shzu.utils.geometry;

import java.util.Random;


/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description rrt search space
 * @date 2023/12/8 16:26:52
 */
public class SearchSpace {
    private int SpaceWidth;
    private int SpaceHeight;
    private float [][] map;

    public SearchSpace(){
        float[][] map = DEMReader.readDEM("D:\\ArcGis\\Project\\Big\\Slope_p106272.tif");
        map = mapFragment(map);
        this.map = map;
        this.SpaceWidth = map.length;
        this.SpaceHeight = map[0].length;
    }
//    public void check(float [][] map){
//        int [][] map1 = new int [500][500];
//        for (int i = 0; i < 500; i++) {
//            for (int j = 0; j < 500; j++) {
//                if (map[i][j]<=25){
//                    map1[i][j] = 0;
//                }else{
//                    map1[i][j] = 1;
//                }
//            }
//        }
//        for (int i = 0; i < 500; i++) {
//            for (int j = 0; j < 500; j++) {
//                System.out.print(map1[i][j]+" ");
//            }
//            System.out.println();
//        }
//    }
    public float[][] mapFragment(float[][] originMap){
        float[][] subMap = new float[1000][1000];
        for (int i = 0; i < 1000; i++) {
            System.arraycopy(originMap[i], 0, subMap[i], 0, 1000);
        }
        return subMap;
    }
    public float[][] getMap() {
        return map;
    }

    public void setMap(float[][] map) {
        this.map = map;
    }

    /*
        判断是否是障碍物,是障碍物返回ture
         */
    public boolean isObstacle(int x,int y){
        return true;
    }
    public boolean isObstacle(Coordinate coordinate){
        int x = coordinate.getX();
        int y = coordinate.getY();
        return map[x][y] >= 25;
    }
    /*
    随机采样
    */
    public TreeNode sampleFree(){
        while (true){
            int []coords =  sample();
            if (isObstacle(coords[0],coords[1])){
                return new TreeNode(new Coordinate(coords[0],coords[1]));
            }
        }
    }

    public int [] sample(){
        Random random = new Random();
        int x = random.nextInt(SpaceWidth);
        int y = random.nextInt(SpaceHeight);
        return new int[]{x, y};
    }

    /**
     *
     * @param sNode 起始点
     * @param eNode 目标点
     * @param r 最大限制距离
     * @return 返回整条线是否有阻碍
     */
    public boolean collisionFree(TreeNode sNode,TreeNode eNode,int r){
        Iterable<TreeNode> treeNodes = geometry.esPointsAlongLine(sNode, eNode, r);
        for (TreeNode node: treeNodes) {
            if (isObstacle(node.getCoordinate())){
                return false;
            }
        }
        return true;
    }


}
