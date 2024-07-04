package cn.shzu.utils;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import net.sourceforge.jFuzzyLogic.FIS;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Set;


/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 计算基础函数值
 * @date 2024/4/21 22:52:32
 */
public class CalculateBaseFunction {
    static double [] baseValue = {0.5540924597034211, 0.5614678627679137, 0.4328499757208123, 0.48877251239509056, 0.3374238702111444, 0.270094501811945, 0.37407707161032316, 0.20437343514474335, 0.1775768045121072, 0.5997029368291747, 0.4044406683594073, 0.4414246789382975, 0.5431608075692248, 0.46555495498227634, 0.42905396256021544, 0.5219091240429988, 0.40733101885505885, 0.29644694035832553, 0.7295904867926312, 0.6815429180341499, 0.4240420187578241, 0.617732287803005, 0.6268555403762225, 0.38366867282392303, 0.5976255582538901, 0.4175310139208628, 0.337443475316474, 0.6370640488300083, 0.49755594221806676, 0.42078436737560565, 0.5426561861866974, 0.42900243769561597, 0.3354387006760145, 0.37114198094270134, 0.42590811792199157, 0.2718039066876637, 0.6901445267147608, 0.608874797753765, 0.5217643962383269, 0.5688625974472674, 0.4767547352757273, 0.28530679197985637, 0.574001473374258, 0.4389589863123821, 0.36371838542051377, 0.800026456878538, 0.7281116202620644, 0.5716257126164346, 0.6659340026537643, 0.6125296332340371, 0.5233315949497965, 0.5324523832471502, 0.38769468668201834, 0.41667152703733223, 0.6236589524510026, 0.5811471157231467, 0.5359048364087118, 0.604297606992032, 0.425420040045732, 0.3629938864686013, 0.5144061734419347, 0.38138487680375854, 0.25168506748398656, 0.7118507084189252, 0.7430355270622016, 0.536311847013552, 0.6590765237059177, 0.6312526513953074, 0.4586058288365079, 0.5847170874479204, 0.46366708972493276, 0.3096330484329324, 0.843700177512896, 0.6847033308019265, 0.7136230187066387, 0.7726353132541948, 0.7539347191086407, 0.5552132069780509, 0.6484103107593898, 0.6169726730670082, 0.4601565138308719};
    public static void main(String[] args) throws IOException {
//        double[] doubles = calculate81Function();
//        System.out.println(Arrays.toString(calculate81Function()));

        BufferedImage image = ImageIO.read(new File("F:\\Data\\ArcgisProject\\MyProject1\\concatenated_tif.tif"));
        BufferedImage image1 = ImageIO.read(new File("F:\\Data\\Data\\concatenated_tif.png"));

        int[][] index = new int[image.getHeight()][image.getWidth()];
        processChunk(image1,index,"con_tif");

    }
    public static double getMax(double[] caseMatrix){
        double max = Double.MIN_VALUE;
        for (double matrix : caseMatrix) {
            if (matrix > max) {
                max = matrix;
            }
        }
        return max;
    }
    public static double getMin(double[] caseMatrix){
        double min = Double.MAX_VALUE;
        for (double matrix : caseMatrix) {
            if (matrix < min) {
                min = matrix;
            }
        }
        return min;
    }

    //归一化
    public static double[] normalization(double[] caseMatrix){
        //原始数据
        double max = getMax(caseMatrix);
        double min = getMin(caseMatrix);
        double chaz = max - min;

        double[] result = new double[81];
        for (int i = 0; i < 81; i++) {
            result[i] = caseMatrix[i]/max;
        }
        max = getMax(result);
        min = getMin(result);
        chaz = max - min;
//        System.out.println("最小："+min);
//        System.out.println("最大："+max);
//        System.out.println("差值："+chaz);
        System.out.println(Arrays.toString(result));
        return result;
    }
    //模糊逻辑代码
    public static double  fuzzyLogicCase(double[] whale){
        //加载模糊逻辑系统
        String fileName = "F:\\Fuzzy\\FuzzyLogicRules\\target.fcl"; // 模糊逻辑控制文件
        FIS fis = FIS.load(fileName, true);
        if (fis == null) {
            System.err.println("Can't load file: '" + fileName + "'");
            return 0.0;
        }
        //设置输入变量
        // 设置输入变量值
        fis.setVariable("slope", whale[0]);
        fis.setVariable("fluctuation", whale[1]);
        fis.setVariable("friction", whale[2]);
        fis.setVariable("mellowness", whale[3]);

        //执行模糊推理
        fis.evaluate();

        //展示模糊输出
        return fis.getVariable("TransitCost").defuzzify();
    }

    //模糊逻辑代码
    public static double  fuzzyLogicCase(double slope, double fluctuation, double friction,double mellowness){
        //加载模糊逻辑系统
        String fileName = "F:\\Fuzzy\\FuzzyLogicRules\\target.fcl"; // 模糊逻辑控制文件
        FIS fis = FIS.load(fileName, true);
        if (fis == null) {
            System.err.println("Can't load file: '" + fileName + "'");
            return 0.0;
        }
        //设置输入变量
        // 设置输入变量值
        fis.setVariable("slope", slope);
        fis.setVariable("fluctuation", fluctuation);
        fis.setVariable("friction", friction);
        fis.setVariable("mellowness", mellowness);

        //执行模糊推理
        fis.evaluate();

        //展示模糊输出
        return fis.getVariable("TransitCost").defuzzify();
    }
    //读取根据白鲸位置得出的规则库，并使用81个验证案例得出结果并计算得出误差结果
    public static double calculateFuzzyError(int num){
        double[][] matrixCase = readMatrixFromFile(num);
        double[] baseResult = calculate81Function();
        double[] fuzzyResult = new double[num];
        double error = 0.0;
        //模糊逻辑得到结果
        for (int i = 0; i < matrixCase.length; i++) {
            fuzzyResult[i] = fuzzyLogicCase(matrixCase[i]);
        }
        for (int i = 0; i < num; i++) {
            double difference = baseResult[i]-fuzzyResult[i];
            error += Math.abs(difference);
        }

//        for (int i = 0; i < baseResult.length; i++) {
//            System.out.println("公式"+i+":"+ baseResult[i]);
//            System.out.println("模糊"+i+":"+ fuzzyResult[i]);
//            System.out.println("差值"+i+":"+ (baseResult[i] - fuzzyResult[i]));
//            System.out.println();
//        }

//        System.out.println("公式计算"+Arrays.toString(baseResult));
//        System.out.println("模糊计算"+Arrays.toString(fuzzyResult));
        return error/num;
    }
    //使用多元回归方程计算81个验证案例的结果并存储
    public  static double[] calculate81Function(){
        double[][] matrixCase = readMatrixFromFile(81);
        double[] result= new double[81];
        for (int i = 0; i < result.length; i++) {
            result[i] =  multivariateEquation(matrixCase[i]);
        }
        return result;
    }
    //获取排序后数组的下标
    public static int[] getSortResult(double[] fit){
        //进行排序
        int [] index = new int[fit.length];
        for (int i = 0; i < fit.length; i++) {
            index[i] = i;
        }
        //复制数组
        double [] fitCopy = new double[fit.length];
        for (int i = 0; i < fitCopy.length; i++) {
            fitCopy[i] = fit[i];
        }
        // 对辅助数组进行排序，根据 index 的值进行比较
        Arrays.sort(fitCopy);
        for (int i = 0; i < fitCopy.length; i++) {
            for (int j = 0; j < fit.length; j++) {
                if (fitCopy[i] == fit[j]){
                    index[i] = j;
                }
            }
        }
        return index;

    }
    //文本读取二维数组
    public static  double[][]  readMatrixFromFile(int num){
        double[][] matrix = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("F:\\Fuzzy\\FuzzyLogicRules\\matrixBase81_plus.txt"));
            String line;
            int numRows = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (matrix == null) {
                    // 根据第一行确定二维数组的大小
                    int numCols = parts.length;
                    matrix = new double[num][numCols];
                }
                for (int i = 0; i < parts.length; i++) {
                    matrix[numRows][i] = Double.parseDouble(parts[i]);
                }
                numRows++;
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("读取文件时出错：" + e.getMessage());
        }
        return matrix;
    }
    //权重多元方程
    public static double multivariateEquation(double[] caseMatrix) {
        double[] w = {0.25, 0.25, 0.25, 0.25};// 如果需要使用相同权重，取消注释此行
        double[] threshold = {30.0,3.5,1.0,1.0};
//        double[] w = {0.8, 0.05, 0.1, 0.05};

        if (caseMatrix[0] >= 30 || caseMatrix[1] >= 3.5 || caseMatrix[2] <= 0.2 || caseMatrix[3] <= 0.1) {
            return 1;
        } else {
            return (w[0] * caseMatrix[0]/threshold[0]) + (w[1] * caseMatrix[1]  / threshold[1]) + (w[2] * (1 - caseMatrix[2] )/threshold[2]) + (w[3] * (1-caseMatrix[3]) /threshold[3]);
        }
    }





    public static void processChunk(BufferedImage image,int [][] index,String name){
        //遍历每个像素点
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                //获取当前RGB值
                int rgb = image.getRGB(i, j);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                if (red==51){
                    index[i][j] = 2;
                }if (red == 179){
                    index[i][j] = 3;
                }if (red==102){
                    index[i][j] = 4;
                }if (green==255){
                    index[i][j] = 1;
                }if (green==153){
                    index[i][j] = 5;
                }if (green==0){
                    index[i][j]=6;
                }
            }
        }
        //将矩阵写入txt文件中
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("F:\\Fuzzy\\image\\imagenum-"+name+".txt");
            for (int i = 0; i < index.length; i++) {
                for (int j = 0; j < index[0].length; j++) {
                    fileWriter.write(String.valueOf(index[i][j]));
                    if (j < index[i].length - 1) {
                        fileWriter.write(" "); // 使用制表符分隔每个元素
                    }
                }
                fileWriter.write("\n"); // 换行
            }
            System.out.println("成功写入");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //读取地物类型
    public static void readObjectTypeByChunk(){
        //颜色分别为：绿色（草地），黑色（岩石），土色（裸土），蓝色（河流），深绿（灌木），深蓝色（雪地）
        int[][] colors = {{0, 255, 153}, {51, 51, 51}, {179, 119, 0}, {102, 204, 255}, {0, 153, 0}, {0, 0, 255}};

    }



    // 判断文件是否为图片文件
    private static boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".gif") || name.endsWith(".bmp");
    }

    // 获取摩擦度
    public static double[][] getFriction(int [][] ints, double[] f){
        double [][] friction = new double[ints.length][ints[0].length];
        for (int i = 0; i < ints.length; i++) {
            for (int j = 0; j < ints[0].length ; j++) {
                int tag =ints[i][j];
                if (tag == 1){
                    friction[i][j] = f[0];
                }else if (tag == 2){
                    friction[i][j] = f[1];
                }else if (tag == 3){
                    friction[i][j] = f[2];
                }else if (tag == 4){
                    friction[i][j] = f[3];
                }else if (tag == 5){
                    friction[i][j] = f[4];
                }else if (tag == 6){
                    friction[i][j] = f[5];
                }
            }
        }
        return  friction;
    }

}
