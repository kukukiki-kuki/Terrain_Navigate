package cn.shzu.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 生成方法
 * @date 2024/4/20 18:52:23
 */
public class GenerateFunction {
    public static void main(String[] args) {
        GenerateFunction generateFunction = new GenerateFunction();
        generateFunction.generateCase();
    }

    public void generateCase(){
        double[] inputCase = new double[4];

        for (int m = 0; m < 10; m++) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    for (int k = 0; k < 3; k++) {
                        for (int l = 0; l < 3; l++) {
                            inputCase[0] = returnSlopeNum(i);
                            inputCase[1] = returnFluctuationNum(j);
                            inputCase[2] = returnFrictionNum(k);
                            inputCase[3] = returnMellownessNum(l);
                            // 写入文件
                            try {
                                FileWriter writer = new FileWriter("F:\\Fuzzy\\FuzzyLogicRules\\matrixBase81_plus.txt",true);
                                for (double v : inputCase) {
                                    writer.write(v + "\t"); // 写入数组元素，并在每个元素后添加换行符
                                }
                                writer.write("\n"); // 写入换行符
                                writer.close();
                                System.out.println("数组数据已成功写入到文件 matrixBase81_plus.txt 中。");
                            } catch (IOException e) {
                                System.out.println("写入文件时出现错误：" + e.getMessage());
                            }
                        }
                    }
                }
            }
        }

    }
    public double returnSlopeNum(int i){
        Random rand = new Random();
        if (i == 0){
            return 2+rand.nextDouble()*10;
        }
        if (i ==1 ){
            return 10+rand.nextDouble()*(20-10);
        }else {
            return 20+rand.nextDouble()*(30-20);
        }
    }
    public double returnFluctuationNum(int i){
        Random rand = new Random();
        if (i == 0){
            return rand.nextDouble()*1;
        }
        if (i ==1 ){
            return 1+rand.nextDouble()*(2.5-1);
        }else {
            return 2.5+ rand.nextDouble();
        }
    }

    public double returnFrictionNum(int i){
        Random rand = new Random();
        if (i == 0){
            return 0.2+rand.nextDouble()*(0.4-0.2);
        }
        if (i ==1 ){
            return 0.4+rand.nextDouble()*(0.6-0.4);
        }else {
            return 0.6+ rand.nextDouble()*(1-0.6);
        }
    }

    public double returnMellownessNum(int i){
        Random rand = new Random();
        if (i == 0){
            return 0.1+rand.nextDouble()*(0.2-0.1);
        }
        if (i ==1 ){
            return 0.2+rand.nextDouble()*(0.8-0.2);
        }else {
            return 0.8+ rand.nextDouble()*(1-0.8);
        }
    }
}
