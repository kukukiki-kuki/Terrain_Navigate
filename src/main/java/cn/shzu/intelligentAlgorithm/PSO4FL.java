package cn.shzu.intelligentAlgorithm;

import cn.shzu.fuzzyLogic.BaseArray;
import cn.shzu.utils.CalculateBaseFunction;
import cn.shzu.utils.RecordExperimentalResults;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 粒子群算法
 * @date 2024/4/22 16:07:48
 */
public class PSO4FL {
    private static final int SWARM_SIZE = 10; // 粒子群大小
    private static final int MAX_ITERATIONS = 1000; // 最大迭代次数
    private static final double C1 = 2.0; // 学习因子1
    private static final double C2 = 2.0; // 学习因子2
    private static final double INERTIA_WEIGHT = 0.7; // 惯性权重

    private static final int DIMENSION = 81; // 问题维度
    private static final int LOWER_BOUND = 0; // 问题范围下界
    private static final int UPPER_BOUND = 3; // 问题范围上界

    private static int[][] swarm; // 粒子群
    private static double[] velocity; // 速度
    private static int[] pBest; // 个体最优解
    private static int[] gBest; // 全局最优解
    private static  double bestFitness = Double.MAX_VALUE; //最优解值
    static  double [] fitnessValue = new double[SWARM_SIZE]; //解值
    static  double [] bestFval = new double[MAX_ITERATIONS]; //最优解值

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        initializeSwarm();

        for (int i = 0; i < MAX_ITERATIONS; i++) {
            int num = i+1;
            System.out.println("开始第"+ num +"轮");
            System.out.println("最优解："+ bestFitness);
            updateVelocityAndPosition();
            updatePersonalBest();
            updateGlobalBest();
            bestFval[i] = bestFitness;
        }

        System.out.println("Global best solution: " + arrayToString(gBest));
        System.out.println("Global bestFitness: " + bestFitness);
        System.out.println("Global bestFval: " + Arrays.toString(bestFval));
        long end = System.currentTimeMillis();
        System.out.println("程序执行时间:"+(end-start)/1000+"s");
        long time = (end-start)/1000;
        //记录解
        RecordExperimentalResults.writeArrayToFile(gBest,bestFitness,"PSO",3, bestFval,time,String.valueOf(end));
        //
    }


    private static void initializeSwarm() {
        swarm = new int[SWARM_SIZE][DIMENSION];
        velocity = new double[DIMENSION];
        pBest = new int[DIMENSION];
        gBest = new int[DIMENSION];

        Random random = new Random();
        for (int i = 0; i < SWARM_SIZE; i++) {
            for (int j = 0; j < DIMENSION; j++) {
//                swarm[i][j] = random.nextInt(4) ;
                velocity[j] = random.nextDouble();
            }
        }
//        for (int i = 0; i < SWARM_SIZE; i++) {
//            swarm[i] = BaseArray.getArrayS(i);
//        }
        List<Solution> solutions = PopulationInitialization.initializePopulation(SWARM_SIZE, DIMENSION);
        //佳点集初始化
        for (int i = 0; i < SWARM_SIZE; i++) {
            swarm[i] = solutions.get(i).getObjectives();
        }

        for (int i = 0; i < SWARM_SIZE; i++) {
            ConstructFuzzyRules.constructRules(swarm[i]);
            fitnessValue[i] = CalculateBaseFunction.calculateFuzzyError(81);
        }
        //找到最优解
        double tempMin = fitnessValue[0];
        int index = 0;
        for (int i = 0; i < fitnessValue.length; i++) {
            if (fitnessValue[i] < tempMin){
                tempMin = fitnessValue[i];
                index = i;
            }
        }
        bestFitness = tempMin;
        pBest = swarm[index];
        // 初始化全局最优解
        gBest = pBest.clone();
    }

    private static void updateVelocityAndPosition() {
        Random random = new Random();
        for (int i = 0; i < SWARM_SIZE; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                double r1 = random.nextDouble();
                double r2 = random.nextDouble();

                // 更新速度
                velocity[j] = INERTIA_WEIGHT * velocity[j] +
                        C1 * r1 * (pBest[j] - swarm[i][j]) +
                        C2 * r2 * (gBest[j] - swarm[i][j]);

                // 更新位置
                swarm[i][j] += (int)velocity[j];

                // 限制位置在范围内
                swarm[i][j] = Math.max(LOWER_BOUND, Math.min(UPPER_BOUND, swarm[i][j]));
            }

        }
    }

    private static void updatePersonalBest() {
        for (int i = 0; i < SWARM_SIZE; i++) {
            if (functionValue(swarm[i]) < functionValue(pBest)) {
                pBest = swarm[i].clone();
            }
        }
    }

    private static void updateGlobalBest() {
        if (functionValue(pBest) < functionValue(gBest)) {
            gBest = pBest.clone();
        }
    }

    private static double functionValue(int[] x) {
        // 适应度值计算
        ConstructFuzzyRules.constructRules(x);
       return CalculateBaseFunction.calculateFuzzyError(81);
    }


    private static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
