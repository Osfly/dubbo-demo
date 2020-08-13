import com.coreland.framework.core.exception.SystemException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sun.xml.internal.ws.developer.SerializationFeature;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * json数据处理工具
 * @author peter
 * @Date Jun 21, 2018 5:44:09 PM
 */
public class JsonUtil {

    static ObjectMapper mapper = new ObjectMapper();
    static {
        //序列化的时候序列对象的所有属性
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //如果是空对象的时候,不抛异常
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //反序列化的时候如果多了其他属性,不抛出异常
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //取消时间的转化格式,默认是时间戳,可以取消,同时需要设置要表现的时间格式
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 允许出现特殊字符和转义符
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        // 允许出现单引号
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 字段保留，将null值转为""
        mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>()
        {
            @Override
            public void serialize(Object o, JsonGenerator jsonGenerator,
                                  SerializerProvider serializerProvider)
                    throws IOException
            {
                jsonGenerator.writeString("");
            }
        });
    }

    /**
     * 将java对象转换成JSON格式的字串
     * 
     * @param object
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static String toJson(Object object) {
        if (object == null) {
            return "";
        }
        Writer writer = new StringWriter();
        try {
			mapper.writeValue(writer, object);
		} catch (IOException e) {
			throw new SystemException(e);
		}
        return writer.toString();
    }

    /**
     * 将JSON格式的字串转换成JAVA对象
     * 
     * @param json json数据
     * @param clazz class类
     * @return  类
     * @throws IOException IO异常
     * @throws JsonMappingException     json转换异常
     * @throws JsonParseException   json格式化异常
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        try {
			return mapper.readValue(json, clazz);
		} catch (IOException e) {
			throw new SystemException(e);
		}
    }

    /**
     * 将JSON格式的字串转换成JAVA对象
     * 
     * @param json json数据
     * @param clazz class类
     * @return  类
     * @throws IOException IO异常
     * @throws JsonMappingException     json转换异常
     * @throws JsonParseException   json格式化异常
     */
    public static <T> T toObject(String json, JavaType javaType) {
        try {
			return mapper.readValue(json, javaType);
		} catch (IOException e) {
			throw new SystemException(e);
		}
    }

    /**
     * 将JSON格式的字串转换成List集合对象
     * @param json json数据
     * @param clazz class类
     * @return 类
     * @throws IOException IO异常
     * @throws JsonMappingException     json转换异常
     * @throws JsonParseException   json格式化异常
     */
    public static <T> ArrayList<T> toList(String json, Class<T> clazz) {
        if(StringUtil.isEmpty(json)){
            return null;
        }
        JavaType javaType = getCollectionType(ArrayList.class, clazz);
        try {
			return mapper.readValue(json, javaType);
		} catch (IOException e) {
			throw new SystemException(e);
		}
    }

    /**
     * 取得指定的集合类型
     * 
     * @param collectionClass
     * @param elementClasses
     * @return
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
