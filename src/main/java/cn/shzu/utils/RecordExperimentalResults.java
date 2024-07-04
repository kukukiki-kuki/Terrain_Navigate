package cn.shzu.utils;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 纪录实验结果
 * @date 2024/4/22 17:30:34
 */
public class RecordExperimentalResults {

    // 将数组写入到文本文件
    // 将一维数组写入到文本文件
    public static void writeArrayToFile(int[] array,double fitness,String name,int weight,double[] bestFavl,long time,String identification) {
        try (FileWriter writer = new FileWriter("F:\\Fuzzy\\FuzzyLogicRules\\experimentAll\\"+name+"\\result"+identification+".txt")) {
            writer.write("best:");
            for (int i = 0; i < array.length; i++) {
                writer.write(String.valueOf(array[i]));
                if (i < array.length - 1) {
                    writer.write(", "); // 每个元素写入一行
                }
            }
            writer.write("\n");
            writer.write("fitness:"+fitness);
            writer.write("\n");
            writer.write("weight:"+weight);
            writer.write("\n");
            writer.write("算法:"+name);
            writer.write("\n");
            for (int i = 0; i <bestFavl.length ; i++) {
                writer.write(String.valueOf(bestFavl[i]));
                if (i<bestFavl.length-1){
                    writer.write(", ");
                }
            }
            writer.write("\n");
            writer.write("时间:"+time);
            System.out.println("Array has been written to file successfully.");
        } catch (IOException e) {
            System.err.println("Error writing array to file: " + e.getMessage());
        }
    }

    //二维数组
    public static void writeArrayToFile(double[][] array, String identification) {
        try (FileWriter writer = new FileWriter("F:\\Fuzzy\\FuzzyLogicRules\\experiment\\result"+identification+".txt")) {
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    writer.write(String.valueOf(array[i][j]));
                    if (j < array[i].length - 1) {
                        writer.write("\t"); // 使用制表符分隔每个元素
                    }
                }
                writer.write("\n"); // 换行
            }
            System.out.println("Array has been written to file successfully.");
        } catch (IOException e) {
            System.err.println("Error writing array to file: " + e.getMessage());
        }
    }
}
