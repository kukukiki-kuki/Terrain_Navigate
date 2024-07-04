package cn.shzu.intelligentAlgorithm;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 模拟退火算法模拟
 * @date 2024/4/29 13:59:55
 */
import cn.shzu.fuzzyLogic.BaseArray;
import cn.shzu.utils.CalculateBaseFunction;

import java.util.Arrays;
import java.util.Random;

public class SimulatedAnnealing {
    static int populationSize;
    static double initialTemperature = 1000; // 初始温度
    static double coolingRate = 0.03; // 降温率
    static int numIterations = 500; // 迭代次数
    static double terminationTemperature = 0.1;//终止温度
    static int dimension = 81;


    // 模拟退火算法
    public static int[] simulatedAnnealing(double initialTemperature, double coolingRate, int numIterations,int[][] location) {
        Random random = new Random();
        double temperature = initialTemperature; // 初始温度
        //初始解
        populationSize = location.length;
        int bestIndex = random.nextInt(populationSize);
        int [] bestLocation = location[bestIndex];
        for (int i = 0; i < numIterations; i++) {
            System.out.println("这是第"+(i+1)+"轮");
            //生成一个领域解
            int [] newLocation = new int[dimension];
            int [] deviation = {-1,1,-2,2};
            int random1 = random.nextInt(81);
            int random2 = random.nextInt(81);
            for (int j = 0; j < dimension; j++) {
                newLocation[j] = bestLocation[j];
            }
            int i1 = newLocation[random1]+deviation[random.nextInt(4)];
            newLocation[random1] =restrict(i1);
            int i2 = newLocation[random2]+deviation[random.nextInt(4)];
            newLocation[random2] = restrict(i2);
            //计算并比较适应度函数值
            ConstructFuzzyRules.constructRules(bestLocation);
            double fitness1 = CalculateBaseFunction.calculateFuzzyError(81);

            ConstructFuzzyRules.constructRules(newLocation);
            double fitness2 = CalculateBaseFunction.calculateFuzzyError(81);
            if (fitness2<=fitness1){
                bestLocation = newLocation;
            }else {
                double p = Math.exp(-(fitness2-fitness1)/temperature);
                double randomValue = random.nextDouble();
                if (randomValue<p){
                    //接受新解
                    bestLocation = newLocation;
                }
            }
            temperature *= 1 - coolingRate; // 降低温度
        }
        return bestLocation;
    }

    public static int restrict(int i){
        if (i<0){
            return 0;
        }
        return Math.min(i, 3);
    }
    public static void main(String[] args) {
        int [][] ints = new int[2][81];
        for (int i = 0; i < 2; i++) {
            ints[i] = BaseArray.getArrayS(i);
        }
        int[] bestSolution = simulatedAnnealing(initialTemperature, coolingRate, numIterations,ints);
        System.out.println("最优解: " + Arrays.toString(bestSolution));
    }
}
