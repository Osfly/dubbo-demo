import com.coreland.framework.core.context.SiteContext;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * xml序列化
 * @author peter
 *
 */
public class XMLSerizlizationUtils {

	/**
	 * 对象转xml
	 * @param obj
	 * @return
	 */
	public static <T> String toString(Object obj) {
		XStream xStream=new XStream(new StaxDriver());
		xStream.setClassLoader(SiteContext.getClassLoader());
		xStream.processAnnotations(obj.getClass());
		return xStream.toXML(obj);
	}
	
	/**
	 * xml转对象
	 * @param xml
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T TemporalObjects(String xml, Class<T> clazz) {
		XStream xStream=new XStream(new StaxDriver());
		xStream.setClassLoader(SiteContext.getClassLoader());
		xStream.processAnnotations(clazz);
		return (T) xStream.fromXML(xml);
	}
	
}
