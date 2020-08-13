import java.text.DecimalFormat;

/**
 * 关于数学计算的工具类
 * @author peter
 * @Date Jun 21, 2018 4:46:21 PM
 */
public class MathUtil {

    //1立方米=1000000000立方毫米
    private static float STERE_TO_CUBIC_MILLIMETRE = 1000000000;
    //2位小数的数字格式
    private static String TWO_DECIMAL= "##0.00";
    private static String ONE_DECIMAL = "##0.0";
    private static String INTEGER = "##0";
    
    /**
     * 体积计算
     * 单位：毫米
     * 
     * @param lengthMillimetre 长
     * @param widthMillimetre 宽
     * @param heightMillimetre 高
     * @return
     */
    public static float cubicMillimetre(float lengthMillimetre,float widthMillimetre,float heightMillimetre)
    {
        float volume =0;
        
        volume = lengthMillimetre * widthMillimetre * heightMillimetre;
        
        return volume;
    }
    
    /**
     * 体积计算,并将结果转换为立方米
     * 单位：毫米
     * 
     * @param lengthMillimetre 长
     * @param widthMillimetre 宽
     * @param heightMillimetre 高
     * @return
     */
    public static float stere(float lengthMillimetre,float widthMillimetre,float heightMillimetre)
    {
        float volume =0;
        
        volume = cubicMillimetre(lengthMillimetre,widthMillimetre,heightMillimetre) / STERE_TO_CUBIC_MILLIMETRE;
        
        return volume;
    }
    
    /**
     * 将浮点数四舍五入到2位小数
     * 
     * @param source 源数据
     * @return
     */
    public static float roundTwoDecimal(float source)
    {
        DecimalFormat df = new DecimalFormat(TWO_DECIMAL); 
        
        return Float.parseFloat(df.format(source));
    }
    
    /**
     * 将浮点数四舍五入到N位小数
     * 
     * @param source 源数据
     * @return
     */
    public static float roundOneDecimal(float source)
    {
        DecimalFormat df = new DecimalFormat(ONE_DECIMAL); 
        
        return Float.parseFloat(df.format(source));
    }
        
    /**
     * 将浮点数四舍五入取整
     * 
     * @param source 源数据
     * @return
     */
    public static float roundInteger(float source)
    {
        DecimalFormat df = new DecimalFormat(INTEGER);
        
        return Float.parseFloat(df.format(source));
    }
    
    /**
     * 将浮点数四舍五入取整
     * 
     * @param source 源数据
     * @return
     */
    public static int roundInteger(int source)
    {
        DecimalFormat df = new DecimalFormat(INTEGER);
        
        return Integer.parseInt(df.format(source));
    }
}
