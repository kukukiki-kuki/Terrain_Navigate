package cn.shzu.sundry;

/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description test2
 * @date 2023/12/18 20:26:09
 */


import org.gdal.gdal.*;
import org.gdal.gdalconst.gdalconstConstants;
import org.gdal.ogr.*;
import org.gdal.ogr.Driver;
import org.gdal.osr.SpatialReference;


import java.io.File;
import java.util.Vector;

/**
 * 调整区划代码
 */

public class TestController {


    String addInsureTask( String geoJson) {
        ogr.RegisterAll();
        Dataset raster = gdal.Open("E:\\测试数据\\zzjg_41_16_2022_20220812\\栅格\\zzjg_41_16_2022_20220812.tif", gdalconstConstants.GA_ReadOnly);
//        //获取到六参数数组
//        double[] ori_transform = raster.GetGeoTransform();
//        //最小x
//        double minX = ori_transform[0];
//        //x方向分辨率
//        double resoutionX = ori_transform[1];
//        //x方向旋转角度
//        double angleX = ori_transform[2];
//        //最大y
//        double maxY = ori_transform[3];
//        //Y方向旋转角度
//        double angleY = ori_transform[4];
//        //y方向分辨率
//        double resoutionY = ori_transform[5];
//        //x方向的像素数
//        int pixelX = raster.getRasterXSize();
//        //y方向像素
//        int pixelY = raster.getRasterYSize();



        String tempShpPath="E:\\测试数据\\zzjg_41_16_2022_20220812\\栅格\\mask.shp";
        jsonToShp(geoJson,tempShpPath,raster.GetProjection());


        String resultPath="E:\\测试数据\\zzjg_41_16_2022_20220812\\栅格\\111.tiff";
        warp(resultPath,raster,tempShpPath);
        double area=getArea(resultPath);

        gdal.GDALDestroyDriverManager();
        return area+"平方米";
    }

    /**
     * 矢量裁剪栅格
     * @param targetPath
     * @param raster
     * @param shpPath
     */
    public void warp(String targetPath,Dataset raster,String shpPath){
//        SpatialReference oSRS = new SpatialReference();
//        oSRS.ImportFromEPSGA(3857);
//        System.out.println(oSRS.toString());
//        oSRS.SetWellKnownGeogCS("EPSG:3857");
        Vector<String> vector=new Vector<>();
        vector.addElement("-t_srs");
        vector.addElement("EPSG:3857");//结果转到3857坐标系，便于计算面积
        vector.addElement("-cutline");
        vector.addElement(shpPath);
        vector.addElement("-crop_to_cutline");
        vector.addElement("true");
        WarpOptions warpOptions=new WarpOptions(vector);
        Dataset[] sources=new Dataset[]{raster};

        //矢栅叠加
        gdal.Warp(targetPath,sources,warpOptions);
    }

    /**
     * geoJson转shp文件
     * @param geoJson
     * @param tempShpPath
     * @param srs
     */
    public void jsonToShp(String geoJson, String tempShpPath, String srs){
        //从geoJson构建字符串，构建Geometry
        Geometry geometry=ogr.CreateGeometryFromJson(geoJson);
        Driver shpDriver=ogr.GetDriverByName("ESRI Shapefile");
        if(new File(tempShpPath).exists()){
            //文件存在，提前删除
            shpDriver.DeleteDataSource(tempShpPath);
        }
        System.out.println(srs.toString());
        //创建shp文件
        DataSource tempShp =shpDriver.CreateDataSource(tempShpPath);
        SpatialReference spatialReference=new SpatialReference(srs);//复制坐标系
        Layer layer=tempShp.CreateLayer("polygon",spatialReference);
        Feature feature = new Feature(layer.GetLayerDefn());
        feature.SetGeometry(geometry);
        layer.CreateFeature(feature);
        layer.SyncToDisk();
        tempShp.FlushCache();
    }

    public double getArea(String rasterPath){
        Dataset raster = gdal.Open(rasterPath, gdalconstConstants.GA_ReadOnly);
        int xSize=raster.getRasterXSize();
        int ySize=raster.getRasterYSize();

        Band band=raster.GetRasterBand(1);
        //读取noData值
        Double[] noData=new Double[1];
        band.GetNoDataValue(noData);
        double[] pixValue = new double[xSize * ySize];
        band.ReadRaster(0,0,xSize,ySize,pixValue);
        long count=0;
        for (int i = 0; i < pixValue.length; i++) {
            if(pixValue[i]!=noData[0]){
                count++;
            }
        }
        double pixel = raster.GetGeoTransform()[1];
        double area=pixel*pixel*count;
        return area;

    }


}


