package cn.shzu.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 读csv
 * @date 2024/4/8 17:46:39
 */
public class readCSV {
    public static void main(String[] args) throws IOException {

        // 文件夹路径
        String folderPath = "F:\\Data\\MeteorologicalData\\2023站点气象数据";

        List<String> cnSuffixStrings = new ArrayList<>();
        // 存储所有CSV文件的数据的列表
        List<String[]> allData = new ArrayList<>();
        for (String fileName: Objects.requireNonNull(new File(folderPath).list())) {
            String filePath = folderPath + "/" + fileName;
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            // 跳过第一行（假设它是列名）
            reader.readLine();
            //
            // 读取第二行
            String line = reader.readLine();
            String[] columns = line.split(",");
            String s = columns[6];
            if (s.substring(0,3).contains("CN")){
                System.out.println(fileName);
                cnSuffixStrings.add(fileName);
            }
        }
        System.out.println(cnSuffixStrings);
    }
}
