package cn.shzu.a_star;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description activate class
 * @date 2023/11/16 22:18:47
 */
public class ActivationClass {

    public static void main(String[] args) {
        Node s = new Node(2,3);
        Node g = new Node(2000,3000);
        String file = "D:\\ArcGis\\Project\\Big\\p10627dsm_Resample1_1.tif";
        MapInformation mapInformation = new MapInformation(file,s,g);
        new AStar().start(mapInformation);
    }
}
