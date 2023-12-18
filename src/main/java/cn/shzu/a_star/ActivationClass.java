package cn.shzu.a_star;


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
        Node s = new Node(11,3);
        Node g = new Node(800,800);
        String DemFile = "D:\\ArcGis\\Project\\Big\\p10627dsm_Resample1_Clip.tif";
        String SlopeFile = "D:\\ArcGis\\Project\\Big\\Slope_p106272.tif";
        long startTime = System.currentTimeMillis();
        MapInformation mapInformation = new MapInformation(DemFile,SlopeFile,s,g);
        long readMapTime = System.currentTimeMillis() - startTime;
        System.out.println("读取地图时间:"+(readMapTime));
        new AStar().start(mapInformation);
        System.out.println();
        System.out.println("执行时间:"+(System.currentTimeMillis() - startTime-readMapTime));
    }
}
