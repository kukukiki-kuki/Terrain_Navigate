package cn.shzu.fuzzyLogic;

import net.sourceforge.jFuzzyLogic.FIS;


/**
 * @author soya
 * @version 1.0
 * @project A_starOnDem
 * @description 使用jFuzzy库对地形点进行模糊逻辑处理
 * @date 2024/3/16 21:26:57
 */
public class TerrainCostCalculatorUsingJFuzzyLogic {
    public static void main(String[] args) {
        // 加载模糊逻辑系统
        String fileName = "F:\\Fuzzy\\FuzzyLogicRules\\target1.fcl"; // 模糊逻辑控制文件
        String fileName1 = "D:\\Data\\JavaData\\A_starOnDem\\src\\main\\java\\cn\\shzu\\fuzzyLogic\\test1.fcl"; // 模糊逻辑控制文件
        FIS fis = FIS.load(fileName, true);
        if (fis == null) {
            System.err.println("Can't load file: '" + fileName + "'");
            return;
        }


        // 设置输入变量值
        fis.setVariable("slope", 25.1);
        fis.setVariable("fluctuation", 3.2);
        fis.setVariable("friction", 0.3);
        fis.setVariable("mellowness", 0.4);

        // 执行模糊推理
        fis.evaluate();

        //展示模糊输出
        double transitCost = fis.getVariable("TransitCost").defuzzify();
        // 获取输出变量值

        // 输出通行代价
        System.out.println("Terrain Cost: " + transitCost);
    }
}
