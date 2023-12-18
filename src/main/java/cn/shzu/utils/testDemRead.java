package cn.shzu.utils;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import java.awt.image.RenderedImage;
import java.awt.image.SampleModel;
import java.io.File;
/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description test on TIFF file
 * @date 2023/12/17 16:47:47
 */
public class testDemRead {
    public static void main(String[] args) {
        // 指定非规则的TIFF文件路径
        String filePath = "D:\\ArcGis\\Project\\Big\\c165Road_PolylineToRaster.tif";

        // 通过JAI库读取TIFF文件
        RenderedOp image = JAI.create("fileload", filePath);

        // 获取图像数据
        RenderedImage renderedImage = image.getAsBufferedImage();

        int height = renderedImage.getHeight();
        int weight = renderedImage.getWidth();
        System.out.println(height);
        System.out.println(weight);

        SampleModel minTileX = renderedImage.getSampleModel();
        System.out.println(minTileX);
        // 这里可以对图像进行进一步的处理，或者将其用于你的应用
    }
}
