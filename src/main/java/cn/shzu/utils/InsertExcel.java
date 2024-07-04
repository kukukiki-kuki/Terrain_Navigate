package cn.shzu.utils;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.*;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 记录随机产生的数组数据
 * @date 2024/4/20 12:37:15
 */
public class InsertExcel {
    public static void main(String[] args) {
        writer2Excel();
    }
    private static String FILE_NAME = "C:\\Users\\soya\\Desktop\\RandomArray.xlsx";
    public static void writer2Excel(){
        try {
            int[] data = {1, 2, 3, 4, 5}; // Sample data

            // Read existing file or create new if not exists
            File file = new File(FILE_NAME);
            XSSFWorkbook workbook;
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
            } else {
                workbook = new XSSFWorkbook();
            }

            Sheet sheet = workbook.getSheetAt(0); // Assuming you want to write to the first sheet
            int lastRowNum = sheet.getLastRowNum();
            Row row = sheet.createRow(lastRowNum + 1); // Create a new row below the last row

            // Write data to cells
            for (int i = 0; i < data.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(data[i]);
            }

            // Write the workbook back to the file
            FileOutputStream fos = new FileOutputStream(FILE_NAME);
            workbook.write(fos);
            fos.close();
            workbook.close();

            System.out.println("Data has been written to Excel successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
