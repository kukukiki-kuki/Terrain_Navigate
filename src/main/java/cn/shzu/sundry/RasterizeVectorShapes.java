package cn.shzu.sundry;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description RasterizeVectorShapes
 * @date 2023/12/18 20:37:28
 */
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconst;
import org.gdal.ogr.DataSource;
import org.gdal.ogr.ogr;

public class RasterizeVectorShapes {

    public static void main(String[] args) {
        // 注册 GDAL 驱动
        gdal.AllRegister();

        // 打开栅格数据源
        String rasterFilePath = "D:\\ArcGis\\Project\\Big\\Slope_p106273.tif"; // 替换为实际的栅格文件路径
        String outputRasterFilePath = "D:\\ArcGis\\Project\\Big\\test1.tif"; // 替换为实际的输出栅格文件路径

        // 打开矢量数据源
        String vectorFilePath = "D:\\ArcGis\\Project\\165Road.shp"; // 替换为实际的矢量文件路径
        DataSource dataSource = ogr.Open(vectorFilePath);

        if (dataSource == null) {
            System.err.println("Failed to open the vector data source.");
            return;
        }

        // 获取矢量图层
        org.gdal.ogr.Layer vectorLayer = dataSource.GetLayerByIndex(0);

        if (vectorLayer == null) {
            System.err.println("Failed to open the vector layer.");
            dataSource.delete();
            return;
        }

        // 执行矢量到栅格转换
        String[] options = {"ATTRIBUTE=your_attribute_name", "ALL_TOUCHED=TRUE", "DN=1"};
        double [] resolution={1};
        gdal.RasterizeLayer(gdal.Open(rasterFilePath, gdalconst.GA_Update), new int[]{1}, vectorLayer, resolution);

        // 保存结果到新的栅格文件
        gdal.GetDriverByName("GTiff").CreateCopy(outputRasterFilePath, gdal.Open(rasterFilePath));

        // 关闭数据源
        dataSource.delete();
    }
}

