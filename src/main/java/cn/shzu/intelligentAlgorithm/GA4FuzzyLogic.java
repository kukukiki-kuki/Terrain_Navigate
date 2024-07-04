package cn.shzu.intelligentAlgorithm;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 遗传算法
 * @date 2024/4/19 22:45:08
 */
import cn.shzu.utils.CalculateBaseFunction;
import cn.shzu.utils.RecordExperimentalResults;

import java.util.Arrays;
import java.util.Random;

public class GA4FuzzyLogic {
    private int populationSize = 4;
    private int dimension = 81;
    private int interMax = 10;
    double mutationRate = 0.1;
    double[] bestFval = new double[interMax+1];
    private static int [] bestLocation = null;
    //最优解和最优解值
    double  bestFitnessValue = Double.MAX_VALUE;


    public static void main(String[] args) {
        GA4FuzzyLogic ga4FuzzyLogic = new GA4FuzzyLogic();
        long start = System.currentTimeMillis();
        ga4FuzzyLogic.process();
        long end = System.currentTimeMillis();
        System.out.println("程序执行时间:"+(end-start)/1000+"s");
        long time = (end-start)/1000;

        RecordExperimentalResults.writeArrayToFile(bestLocation, ga4FuzzyLogic.bestFitnessValue, "GA",1, ga4FuzzyLogic.bestFval, time,String.valueOf(end));
    }
    public void process(){

        // 位置矩阵 初始化种群
        int[][] population = new int[populationSize][dimension];
        Random random = new Random();
        for (int i = 0; i < populationSize; i++) {
            for (int j = 0; j < dimension; j++) {
                population[i][j] = random.nextInt(4);
            }
        }
        //使用本地给出的初始化种群

        //适应度函数
        double [] fitnessValue = new double[populationSize];
        //循环迭代得到最优解
        for (int t = 0; t < interMax; t++) {
            int numlun = t+1;
            System.out.println("开始第"+numlun+"轮");
            System.out.println(Arrays.toString(bestLocation));
            System.out.println(bestFitnessValue);

            //根据位置更新规则库，获取每个位置的适应度值
            //根据位置更新规则库 ,获取每个位置的适应度函数的值
            for (int i = 0; i < populationSize; i++) {
                ConstructFuzzyRules.constructRules(population[i]);
                int num = 81;
                fitnessValue[i] = CalculateBaseFunction.calculateFuzzyError(num);
            }
            //选择
            int[][] selectionPopulation = selection(population, fitnessValue);
            //交叉
            int[][] offspring = crossover(selectionPopulation);
            //变异
            mutation(population,mutationRate);

            population = offspring;
            //记录最佳解
            for (int i = 0; i < populationSize; i++) {
                if (fitnessValue[i] < bestFitnessValue){
                    bestFitnessValue = fitnessValue[i];
                    bestLocation = population[i];
                }
            }
            bestFval[t+1] = bestFitnessValue;
        }
        System.out.println("Best solution found: " + Arrays.toString(bestLocation));
        System.out.println("Best fitness found: " + bestFitnessValue);
        System.out.println(Arrays.toString(bestFval));

    }

    // 选择操作,轮盘赌选择，适应度函数小的个体被选择的概率大
    public static int[][] selection(int[][] population, double[] fitness) {
        int[][] selectedPopulation = new int[population.length][population[0].length];
        double[] temp_fitness = new double[fitness.length];
        for (int i = 0; i < fitness.length; i++) {
            temp_fitness[i] = 1 - fitness[i];
        }
        double totalFitness = Arrays.stream(temp_fitness).sum();
        double[] probabilities = new double[fitness.length];
        for (int i = 0; i < fitness.length; i++) {
            probabilities[i] =  temp_fitness[i] / totalFitness;
        }
        for (int i = 0; i < population.length; i++) {
            int selectedIndex = selectIndex(probabilities);
            selectedPopulation[i] = population[selectedIndex];
        }
        return selectedPopulation;
    }

    // 根据概率选择索引
    public static int selectIndex(double[] probabilities) {
        double r = Math.random();
        double cumulativeProbability = 0;
        for (int i = 0; i < probabilities.length; i++) {
            cumulativeProbability += probabilities[i];
            if (r <= cumulativeProbability) {
                return i;
            }
        }
        return probabilities.length - 1;
    }

    // 交叉操作
    public static int[][] crossover(int[][] population) {
        int[][] offspring = new int[population.length][population[0].length];
        Random random = new Random();
        for (int i = 0; i < population.length; i += 2) {
            int crossoverPoint = random.nextInt(population[0].length);
            for (int j = 0; j < crossoverPoint; j++) {
                offspring[i][j] = population[i][j];
                offspring[i + 1][j] = population[i + 1][j];
            }
            for (int j = crossoverPoint; j < population[0].length; j++) {
                offspring[i][j] = population[i + 1][j];
                offspring[i + 1][j] = population[i][j];
            }
        }
        return offspring;
    }

    // 变异操作
    public static void mutation(int[][] population, double mutationRate) {
        Random random = new Random();
        for (int i = 0; i < population.length; i++) {
            for (int j = 0; j < population[0].length; j++) {
                if (random.nextDouble() < mutationRate) {
                    population[i][j] = random.nextInt(4); // 0到4的随机整数
                }
            }
        }
    }

}

