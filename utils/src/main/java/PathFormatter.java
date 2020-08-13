import com.coreland.framework.core.log.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 上传文件路径格式化类，提供静态格式化方法
 * @author youke
 *
 */
public class PathFormatter {
	
	/**
	 * 根据格式化模板字符串，替换对象参数的属性值，当格式化模板为空时，默认设置6位随机数
	 * 上传保存路径,可以自定义保存路径和文件名格式 
	 * <p>当isDefaultReplace=true时，默认执行以下替换</p>
	 * {rand:6} 会替换成随机数,后面的数字是随机数的位数 <br/>
	 * {time} 会替换成时间戳 <br/>
	 * {yyyy} 会替换成四位年份 <br/>
	 * {yy} 会替换成两位年份 <br/>
	 * {MM} 会替换成两位月份 <br/>
	 * {dd} 会替换成两位日期 <br/>
	 * {HH} 会替换成两位小时 <br/>
	 * {mm} 会替换成两位分钟 <br/>
	 * {ss} 会替换成两位秒 <br/>
	 * 
	 * @param pathFormat 格式化字符串参数
	 * @param args 替换对象 （必须）
	 * @return 替换后字符串
	 */
	public static String format(String pathFormat, Object args) {
		return format(pathFormat, args, true);
	}
	
	/**
	 * 根据格式化模板字符串，替换对象参数的属性值，当格式化模板为空时，默认设置6位随机数
	 * 上传保存路径,可以自定义保存路径和文件名格式 
	 * <p>当isDefaultReplace=true时，默认执行以下替换</p>
	 * {rand:6} 会替换成随机数,后面的数字是随机数的位数 <br/>
	 * {time} 会替换成时间戳 <br/>
	 * {yyyy} 会替换成四位年份 <br/>
	 * {yy} 会替换成两位年份 <br/>
	 * {MM} 会替换成两位月份 <br/>
	 * {dd} 会替换成两位日期 <br/>
	 * {HH} 会替换成两位小时 <br/>
	 * {mm} 会替换成两位分钟 <br/>
	 * {ss} 会替换成两位秒 <br/>
	 * @param pathFormat 格式化字符串参数
	 * @param args 替换对象 （必须）
	 * @param isDefaultReplace 是否替换默认字段
	 * @return 替换后字符串
	 */
	public static String format(String pathFormat, Object args, boolean isDefaultReplace) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		if(args != null) {
			for(Field field : args.getClass().getDeclaredFields()) {
				try {
					field.setAccessible(true);
					params.put(field.getName(), field.get(args));
				} catch (Exception e) {
					LoggerFactory.getLogger(PathFormatter.class).error("字符串格式化错误", e);
				}
			}
		}
		
		return format(pathFormat, params, isDefaultReplace);
	}
	
	/**
	 * 根据格式化模板字符串，替换对象参数的属性值，当格式化模板为空时，默认设置6位随机数
	 * 上传保存路径,可以自定义保存路径和文件名格式 
	 * <p>当isDefaultReplace=true时，默认执行以下替换</p>
	 * {rand:6} 会替换成随机数,后面的数字是随机数的位数 <br/>
	 * {time} 会替换成时间戳 <br/>
	 * {yyyy} 会替换成四位年份 <br/>
	 * {yy} 会替换成两位年份 <br/>
	 * {MM} 会替换成两位月份 <br/>
	 * {dd} 会替换成两位日期 <br/>
	 * {HH} 会替换成两位小时 <br/>
	 * {mm} 会替换成两位分钟 <br/>
	 * {ss} 会替换成两位秒 <br/>
	 * 
	 * @param pathFormat 格式化字符串参数
	 * @param args 替换对象 （必须）
	 * @return 替换后字符串
	 */
	public static String format(String pathFormat, Map<String, Object> args) {
		return format(pathFormat, args, true);
	}
	
	/**
	 * 根据格式化模板字符串，替换对象参数的属性值，当格式化模板为空时，默认设置6位随机数
	 * 上传保存路径,可以自定义保存路径和文件名格式 
	 * <p>当isDefaultReplace=true时，默认执行以下替换</p>
	 * {rand:6} 会替换成随机数,后面的数字是随机数的位数 <br/>
	 * {time} 会替换成时间戳 <br/>
	 * {yyyy} 会替换成四位年份 <br/>
	 * {yy} 会替换成两位年份 <br/>
	 * {MM} 会替换成两位月份 <br/>
	 * {dd} 会替换成两位日期 <br/>
	 * {HH} 会替换成两位小时 <br/>
	 * {mm} 会替换成两位分钟 <br/>
	 * {ss} 会替换成两位秒 <br/>
	 * 
	 * @param pathFormat 格式化字符串参数
	 * @param args 替换对象 （必须）
	 * @param isDefaultReplace 是否替换默认字段
	 * @return 替换后字符串
	 */
	public static String format(String pathFormat, Map<String, Object> args, boolean isDefaultReplace) {
        if (StringUtil.isNullOrEmpty(pathFormat))
        {
            pathFormat = "{time}{rand:6}";
        }

        if (isDefaultReplace) pathFormat = formatPath(pathFormat);

        if (args != null)
        {
            for(Entry<String, Object> p : args.entrySet()) {
            	pathFormat = pathFormat.replaceAll("\\{" + p.getKey() + "\\}", p.getValue() == null ? "" : p.getValue().toString());
            }
        }
        return pathFormat;

	}
	
	/**
	 * 默认格式化基础方法
	 * @param pathFormat 格式化字符串
	 * @return 替换后结果
	 */
	private static String formatPath(String pathFormat) {
		Pattern pattern = Pattern.compile("\\{rand:(\\d+)\\}");
        Matcher matcher = pattern.matcher(pathFormat);
        if(matcher.find()) {
        	matcher.reset();
        	while(matcher.find()) {
        		int digit = Integer.parseInt(matcher.group(1));
        		Random rand = new Random();
        		long t = (long)(rand.nextDouble() * Math.pow(10, digit));
        		pathFormat = pathFormat.replaceAll("\\{rand:(\\d+)\\}", StringUtil.alignLeft(Long.toString(t), digit, "0"));
        	}
        }
        Calendar now = Calendar.getInstance();
        
        pathFormat = pathFormat.replaceAll("\\{time\\}", Long.toString(now.getTimeInMillis()));
        pathFormat = pathFormat.replaceAll("\\{yyyy\\}", DateUtil.format(now.getTime(), "yyyy"));
        pathFormat = pathFormat.replaceAll("\\{yy\\}", DateUtil.format(now.getTime(), "yy"));
        pathFormat = pathFormat.replaceAll("\\{MM\\}", DateUtil.format(now.getTime(), "MM"));
        pathFormat = pathFormat.replaceAll("\\{dd\\}", DateUtil.format(now.getTime(), "dd"));
        pathFormat = pathFormat.replaceAll("\\{HH\\}", DateUtil.format(now.getTime(), "HH"));
        pathFormat = pathFormat.replaceAll("\\{mm\\}", DateUtil.format(now.getTime(), "mm"));
        pathFormat = pathFormat.replaceAll("\\{ss\\}", DateUtil.format(now.getTime(), "ss"));

        return pathFormat;

	}
}
