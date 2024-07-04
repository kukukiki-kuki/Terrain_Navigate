package cn.shzu.intelligentAlgorithm;

import org.jfree.util.Log;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 构造模糊规则
 * @date 2024/4/18 22:00:37
 */
public class ConstructFuzzyRules {
    private static String [] slopeType = {"flat","relief","cragginess"};
    private static String [] fluctuationType = {"gentle","moderate","high"};
    private static String [] frictionType = {"slippy", "coarse","roughness"};
    private static String [] mellownessType= {"soft", "slight","stiff"};
    private static String []  transitCostType = {"safe","lowRisk","mediumRisk","risk"};
    private static String outFilePath = "F:\\Fuzzy\\FuzzyLogicRules\\rules.txt";
    private static String inFilePath = "F:\\Fuzzy\\FuzzyLogicRules\\fuzzy.txt";
    private static String targetFilePath = "F:\\Fuzzy\\FuzzyLogicRules\\target.fcl";
    private static int insertLineNumber = 65;

    public static void main(String[] args) {
        int []  pro =  new int[81];
        Random random = new Random();
        for (int i = 0; i < pro.length; i++) {
            pro[i]=random.nextInt(4);
        }
        System.out.println(Arrays.toString(pro));
        ConstructFuzzyRules.constructRules(pro);
    }
    public static void constructRules(int [] pro){
        String rule;
        int count = 1;
        // 创建 BufferedWriter 对象，用于写入文件
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outFilePath));
            for (int i = 0; i <slopeType.length ; i++) {
                for (int j = 0; j < fluctuationType.length; j++) {
                    for (int k = 0; k < frictionType.length; k++) {
                        for (int l = 0; l < mellownessType.length; l++) {
                            rule = "    RULE "+count+": IF slope IS "+slopeType[i]+ " AND fluctuation IS "+fluctuationType[j]+" AND friction IS "+frictionType[k]+" AND mellowness IS "+mellownessType[l]+" THEN TransitCost IS "+transitCostType[pro[count-1]]+";";
                            //将字符串写入文件
                            writer.write(rule);
                            writer.newLine();
                            //提示成功写入
                            count++;
                        }
                    }
                }
            }
//            System.out.println("文件写入");
            //关闭 BufferedWriter
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    writerFile();
    }

    public static void writerFile(){
        // 读取源文件内容
        StringBuilder sourceContentBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inFilePath));
            String line;
            while ((line = reader.readLine()) != null) {
                sourceContentBuilder.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("读取源文件时出错：" + e.getMessage());
            return;
        }

        // 读取要插入的文件内容
        StringBuilder insertContentBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(outFilePath));
            String line;
            while ((line = reader.readLine()) != null) {
                insertContentBuilder.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("读取要插入的文件时出错：" + e.getMessage());
            return;
        }

        // 将要插入的文件内容插入到源文件中的指定位置
        String[] sourceLines = sourceContentBuilder.toString().split("\n");
        StringBuilder targetContentBuilder = new StringBuilder();
        for (int i = 0; i < sourceLines.length; i++) {
            targetContentBuilder.append(sourceLines[i]).append("\n");
            if (i + 1 == insertLineNumber) {
                targetContentBuilder.append(insertContentBuilder);
            }
        }

        // 将合并后的内容写入目标文件
        try {
            FileWriter writer = new FileWriter(targetFilePath);
            writer.write(targetContentBuilder.toString());
            writer.close();
//            System.out.println("内容已成功插入到目标文件！");
        } catch (IOException e) {
            System.out.println("写入目标文件时出错：" + e.getMessage());
        }
    }

}
