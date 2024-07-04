package cn.shzu.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 读取txt作为数组
 * @date 2024/5/10 22:38:12
 */
public class ReadTXTAsArray {
    public static void main(String[] args) {
        process("D:\\Data\\pythonData\\tailorTiff\\matrix.txt");
    }

    public static double [][] process(String filePath,int num){
        // 确定二维数组的大小
        int rows = 10713; // 根据您的需求指定行数
        int cols = 7047;  // 根据您的需求指定列数
        double[][] matrix = new double[rows][cols];
        // 读取txt文件
        File file = new File(filePath);
        try {
            Scanner scanner = new Scanner(file);

            // 逐行读取并填充二维数组
            for (int i = 0; i < rows; i++) {
                String line = scanner.nextLine();
                String[] values = line.split("\t"); // 假设数据以制表符分隔
                for (int j = 0; j < cols; j++) {
                    matrix[i][j] = Double.parseDouble(values[j]);
                }
            }

            // 打印二维数组（可选）


            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return  matrix;
    }
    public static int [][] process(String filePath){
        // 确定二维数组的大小
        int rows = 10713; // 根据您的需求指定行数
        int cols = 7047;  // 根据您的需求指定列数
        int[][] matrix = new int[rows][cols];
        // 读取txt文件
        File file = new File(filePath);
        try {
            Scanner scanner = new Scanner(file);

            // 逐行读取并填充二维数组
            for (int i = 0; i < rows; i++) {
                String line = scanner.nextLine();
                String[] values = line.split("\t"); // 假设数据以制表符分隔
                for (int j = 0; j < cols; j++) {
                    matrix[i][j] = Integer.parseInt(values[j]);
                }
            }
            // 打印二维数组（可选）
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return  matrix;
    }
}
