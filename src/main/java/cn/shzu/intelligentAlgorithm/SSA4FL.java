package cn.shzu.intelligentAlgorithm;

import cn.shzu.utils.CalculateBaseFunction;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 麻雀算法
 * @date 2024/4/26 21:52:20
 */
public class SSA4FL {
    static  int populationSize = 10;
    static int dimension = 81;
    static  double p_percent = 0.2;
    static int upperBound = 3;
    static int lowerBound = 0;
    static int interMax = 1000;
    int [][] location = new int[populationSize][dimension];
    double [] fitnessValue = new double[populationSize];
    double bestFitnessValue = Double.MAX_VALUE;
    int [] bestLocation = new int[dimension];
    double maxValue = Double.MIN_VALUE;

    public static void main(String[] args) {
        SSA4FL ssa4FL = new SSA4FL();
        long start = System.currentTimeMillis();
        ssa4FL.process();
        long end = System.currentTimeMillis();
        System.out.println((end-start)/1000+"s");

    }
    public void process(){
        int[] sortIndex = CalculateBaseFunction.getSortResult(fitnessValue);
        //初始化种群
        initialization();
        Random random = new Random();
        //最佳个体
        double[] pFit = fitnessValue.clone();
        int [][] pLocation = location.clone();
        //迭代更新
        for (int t = 0; t < interMax; t++) {

            //取出最差个体
            int worstIndex = 0;
            for (int i = 0; i < populationSize; i++) {
                if (pFit[i]>maxValue){
                    maxValue = pFit[i];
                    worstIndex = i;
                }
            }
            double worstFitnessValue = pFit[worstIndex];
            int [] worstLocation = pLocation[worstIndex];
            int pNum =(int) (populationSize*p_percent);
            //预警值
            double r2 = random.nextDouble();
            if (r2<0.8){
                for (int i = 0; i <pNum; i++) {
                    double r1 = random.nextDouble();
                    double factor = Math.exp(-(i + 1) / (r1 * interMax));
                    for (int j = 0; j < pNum; j++) {
                       location[sortIndex[i]][j] =  pLocation[sortIndex[i]][j] * (int)factor;
                       //边界处理
                        // 处理超出边界值
                        for (int k = 0; k < dimension; k++) {
                            if (pLocation[i][k] < lowerBound) {
                                pLocation[i][k] = lowerBound;
                            } else if (pLocation[i][k] > upperBound) {
                                pLocation[i][k] = upperBound;
                            }
                        }
                        ConstructFuzzyRules.constructRules(pLocation[sortIndex[i]]);
                        fitnessValue[sortIndex[i]] = CalculateBaseFunction.calculateFuzzyError(81);
                    }
                }
            }else if (r2>=0.8){
                for (int i = 0; i < pNum; i++) {
                    double gaussian = random.nextGaussian();
                    for (int j = 0; j < dimension; j++) {
                        location[sortIndex[i]][j] = (int)(pLocation[sortIndex[i]][j] + gaussian);//更新位置
                        // 处理超出边界值
//                        location[sortIndex[i]][j] = restrictBounds(location[sortIndex[i]]);
                    }
                }
            }
        }

    }
    //边界处理
    public int[] restrictBounds(int [] pLocation,int j){
        for (int k = 0; k < dimension; k++) {
            if (pLocation[k] < lowerBound) {
                pLocation[k] = lowerBound;
            } else if (pLocation[k] > upperBound) {
                pLocation[k] = upperBound;
            }
        }
        return pLocation;
    }


    public  void  initialization(){
       //初始化矩阵
        List<Solution> solutions = PopulationInitialization.initializePopulation(populationSize, dimension);
        for (int i = 0; i < populationSize; i++) {
            location[i] = solutions.get(i).getObjectives();
        }
        //获得初始化适应度值矩阵
        for (int i = 0; i < populationSize; i++) {
            ConstructFuzzyRules.constructRules(location[i]);
            fitnessValue[i] = CalculateBaseFunction.calculateFuzzyError(81);
        }

        int index = 0;
        //找到初始化最佳个体
        for (int i = 0; i < populationSize; i++) {
            if (fitnessValue[i]<bestFitnessValue){
                bestFitnessValue = fitnessValue[i];
                index = i;
            }
        }
        bestLocation = location[index];

        //初始化收敛曲线
        double[][] Convergence_curve = new double[1][interMax];
    }

}
