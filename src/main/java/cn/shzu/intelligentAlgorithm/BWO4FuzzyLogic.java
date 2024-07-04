package cn.shzu.intelligentAlgorithm;

import cn.shzu.fuzzyLogic.BaseArray;
import cn.shzu.utils.CalculateBaseFunction;
import cn.shzu.utils.RecordExperimentalResults;
import net.sourceforge.jFuzzyLogic.FIS;
import org.apache.commons.numbers.gamma.Gamma;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;



/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 白鲸算法实现
 * @date 2024/4/15 17:28:24
 */
public class BWO4FuzzyLogic {
    private int populationSize = 10;
    private int dimension = 81;
    private  int upperBound = 3;
    private  int lowerBound = 0;
    private int interMax = 250;
    // 记录每轮更新最优解
    double[] bestFval = new double[interMax+1];
    double [] fitnessValue = new double[populationSize];
    int [] bestLocation = new int[dimension];
    double bestFitnessValue;

    public static void main(String[] args) {
        BWO4FuzzyLogic bwo = new BWO4FuzzyLogic();
        long start = System.currentTimeMillis();
        bwo.process();
        long end = System.currentTimeMillis();
        System.out.println("程序执行时间:"+(end-start)/1000+"s");
        long time = (end-start)/1000;
        RecordExperimentalResults.writeArrayToFile(bwo.bestLocation, bwo.bestFitnessValue, "IPBWO",3, bwo.bestFval, time,String.valueOf(end));
    }



    public void process(){

        // 位置矩阵
        int[][] location = new int[populationSize][dimension];
        Random random = new Random();
//        for (int i = 0; i < populationSize-1; i++) {
//            for (int j = 0; j < dimension; j++) {
//                location[i][j] = random.nextInt(4);
//            }
//        }r
        //手动给定初始解
//        for (int i = 0; i < populationSize; i++) {
//            location[i] = BaseArray.getArrayS(i);
//        }
        //佳点集初始化
        List<Solution> solutions = PopulationInitialization.initializePopulation(populationSize, dimension);
        for (int i = 0; i < populationSize; i++) {
            location[i] = solutions.get(i).getObjectives();
        }

        //根据位置更新规则库 ,获取每个位置的适应度函数的值
        for (int i = 0; i < populationSize; i++) {
            ConstructFuzzyRules.constructRules(location[i]);
            int num = 81;
            fitnessValue[i] = CalculateBaseFunction.calculateFuzzyError(num);
        }
        //适应度值拷贝
        double [] fCopy = new double[populationSize];
        for (int i = 0; i < populationSize; i++) {
            fCopy[i] = fitnessValue[i];
        }
        //最优解和最优解值
        // 循环遍历数组，找到最小值
        double min = fCopy[0];
        int index = 0;
        for (int i = 1; i < fCopy.length; i++) {
            if (fCopy[i] < min) {
                min = fCopy[i];
                index = i;
            }
        }
        bestFitnessValue = min;

        for (int i = 0; i < dimension; i++) {
            bestLocation[i] = location[index][i];
        }
        bestFval[0] = bestFitnessValue;

        Random rand = new Random();
        //循环更新
        for (int t = 0; t < interMax; t++) {
            int numlun = t+1;
            System.out.println("开始第"+numlun+"轮");
            System.out.println(Arrays.toString(bestLocation));
            System.out.println(bestFitnessValue);
            //位置矩阵拷贝
            int[][] locationCopy = new int[populationSize][dimension];
            for (int i = 0; i < populationSize; i++) {
                for (int j = 0; j < dimension; j++) {
                  locationCopy[i][j] = location[i][j];
                }
            }
            //鲸落概率
            double wf = 0.1 - 0.05 * ((double) t / interMax);
            //计算每只标记的平滑因子bf
            double[] b0 = new double[populationSize];
            for (int i = 0; i < populationSize; i++) {
                b0[i] = rand.nextDouble();
                while (b0[i] == 0) {
                    b0[i] = rand.nextDouble();
                }
            }
            double[] bf = new double[populationSize];
            for (int i = 0; i < populationSize; i++) {
                bf[i] = b0[i] * (1 - t / (2.0 * interMax));
            }
            //更新每一只白鲸的位置
            for (int i = 0; i < populationSize; i++) {
                // 探索阶段
                if (bf[i] > 0.5) {
                    double r1 = rand.nextDouble();
                    double r2 = rand.nextDouble();
                    int r = rand.nextInt(populationSize);
                    while (r == i) {
                        r = rand.nextInt(populationSize);
                    }
                    int[] pj = new int[dimension];
                    for (int j = 0; j < dimension; j++) {
                        pj[j] = j;
                    }
                    shuffleArray(pj);
                    if (dimension <= populationSize / 5) {
                        locationCopy[i][pj[0]] = location[i][pj[0]] + (location[r][pj[0]] - location[i][pj[0]]) * (int)((1 + r1) * Math.sin(2 * Math.PI * r2));
                        locationCopy[i][pj[1]] = location[i][pj[1]] + (location[r][pj[1]] - location[i][pj[1]]) * (int)((1 + r1) * Math.cos(2 * Math.PI * r2));
                    } else {
                        for (int j = 0; j < dimension / 2; j++) {
                            locationCopy[i][2 * j] = location[i][2 * j] + (location[r][pj[1]] - location[i][2 * j]) * (int)((1 + r1) * Math.sin(2 * Math.PI * r2));
                            locationCopy[i][2 * j + 1] = location[i][2 * j + 1] + (location[r][pj[1]] - location[i][2 * j + 1]) *(int) ((1 + r1) * Math.cos(2 * Math.PI * r2));
                        }
                    }
//                    System.out.println("第"+i+"只白鲸在探索："+ Arrays.toString(locationCopy[i]));
                }
                //开发阶段
                else {
                        double r3 = rand.nextDouble();
                        double r4 = rand.nextDouble();
                        int r = rand.nextInt(populationSize);
                        while (r == i) {
                            r = rand.nextInt(populationSize);
                        }
                    double c1 = 2 * r4 * (1 - (t / (double) interMax));
                    double beta = 3 / 2.0;
                    double delta = (Gamma.value(1 + beta) * Math.sin((Math.PI * beta) / 2)) / (Gamma.value((1 + beta) / 2) * beta * Math.pow(2, ((beta - 1) / 2.0)));
                    double[] u = new double[dimension];
                    double[] v = new double[dimension];
                    double[] lf = new double[dimension];
                    for (int j = 0; j < dimension; j++) {
                        u[j] = rand.nextGaussian();
                        v[j] = rand.nextGaussian();
                        lf[j] = 0.05 * ((u[j] * delta) / (Math.pow(Math.abs(v[j]), (1 / beta))));
                    }
                    for (int j = 0; j < dimension; j++) {
                        locationCopy[i][j] = (int)(r3 * bestLocation[j] - r4 * location[i][j] + c1 * lf[j] * (location[r][j] - location[i][j]));
                    }
//                    System.out.println("第"+i+"只白鲸在开发："+Arrays.toString(locationCopy[i]));
                }
                //处理超过边界值
                for (int k = 0; k < dimension; k++) {
                    if (locationCopy[i][k] < lowerBound) {
                        locationCopy[i][k] = lowerBound;
                    } else if (locationCopy[i][k] > upperBound) {
                        locationCopy[i][k] = upperBound;
                    }
                }
                //计算函数值
//                for (int k = 0; k < populationSize; k++) {
//                    ConstructFuzzyRules.constructRules(locationCopy[k]);
//                    int num = 200;
//                    fCopy[k] = calculateFuzzyError(num);
//                }
                ConstructFuzzyRules.constructRules(locationCopy[i]);
                int num = 81;
                fCopy[i] = CalculateBaseFunction.calculateFuzzyError(num);
                //更新最优解
                if (fCopy[i]<fitnessValue[i]){
                    for (int j = 0; j < dimension; j++) {
                        location[i][j] = locationCopy[i][j];
                    }
                    fitnessValue[i] = fCopy[i];
                }

            }
            //鲸落
            for (int i = 0; i < populationSize; i++) {
                if (bf[i] <= wf) {
                    double r5 = rand.nextDouble();
                    double r6 = rand.nextDouble();
                    double r7 = rand.nextDouble();
                    double c2 = 2 * wf * populationSize;
                    int r = rand.nextInt(populationSize);
                    double xstep = (upperBound - lowerBound) * (Math.exp((-c2 * t) / (double) interMax));
                    for (int j = 0; j < dimension; j++) {
                        locationCopy[i][j] = (int)(r5 * location[i][j] - r6 * location[r][j] + r7 * xstep);
                    }
//                    System.out.println("第"+i+"只白鲸在鲸落："+ Arrays.toString(locationCopy[i]));
                    // 处理超出边界值
                    for (int k = 0; k < dimension; k++) {
                        if (locationCopy[i][k] < lowerBound) {
                            locationCopy[i][k] = lowerBound;
                        } else if (locationCopy[i][k] > upperBound) {
                            locationCopy[i][k] = upperBound;
                        }
                    }
                    // 计算函数值
//                    for (int k = 0; k < populationSize; k++) {
//                        ConstructFuzzyRules.constructRules(locationCopy[k]);
//                        int num = 200;
//                        fCopy[k] = calculateFuzzyError(num);
//                    }
                    ConstructFuzzyRules.constructRules(locationCopy[i]);
                    int num = 81;
                    fCopy[i] = CalculateBaseFunction.calculateFuzzyError(num);
                    // 更新最优解
                    if (fCopy[i] < fitnessValue[i]) {
                        for (int j = 0; j < dimension; j++) {
                            location[i][j] = locationCopy[i][j];
                        }
                        fitnessValue[i] = fCopy[i];
                    }
                }
            }
            //更改最优值
            double minFitness = Double.MAX_VALUE;
            int minIndex = -1;
            for (int i = 0; i < populationSize; i++) {
                if (fitnessValue[i] < minFitness){
                    minFitness = fitnessValue[i];
                    minIndex = i;
                }
            }
            if (minFitness < bestFitnessValue){
                bestFitnessValue = minFitness;
                for (int i = 0; i <dimension ; i++) {
                    bestLocation[i] = location[minIndex][i];
                }
            }
            //结合模拟退火算法
            //对现有的适应度值进行排序
            int[] sortResult = CalculateBaseFunction.getSortResult(fitnessValue);
            int [][] arrays = new int[(int) (populationSize*0.2)][81];
            for (int i = 0; i < arrays.length; i++) {
                arrays[i] = location[sortResult[i]];
            }
            bestLocation = SimulatedAnnealing.simulatedAnnealing(1000, 0.03, 800, arrays);
            ConstructFuzzyRules.constructRules(bestLocation);
            bestFitnessValue = CalculateBaseFunction.calculateFuzzyError(81);
            bestFval[t+1] = bestFitnessValue;
        }
        System.out.println("bestLocation: " + Arrays.toString(bestLocation));
        System.out.println("bestFitnessValue: " + bestFitnessValue);
        System.out.println("bestFval: " + Arrays.toString(bestFval));
    }

    private static void shuffleArray(int[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            // 交换 array[i] 和 array[index]
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}

