package cn.shzu.function;

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
import org.gdal.ogr.*;
import org.gdal.osr.CoordinateTransformation;
import org.gdal.osr.SpatialReference;

import java.util.Arrays;


public class DEMReader {
    private static final int ROWS = 500;
    private static final int COLS = 500;
    private static float [][] map;

    public static void main(String[] args) {
        float [][] ints = slopeMap("F:\\Data\\ArcgisProject\\MyProject1\\Extract_dsm1_1.tif");
//        map = ints;
//        haveOne();
        System.out.println(Arrays.deepToString(ints));
    }
    public static float[][] readDEM(String filePath){
        // 初始化GDAL
        gdal.AllRegister();

        // 打开DEM文件
        Dataset dataset = gdal.Open(filePath, gdalconst.GA_ReadOnly);
        float[][] elevationData;
        if (dataset != null) {
            // 获取DEM的宽度和高度
            int width = dataset.getRasterXSize();
            int height = dataset.getRasterYSize();
//            int width = ROWS;
//            int height = COLS;
            //读取坐标系
            String rasterProjection = dataset.GetProjection();
            System.out.println(rasterProjection);


            // 读取DEM的高程数据

            float[] elevationDataDimensional = new float[width*height];
            dataset.GetRasterBand(1).ReadRaster(0, 0, width, height, elevationDataDimensional);
            elevationData = convertToOneDimensional(elevationDataDimensional,width,height);

//            System.out.println(width);
//            System.out.println(height);
            System.out.println("读取tif文件成功，长:"+height+",宽:"+width);
//            readVector(dataset);
            // 关闭数据集
            dataset.delete();

        } else {
            System.out.println("Failed to open DEM file.");
            elevationData = null;
        }
//        map = elevationData;
        return elevationData;
    }

    /**
     *  进行矢量数据的读取和使用
     * @param rasterDataset 栅格数据集
     */
    public static void readVector(Dataset rasterDataset){
        DataSource vectorDataSource = ogr.Open("D:\\ArcGis\\Project\\165Road.shp");
        if (vectorDataSource !=null){
            //获取矢量数据的空间参考
            SpatialReference vectorSpatialRef = vectorDataSource.GetLayerByIndex(0).GetSpatialRef();
            System.out.println("vectorSpatialRef  "+vectorSpatialRef);

            //进行配准
            String targetSRS = rasterDataset.GetProjection();
            SpatialReference targetSpatialRef = new SpatialReference(targetSRS);
            CoordinateTransformation coordinateTransformation = new CoordinateTransformation(vectorSpatialRef, targetSpatialRef);
            //获取矢量数据坐标
            //获取矢量图层
            Layer vectorLayer = vectorDataSource.GetLayerByIndex(0);
            if (vectorLayer ==null){
                System.err.println("不存在矢量图层");
                return;
            }
            Feature feature;
            vectorLayer.ResetReading();
            while ((feature = vectorLayer.GetNextFeature())!=null){
                Geometry geometry = feature.GetGeometryRef();
                //处理线的坐标和其他属性
                System.out.println("Feature ID :"+feature.GetFID());
                System.out.println("Geometry Type :"+geometry.GetGeometryName());
                System.out.println("Point Count :"+geometry.GetPointCount());
                int transform = geometry.Transform(coordinateTransformation);

            }

            vectorDataSource.delete();
        }
    }

    /**
     * 判断地图中是否含有道路信息的栅格标志
     */
    public static void haveOne(){
        int roadCount = 0;
        int obsCount = 0;
        int freeCount =0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j]==1){
                    roadCount++;
                }else if (map[i][j]==0){
                    obsCount++;
                }else {
                    freeCount++;
                }
            }
        }
        System.out.println(roadCount);
        System.out.println(obsCount);
        System.out.println(freeCount);
    }

    /**
     *
     * @param map 地图信息
     * @return 将栅格地图上不同的类别用不同数字代替
     */
    public static int [][] getSlopMap(float[][] map){
        int [][] mapInfo = new int[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j <map[0].length ; j++) {
                if (map[i][j] == 1){
                    mapInfo[i][j]=1;
                }else if ((map[i][j]<25)&&(map[i][j] != 1)){
                    mapInfo[i][j] = 2;
                }else {
                    mapInfo[i][j] = 0;
                }
            }
        }
        return mapInfo;
    }


    /**
     *
     * @param name 地图路径名称
     * @return 返回getSlopMap方法返回值
     */
    public static float[][] slopeMap(String name){
//        return captureMap(readDEM(name));
//       return getSlopMap(readDEM(name));
       return readDEM(name);
    }

    /**
     *
     * @param oneDimensionalArray 一维数组
     * @param rows 行数
     * @param cols 列数
     * @return 返回二维数组
     */
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
    public static int [][] captureMap(float [][] map){
        int [][] mapInfo = new int[ROWS][COLS];
        for (int i = 0; i < mapInfo.length; i++) {
            for (int j = 0; j <mapInfo.length ; j++) {
                 if (map[i][j]<30){
                    mapInfo[i][j] = 0;
                }else {
                    mapInfo[i][j] = 1;
                }
            }
        }
        return mapInfo;
    }
}

