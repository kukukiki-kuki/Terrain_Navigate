package cn.shzu.fuzzyLogic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 随机算法
 * @date 2024/3/28 22:40:39
 */
public class randomScrambling {
    public static void main(String[] args) {
        int rowCount = 4;
        int columnCount = 81;
        int matrixCount = 500;
        String outputDirectory = "F:\\FuzzyLogicMatrix1\\";
        List<int[][]> matrices = generateUniqueMatrices(rowCount, columnCount, matrixCount);

        // 输出生成的矩阵数量
        System.out.println("生成的矩阵数量: " + matrices.size());
        // 将生成的矩阵存储到指定位置
        for (int i = 0; i < matrices.size(); i++) {
            String filename = outputDirectory + "matrix_" + (i + 1) + ".txt";
            saveMatrixToFile(matrices.get(i), filename);
            System.out.println("Matrix " + (i + 1) + " generated and saved to " + filename);
        }
    }
    public static void saveMatrixToFile(int[][] matrix, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int[] row : matrix) {
                for (int cell : row) {
                    writer.write(cell + " ");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<int[][]> generateUniqueMatrices(int rowCount, int columnCount, int matrixCount) {
        List<int[][]> matrices = new ArrayList<>();
        Random random = new Random();

        while (matrices.size() < matrixCount) {
            int[][] matrix = new int[rowCount][columnCount];

            // 生成一列矩阵
            for (int j = 0; j < columnCount; j++) {
                int rowIndex = random.nextInt(rowCount); // 随机选择行索引
                matrix[rowIndex][j] = 1;
            }

            // 判断是否重复
            boolean isDuplicate = false;
            for (int[][] existingMatrix : matrices) {
                if (areMatricesEqual(matrix, existingMatrix)) {
                    isDuplicate = true;
                    break;
                }
            }
            // 如果不重复，则添加到列表中
            if (!isDuplicate) {
                matrices.add(matrix);
            }
        }

        return matrices;
    }

    public static boolean areMatricesEqual(int[][] matrix1, int[][] matrix2) {
        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
            return false;
        }
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++) {
                if (matrix1[i][j] != matrix2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
