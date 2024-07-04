package cn.shzu.sundry;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description test1
 * @date 2023/12/18 12:30:58
 */
import org.gdal.gdal.Dataset;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconst;
import org.gdal.ogr.DataSource;
import org.gdal.ogr.Layer;
import org.gdal.ogr.ogr;
import org.gdal.osr.CoordinateTransformation;
import org.gdal.osr.SpatialReference;

public class CoordinateTransformationExample {

    public static void main(String[] args) {
        // 初始化 GDAL
        gdal.AllRegister();
        ogr.RegisterAll();

        // 栅格数据文件路径
        String rasterFilePath = "path/to/your/raster.tif";

        // 矢量数据文件路径
        String vectorFilePath = "path/to/your/vector.shp";

        // 读取栅格数据
        Dataset rasterDataset = gdal.Open(rasterFilePath, gdalconst.GA_ReadOnly);

        if (rasterDataset != null) {
            System.out.println("Raster Dataset Information:");
            System.out.println("Driver: " + rasterDataset.GetDriver().getShortName());
            System.out.println("Size: " + rasterDataset.getRasterXSize() + " x " + rasterDataset.getRasterYSize());
            System.out.println("Projection: " + rasterDataset.GetProjection());

            // 读取矢量数据
            DataSource vectorDataSource = ogr.Open(vectorFilePath);
            if (vectorDataSource != null) {
                System.out.println("\nVector DataSource Information:");
                System.out.println("Driver: " + vectorDataSource.GetDriver().getName());

                // 获取矢量数据的空间参考
                SpatialReference vectorSpatialRef = vectorDataSource.GetLayerByIndex(0).GetSpatialRef();
                System.out.println("Vector Spatial Reference: " + vectorSpatialRef.ExportToWkt());

                // 获取栅格数据的空间参考
                SpatialReference rasterSpatialRef = new SpatialReference(rasterDataset.GetProjection());

                // 创建坐标转换对象
                CoordinateTransformation coordinateTransformation = new CoordinateTransformation(vectorSpatialRef, rasterSpatialRef);

                // 读取矢量图层
                Layer vectorLayer = vectorDataSource.GetLayerByIndex(0);

                // 遍历矢量图层中的要素
                for (int i = 0; i < vectorLayer.GetFeatureCount(); i++) {
                    // 读取矢量要素
                    org.gdal.ogr.Feature vectorFeature = vectorLayer.GetFeature(i);

                    // 获取矢量要素的几何
                    org.gdal.ogr.Geometry geometry = vectorFeature.GetGeometryRef();

                    // 进行坐标转换
                    geometry.Transform(coordinateTransformation);

                    // 现在你可以使用 geometry 中的坐标进行后续处理
                }

                // 最后，记得关闭数据集
                rasterDataset.delete();
                vectorDataSource.delete();
            } else {
                System.out.println("Failed to open vector data source.");
            }
        } else {
            System.out.println("Failed to open raster dataset.");
        }
    }
}
