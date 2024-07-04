package cn.shzu.function;

import cn.shzu.utils.ReadTXTAsArray;
import cn.shzu.utils.RecordExperimentalResults;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 矩阵池化
 * @date 2024/5/30 12:55:10
 */
public class MatrixPooling {
    public static void main(String[] args) {
        new MatrixPooling().process();
    }
    public void process(){
        //从txt文件中读取二维数组
        double [][] original = new double[10713][7047];
        String filePath = "F:\\Fuzzy\\FuzzyLogicRules\\experiment\\resultweight.txt";
        original = ReadTXTAsArray.process(filePath, 1);
        //对二维数组进行池化处理
        double[][] processMatrix = poolMatrix(original, 1071, 704);
        //保存池化后的二维数组到txt文件中存储
        RecordExperimentalResults.writeArrayToFile(processMatrix,"poolingMatrix");
    }
    public static double[][] poolMatrix(double[][] original, int targetHeight, int targetWidth) {
        int originalHeight = original.length;
        int originalWidth = original[0].length;
        double[][] pooled = new double[targetHeight][targetWidth];

        int windowHeight = originalHeight / targetHeight;
        int windowWidth = originalWidth / targetWidth;

        for (int i = 0; i < targetHeight; ++i) {
            for (int j = 0; j < targetWidth; ++j) {
                double sum = 0;
                int count = 0;
                for (int y = i * windowHeight; y < (i + 1) * windowHeight && y < originalHeight; ++y) {
                    for (int x = j * windowWidth; x < (j + 1) * windowWidth && x < originalWidth; ++x) {
                        sum += original[y][x];
                        count++;
                    }
                }
                pooled[i][j] = sum / count; // Calculate average
            }
        }

        return pooled;
    }
}
