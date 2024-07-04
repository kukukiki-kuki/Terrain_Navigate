package cn.shzu.intelligentAlgorithm;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description GPT实现蚁群算法
 * @date 2024/4/23 12:58:37
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CopyACO {
    private int numAnts;
    private int numDimensions;
    private double[][] bounds;
    private double[][] pheromones;
    private double alpha;
    private double beta;
    private double evaporationRate;
    private int maxIterations;

    public static void main(String[] args) {
    // 设置优化问题的参数
        int numAnts = 20; // 蚂蚁数量
        int numDimensions = 2; // 参数维度数量
        double[][] bounds = { {0, 10}, {0, 10}}; // 参数取值范围
        double alpha = 1.0; // 信息素重要性参数
        double beta = 2.0; // 启发式信息重要性参数
        double evaporationRate = 0.5; // 信息素挥发率
        int maxIterations = 100; // 最大迭代次数
        // 创建 OptimizationACO 对象
        CopyACO copyACO = new CopyACO(numAnts, numDimensions, bounds, alpha, beta, evaporationRate, maxIterations);
        double[] bestSolution = copyACO.solveOptimizationProblem();

        //输出找到最优解
        System.out.println("Best solution:");
        for (int i = 0; i < numDimensions; i++) {
            System.out.println("Dimension " + (i+1) + ": " + bestSolution[i]);
        }
        System.out.println(copyACO.evaluateObjective(bestSolution));
    }
    public CopyACO(int numAnts, int numDimensions, double[][] bounds, double alpha, double beta, double evaporationRate, int maxIterations) {
        this.numAnts = numAnts;
        this.numDimensions = numDimensions;
        this.bounds = bounds;
        this.alpha = alpha;
        this.beta = beta;
        this.evaporationRate = evaporationRate;
        this.maxIterations = maxIterations;

        pheromones = new double[numDimensions][numDimensions];
        initializePheromones();
    }

    private void initializePheromones() {
        double initialPheromone = 1.0 / numDimensions;
        for (int i = 0; i < numDimensions; i++) {
            for (int j = 0; j < numDimensions; j++) {
                pheromones[i][j] = initialPheromone;
            }
        }
    }

    public double[] solveOptimizationProblem() {
        double[] bestSolution = null;
        double bestObjectiveValue = Double.POSITIVE_INFINITY;

        for (int iter = 0; iter < maxIterations; iter++) {
            List<double[]> antSolutions = new ArrayList<>();
            double[] objectiveValues = new double[numAnts];

            for (int ant = 0; ant < numAnts; ant++) {
                double[] solution = constructSolution();
                antSolutions.add(solution);
                double objectiveValue = evaluateObjective(solution);
                objectiveValues[ant] = objectiveValue;
                if (objectiveValue < bestObjectiveValue) {
                    bestObjectiveValue = objectiveValue;
                    bestSolution = solution.clone();
                }
            }

            updatePheromones(antSolutions, objectiveValues);
            evaporatePheromones();
        }

        return bestSolution;
    }

    private double[] constructSolution() {
        Random random = new Random();
        double[] solution = new double[numDimensions];
        for (int i = 0; i < numDimensions; i++) {
            double lowerBound = bounds[i][0];
            double upperBound = bounds[i][1];
            // 计算选择概率时使用 alpha 和 beta 参数
            double pheromoneFactor = Math.pow(pheromones[i][i], alpha);
            double heuristicFactor = Math.pow(1.0 / (upperBound - lowerBound), beta);
            // 计算选择概率
            double[] probabilities = new double[numDimensions];
            double totalProbability = 0.0;
            for (int j = 0; j < numDimensions; j++) {
                probabilities[j] = pheromoneFactor * heuristicFactor; // 这里可以根据需要添加启发式信息
                totalProbability += probabilities[j];
            }
            // 根据概率选择参数值
            double randomNumber = random.nextDouble() * totalProbability;
            double cumulativeProbability = 0.0;
            for (int j = 0; j < numDimensions; j++) {
                cumulativeProbability += probabilities[j];
                if (randomNumber <= cumulativeProbability) {
                    solution[i] = lowerBound + j * (upperBound - lowerBound) / (numDimensions - 1);
                    break;
                }
            }
        }
        return solution;
    }

    private double evaluateObjective(double[] solution) {
        // 这里修改为你要优化的目标函数
        double v = solution[0];
        double u = solution[1];
        return (u+v)/2*Math.pow(u*v,0.5);

    }

    private void updatePheromones(List<double[]> antSolutions, double[] objectiveValues) {
        double[][] deltaPheromones = new double[numDimensions][numDimensions];

        for (int ant = 0; ant < numAnts; ant++) {
            double[] solution = antSolutions.get(ant);
            double objectiveValue = objectiveValues[ant];
            for (int i = 0; i < numDimensions; i++) {
                for (int j = 0; j < numDimensions; j++) {
                    deltaPheromones[i][j] += 1.0 / objectiveValue;
                }
            }
        }

        for (int i = 0; i < numDimensions; i++) {
            for (int j = 0; j < numDimensions; j++) {
                pheromones[i][j] = (1 - evaporationRate) * pheromones[i][j] + deltaPheromones[i][j];
            }
        }
    }

    private void evaporatePheromones() {
        for (int i = 0; i < numDimensions; i++) {
            for (int j = 0; j < numDimensions; j++) {
                pheromones[i][j] *= (1 - evaporationRate);
            }
        }
    }
}