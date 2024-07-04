package cn.shzu.ara;

import cn.shzu.function.DEMReader;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description test
 * @date 2023/11/16 15:55:21
 */
public class test {
    public static void main(String[] args) {
        test.DEMSlopeTest();
    }
    public void ArrayNumberTest(){
        int [][] information = new int[3][4];
        int [] b = new int[9];
        for (int i = 0; i < 9; i++) {
            b[i] = i+1;
        }
        int index = 0;
        for (int i = 0; i <3; i++) {
            for (int j = 0; j < 4; j++) {
                information[i][j] = index++;
            }
        }
        System.out.println(information[1][0]);
    }
    public static void DEMSlopeTest(){
        float[][] slope = DEMReader.readDEM("D:\\ArcGis\\Project\\Big\\Slope_p106272.tif");
        int count = 0;
        for (float[] floats : slope) {
            for (int j = 0; j < slope[0].length; j++) {
                if (floats[j] >= 25) {
                    count++;
                }
            }
        }
        System.out.println(count);
    }
    }

