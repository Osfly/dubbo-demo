import org.apache.commons.io.FileUtils;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

/**
 * 序列化工具类
 * @author peter
 * @Date Aug 9, 2018 9:36:01 AM
 */
public class SerializationUtils {

	/**
	 * 序列化对象
	 * @param obj	实现序列化接口对象
	 * @return	序列化的值
	 */
	public static byte[] serialize(Object obj) {
		if(null == obj) {
			return null;
		}
		if(obj instanceof byte[]) {
			return (byte[]) obj;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.flush();
		} catch (IOException e) {
			throw new IllegalArgumentException("对象序列化失败: " + obj.getClass(), e);
		}
		return baos.toByteArray();
	}
	
	/**
	 * 将序列化对象转换为xml文本字符串工具
	 * @param entity 序列化对象
	 * @return xml格式字符串
	 */
    public static <T extends Serializable> String serializeXML(T entity) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(out));
        encoder.writeObject(entity);
        encoder.close();
        return out.toString();
    }
	
	/**
	 * 反序列化对象
	 * @param bytes	序列化值
	 * @return	对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deserialize(byte[] bytes) {
		
		if(null == bytes) {
			return null;
		}
		try {
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
			return (T)ois.readObject();
		} catch (IOException e) {
			throw new IllegalArgumentException("对象反序列化失败", e);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("对象反序列化失败", e);
		}
	}
	
	
	/**
	 * 将xml格式字符串反序列化成对象
	 * @param xmlPath 反序列化文件地址
	 * @return 反序列化对象
	 */
	public static <T> T deserializeXML(String xmlPath) throws IOException {
		return deserializeXML(new File(xmlPath));
	}
	
	/**
	 * 将xml格式字符串反序列化成对象
	 * @param xml 序列化字符串
	 * @return 反序列化对象
	 * @throws IOException 文件读取异常
	 */
	public static <T> T deserializeXML(File xmlFile) throws IOException {
        return deserializeXML(FileUtils.readFileToByteArray(xmlFile));
    }

	/**
	 * 将xml格式字符串反序列化成对象
	 * @param xmlBytes 序列化数据
	 * @return 反序列化对象
	 */
    @SuppressWarnings("unchecked")
	public static <T> T deserializeXML(byte[] xmlBytes) {
        ByteArrayInputStream in = new ByteArrayInputStream(xmlBytes);
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(in));
        decoder.close();
        return (T) decoder.readObject();
    }
    
}
