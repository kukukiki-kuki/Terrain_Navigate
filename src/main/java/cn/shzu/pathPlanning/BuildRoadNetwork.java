package cn.shzu.pathPlanning;

import cn.shzu.utils.ReadTXTAsArray;

import java.util.ArrayList;
import java.util.List;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 构建路网
 * @date 2024/5/30 21:52:49
 */
public class BuildRoadNetwork {
    private static final int WIDTH = 1071;
    private static final int HEIGHT = 704;
    private double[][] grid = new double[HEIGHT][WIDTH]; // 假设已初始化

    public static void main(String[] args) {
    }
    public void process(){
      grid =  ReadTXTAsArray.process("",1);
      List<int[]> ints = selectKeyPoints(0.4);
    }

    //选取栅格点中的重要节点作为拓扑点
    public List<int[]> selectKeyPoints(double threshold) {
        List<int[]> keyPoints = new ArrayList<>();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (grid[i][j] > threshold) {
                    keyPoints.add(new int[]{i, j});
                }
            }
        }
        return keyPoints;
    }
    //使用A*算法对关键节点之间进行路径规划构建路网

}
