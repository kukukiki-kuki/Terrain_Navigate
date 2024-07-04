package cn.shzu.transitCost;

import cn.shzu.function.DEMReader;
import cn.shzu.utils.CalculateBaseFunction;
import cn.shzu.utils.ReadTXTAsArray;
import cn.shzu.utils.RecordExperimentalResults;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 构造权重地图
 * @date 2024/5/9 14:39:40
 */
public class ConstructWeightMap {
    double[] friction = {0.7, 0.85,0.3,0.1,0.55,0.25};
    double[] mellowness = {0.6, 0.9,0.35,0,0.5,0.15};
    double[][] weight =new double [10713][7047];
    double [] c = new double[4];
    public static void main(String[] args) {
        ConstructWeightMap constructWeightMap = new ConstructWeightMap();
        constructWeightMap.processMain();
    }
    public void processMain(){
        //读取地图的坡度
        float [][] slope = DEMReader.readDEM("F:\\Data\\ArcgisProject\\MyProject1\\Slope_Extrac1.tif");
        System.out.println(slope[1023][1045]);
        //读取地图的起伏度
        float[][] fluctuation = DEMReader.readDEM("F:\\Data\\ArcgisProject\\MyProject1\\focals_raste.tif");
        System.out.println(fluctuation[1023][1045]);
        //读取地图覆盖物类别
        int [][] features = ReadTXTAsArray.process("D:\\Data\\pythonData\\tailorTiff\\matrix.txt");
        System.out.println(features[1023][1045]);
        //根据对照表得到摩擦度系数
        double[][] f = CalculateBaseFunction.getFriction(features, friction);
        System.out.println(f[1023][1045]);
        //根据对照表得到松软度系数
        double[][] m = CalculateBaseFunction.getFriction(features, mellowness);
        System.out.println(m[1023][1045]);
        //调用模糊逻辑方法，得到权重地图

        for (int i = 0; i < weight.length; i++) {
            for (int j = 0; j < weight[0].length; j++) {
              weight[i][j] = CalculateBaseFunction.fuzzyLogicCase(slope[i][j],fluctuation[i][j],f[i][j],m[i][j]);
            }
        }
        RecordExperimentalResults.writeArrayToFile(weight,"weight");
    }


}
