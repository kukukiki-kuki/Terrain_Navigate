package cn.shzu.intelligentAlgorithm;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 实现佳点集
 * @date 2024/4/26 20:27:06
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Solution {
    int[] objectives;

    public Solution(int[] objectives) {
        this.objectives = objectives;
    }

    public int[] getObjectives() {
        return objectives;
    }

    // 判断当前解是否被另一个解支配
    public boolean isDominatedBy(Solution other) {
        boolean isDominated = true;
        for (int i = 0; i < objectives.length; i++) {
            if (this.objectives[i] > other.objectives[i]) {
                isDominated = false;
                break;
            }
        }
        return isDominated;
    }
}

public class PopulationInitialization {
    public static List<Solution> initializePopulation(int populationSize, int numberOfObjectives) {
        List<Solution> population = new ArrayList<>();
        Random random = new Random();


        // 生成随机解作为初始种群
        for (int i = 0; i < populationSize; i++) {
            int[] objectives = new int[numberOfObjectives];
            for (int j = 0; j < numberOfObjectives; j++) {
                // 生成随机目标函数值（0到3的整数）
                objectives[j] = random.nextInt(4);
            }
            population.add(new Solution(objectives));
        }

        // 识别 Pareto 前沿上的解
        List<Solution> paretoFront = identifyParetoFront(population);

        // 从 Pareto 前沿中选择解作为初始种群
        int selectedPopulationSize = Math.min(populationSize, paretoFront.size());
        List<Solution> initialPopulation = new ArrayList<>();
        for (int i = 0; i < selectedPopulationSize; i++) {
            initialPopulation.add(paretoFront.get(i));
        }

        return initialPopulation;
    }

    public static List<Solution> identifyParetoFront(List<Solution> population) {
        List<Solution> paretoFront = new ArrayList<>();
        for (Solution solution : population) {
            boolean isDominated = false;
            for (Solution other : population) {
                if (solution != other && isDominatedBy(solution, other)) {
                    isDominated = true;
                    break;
                }
            }
            if (!isDominated) {
                paretoFront.add(solution);
            }
        }
        return paretoFront;
    }

    // 判断一个解是否被另一个解支配
    public static boolean isDominatedBy(Solution solution, Solution other) {
        boolean isDominated = true;
        for (int i = 0; i < solution.getObjectives().length; i++) {
            if (solution.getObjectives()[i] > other.getObjectives()[i]) {
                isDominated = false;
                break;
            }
        }
        return isDominated;
    }

    public static void main(String[] args) {
        int populationSize = 20;
        int numberOfObjectives = 81;

        // 初始化种群
        List<Solution> initialPopulation = initializePopulation(populationSize, numberOfObjectives);

        // 输出初始种群
        for (Solution solution : initialPopulation) {
            System.out.println("Solution: " + java.util.Arrays.toString(solution.getObjectives()));
        }
    }
}


