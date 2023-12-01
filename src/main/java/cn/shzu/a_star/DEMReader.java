package cn.shzu.a_star;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description To test something
 * @date 2023/11/16 11:16:12
 */
import org.gdal.gdal.Dataset;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconst;


public class DEMReader {
    public static void main(String[] args) {
        float [][] answer = readDEM("D:\\ArcGis\\Project\\Big\\p10627dsm_Resample_1.tif");
    }
    public static float[][] readDEM(String filePath){
        // 初始化GDAL
        gdal.AllRegister();

        // DEM文件路径

        // 打开DEM文件
        Dataset dataset = gdal.Open(filePath, gdalconst.GA_ReadOnly);
        float[][] elevationData;
        if (dataset != null) {
            // 获取DEM的宽度和高度
            int width = dataset.getRasterXSize();
            int height = dataset.getRasterYSize();

            // 读取DEM的高程数据

            float[] elevationDataDimensional = new float[width*height];
            dataset.GetRasterBand(1).ReadRaster(0, 0, width, height, elevationDataDimensional);
            elevationData = convertToOneDimensional(elevationDataDimensional,width,height);

            // 关闭数据集
            dataset.delete();

            // 打印高程数据的示例
            System.out.println(width);
            System.out.println(height);

        } else {
            System.out.println("Failed to open DEM file.");
            elevationData = null;
        }
        return elevationData;
    }
    // 将一维数组转为二维数组的方法
    private static float[][] convertToOneDimensional(float[] oneDimensionalArray, int rows, int cols) {
        float[][] result = new float[cols][rows];
        int index = 0;

        // 循环填充二维数组
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                result[i][j] = oneDimensionalArray[index++];
            }
        }
        return result;
    }
}

