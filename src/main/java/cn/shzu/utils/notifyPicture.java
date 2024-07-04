package cn.shzu.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 检测图片格式是否正确
 * @date 2024/3/16 17:55:25
 */
public class notifyPicture {
    public static void listFiles(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        System.out.println(files[0].getName());
        System.out.println(files[1].getName());
        String fileName ;
        String fileName2;
        for (int i = 0; i < files.length; i=i+2) {
            fileName = files[i].getName();
            fileName2 = files[i+1].getName();
            if (fileName.substring(0, fileName.lastIndexOf('.')).equals(fileName2.substring(0, fileName2.lastIndexOf('.')))){
                System.out.println("正确");
                System.out.println(i);
            }else {
                System.out.println(fileName2);
                System.out.println("错误");
            }
        }
    }
    public static void deleteFiles(String directoryPath,String directoryPath2){
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        File directory2 = new File(directoryPath2);
        File[] files2 = directory2.listFiles();
        ArrayList <String> strings = new ArrayList<>();
        int count = 0;
        for (int i = 1; i < files.length+1; i+=2) {
            strings.add(files[i].getName());
        }
        for (int i = 0; i < files2.length; i++) {
            if (!(strings.contains(files2[i].getName()))){
                System.out.println(files2[i].getName());
                if (files2[i].delete()){
                    System.out.println("删除成功");
                }
                count++;
            }
        }
        System.out.println(count);
    }
    public static void copyFile(String directoryPathIn,String directoryPathOut){
        File directoryOut = new File(directoryPathOut);
        File directoryIn= new File(directoryPathIn);
        File [] files = directoryOut.listFiles();
        for (File file:files) {
            if (file.getName().endsWith(".json")) {
                try {
                    FileInputStream fis = new FileInputStream(file);
                    FileOutputStream fos = new FileOutputStream(new File(directoryIn, file.getName()));

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }
                    fis.close();
                    fos.close();
                    System.out.println("文件 " + file.getName() + " 已复制到目标文件夹。");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
//        listFiles("F:\\Data\\Tiff\\processtiff\\165");
//        deleteFiles("F:\\Data\\Tiff\\processtiff\\165","F:\\Data\\Tiff\\processtiff\\165labelme");
        copyFile("F:\\Data\\Tiff\\processtiff\\165label","F:\\Data\\Tiff\\processtiff\\165fix");
    }
}
