package cn.shzu.intelligentAlgorithm;

import java.util.HashMap;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 混沌映射
 * @date 2024/4/29 11:58:50
 */
public class ChaoticMapping {
    public static void main(String[] args) {
        ChaoticMapping chaoticMapping = new ChaoticMapping();
        chaoticMapping.chaotic();
    }
    public void chaotic(){
        // 设置边界下限和上限
        double xt_min = 0.0;
        double xt_max = 1.0;

        // 生成混沌序列Zt
        double[] zt = generateChaosSequence(1000, 3.6, 0.5);

        // 对混沌序列进行Tent映射
        for (int i = 0; i < zt.length; i++) {
            double xt = tentMapping(xt_min, xt_max, zt[i]);
            System.out.println("Iteration " + i + ": " + xt);
        }
    }

    // Tent映射函数
    public static double tentMapping(double xt_min, double xt_max, double zt) {
        return xt_min + (xt_max - xt_min) * zt;
    }

    // 生成混沌序列Zt，这里使用Logistic映射作为混沌序列生成算法
    public static double[] generateChaosSequence(int iterations, double r, double initial) {
        double[] zt = new double[iterations];
        double x = initial;
        for (int i = 0; i < iterations; i++) {
            x = r * x * (1 - x);
            zt[i] = x;
        }
        return zt;
    }


}
