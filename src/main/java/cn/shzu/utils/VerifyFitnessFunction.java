package cn.shzu.utils;

import cn.shzu.fuzzyLogic.BaseArray;
import cn.shzu.intelligentAlgorithm.BWO4FuzzyLogic;
import cn.shzu.intelligentAlgorithm.ConstructFuzzyRules;
import net.sourceforge.jFuzzyLogic.FIS;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 验证适应度方程
 * @date 2024/4/20 11:44:18
 */
public class VerifyFitnessFunction {
    public static void main(String[] args) {
        VerifyFitnessFunction verifyFitnessFunction = new VerifyFitnessFunction();
        verifyFitnessFunction.constructRules();
        verifyFitnessFunction.process();
    }
    public void constructRules(){
        int []  pro =  new int[81];
        Random random = new Random();
//        for (int i = 0; i < pro.length; i++) {
//            pro[i]=random.nextInt(4);
//        }
        //读取专家矩阵
        pro = BaseArray.getArrayS(16);
        System.out.println(Arrays.toString(pro));
        ConstructFuzzyRules.constructRules(pro);
    }

    public void process(){
        int num = 81;
        double error = CalculateBaseFunction.calculateFuzzyError(num);
        System.out.println("81个案例平均误差:" + error);
    }

    public double[][]  readMatrixFromFile(int num){
        double[][] matrix = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("F:\\Fuzzy\\FuzzyLogicRules\\matrixBase81_plus.txt"));
            String line;
            int numRows = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (matrix == null) {
                    // 根据第一行确定二维数组的大小
                    int numCols = parts.length;
                    matrix = new double[num][numCols];
                }
                for (int i = 0; i < parts.length; i++) {
                    matrix[numRows][i] = Double.parseDouble(parts[i]);
                }
                numRows++;
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("读取文件时出错：" + e.getMessage());
        }
        return matrix;
    }
    public double fuzzyLogicCase(double[] whale){
        //加载模糊逻辑系统
        String fileName = "F:\\Fuzzy\\FuzzyLogicRules\\target.fcl"; // 模糊逻辑控制文件
        FIS fis = FIS.load(fileName, true);
        if (fis == null) {
            System.err.println("Can't load file: '" + fileName + "'");
            return 0.0;
        }
        //设置输入变量
        // 设置输入变量值
        fis.setVariable("slope", whale[0]);
        fis.setVariable("fluctuation", whale[1]);
        fis.setVariable("friction", whale[2]);
        fis.setVariable("mellowness", whale[3]);

        //执行模糊推理
        fis.evaluate();

        //展示模糊输出
        return fis.getVariable("TransitCost").defuzzify();
    }
}
