package cn.shzu.utils;

import java.io.*;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 插入空白行
 * @date 2024/3/29 14:49:38
 */
public class insertTet {
    public static void main(String[] args) {
        String inputFilePath = "D:\\Data\\JavaData\\A_starOnDem\\src\\main\\java\\cn\\shzu\\fuzzyLogic\\FuzzyLogical.txt"; // 输入文件路径
        String outputFilePath = "C:\\Users\\soya\\Desktop\\allFuzzyLogicalRule.txt"; // 输出文件路径
        int insertFrequency = 4; // 插入频率
        insertRule(inputFilePath,outputFilePath,insertFrequency);
//        insertBlankLines(inputFilePath, outputFilePath, insertFrequency);
        System.out.println("空格行已插入完成！");
    }

    public static void insertBlankLines(String inputFilePath, String outputFilePath, int insertFrequency) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            int count = 0,num = 1;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
                count++;
                if (count % insertFrequency == 0) {
                    writer.write(Integer.toString(num/4));
                    writer.newLine(); // 插入空白行
                }
                num++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void insertRule(String inputFilePath, String outputFilePath, int insertFrequency) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            String line;
            int count = 1;
            while ((line = reader.readLine()) != null) {
                String lines = "\tRULE "+count+": "+ line;
                writer.write(lines);
                writer.newLine();
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
