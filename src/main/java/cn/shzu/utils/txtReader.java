package cn.shzu.utils;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 批量处理文本文件
 * @date 2024/3/15 17:43:21
 */
public class txtReader {
    public static void main(String[] args) {
        txtReader txtReader = new txtReader();
        txtReader.writerMatrix();
    }
    public  void process(){
        //String[] fuzzyType = {"slope", "fluctuation", "friction", "mellowness"};
        String[] slopeType = {"flat", "relief", "cragginess"};
        String[] fluctuationType = {"gentle", "moderate", "high"};
        String[] frictionType = {"slippy", "coarse", "roughness"};
        String[] mellownessType = {"stiff", "slight", "soft"};
        String[] transitCostType = {"safe", "lowRisk", "mediumRisk", "risk"};

        // 指定要写入的文件路径
        String filePath = "C:\\Users\\soya\\Desktop\\fcl.txt";
        String content ;
        try {
            // 创建 BufferedWriter 对象，用于写入文件
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (int i = 0; i < slopeType.length; i++) {
                for (int j = 0; j < fluctuationType.length; j++) {
                    for (int k = 0; k < frictionType.length; k++) {
                        for (int l = 0; l < mellownessType.length; l++) {
                            for (int m = 0; m < transitCostType.length; m++) {
                                // 要写入文件的字符串内容
                                content = "IF slope IS "+slopeType[i]+ " AND fluctuation IS "+fluctuationType[j]+" AND friction IS "+frictionType[k]+" AND mellowness IS "+mellownessType[l]+" THEN TransitCost IS "+transitCostType[m]+";";
                                // 将字符串写入文件
                                writer.write(content);
                                writer.newLine();
                                // 提示写入成功
                                System.out.println("内容已成功写入文件 " + filePath);
                            }
                        }
                    }
                }
            }
            // 关闭 BufferedWriter
            writer.close();

        } catch (IOException e) {
            // 捕获可能发生的异常
            e.printStackTrace();
        }
    }

    //将矩阵写入文本
    public  void writerMatrix(){
        double[][] doubles = randomMatrix(30, 3.5, 1, 1, 200);
        String filePath = "F:\\Fuzzy\\FuzzyLogicRules\\matrix.txt";
        try {
            FileWriter writer = new FileWriter(filePath);
            for (double[] aDouble : doubles) {
                for (double v : aDouble) {
                    writer.write(v + "\t");
                }
                writer.write("\n"); // 写入换行符
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("写入文件时出错：" + e.getMessage());
        }
    }
    //随机生成矩阵信息
    public  double[][] randomMatrix(int slope,double fluctuation ,int friction ,int mellowness,int num){
        double[][] doubles = new double[num][4];
        Random random = new Random();
        for (int i = 0; i <num ; i++) {
            double slopeNum = random.nextDouble()*slope;
            while (slopeNum<5){
                slopeNum = random.nextDouble()*slope;
            }
            double fluctuationNum = random.nextDouble()*fluctuation;
            while (fluctuationNum<0.3){
                fluctuationNum = random.nextDouble()*fluctuation;
            }
            double frictionNum = random.nextDouble()*friction;
            while (frictionNum<0.1){
                frictionNum = random.nextDouble()*friction;
            }
            double mellownessNum = random.nextDouble()*mellowness;
            while (mellownessNum<0.1){
                mellownessNum = random.nextDouble()*mellowness;
            }
            doubles[i][0] = slopeNum;
            doubles[i][1] = fluctuationNum;
            doubles[i][2] = frictionNum;
            doubles[i][3] = mellownessNum;
        }
        System.out.println(Arrays.deepToString(doubles));
        return doubles;
    }
}
