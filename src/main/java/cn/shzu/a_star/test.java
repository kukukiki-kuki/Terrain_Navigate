package cn.shzu.a_star;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description test
 * @date 2023/11/16 15:55:21
 */
public class test {
    public static void main(String[] args) {
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
    }

