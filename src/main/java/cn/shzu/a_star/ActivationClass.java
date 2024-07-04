package cn.shzu.a_star;


import cn.shzu.utils.MapConstruction;
import cn.shzu.utils.Node;

import java.time.Duration;


/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description activate class
 * @date 2023/11/16 22:18:47
 */
public class ActivationClass {

    public static void main(String[] args) {
        Node s = new Node(990, 341);
        Node g = new Node(101,101);
//        String DemFile = "D:\\ArcGis\\Project\\Big\\p10627dsm_Resample1_Clip.tif";
        String SlopeFile = "D:\\Data\\TerrainApplication\\app\\src\\main\\assets\\TerrainApplication\\Data\\Slope_p106273.tif";
        long startTime = System.currentTimeMillis();
        MapConstruction mapConstruction = new MapConstruction(SlopeFile, s, g);
        long readMapTime = System.currentTimeMillis() - startTime;
        System.out.println("读取地图时间:"+(readMapTime));
        new AStar().start(mapConstruction);
        System.out.println("执行时间:"+(System.currentTimeMillis() - startTime-readMapTime)+"ms");
    }
}
