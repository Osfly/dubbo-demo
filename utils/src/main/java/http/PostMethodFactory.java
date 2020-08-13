package http;

import com.coreland.framework.core.log.Logger;
import com.coreland.framework.core.log.LoggerFactory;
import com.coreland.framework.core.utils.StringUtil;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.UnsupportedEncodingException;

/**
 * PostMethod工厂类，每调用createPostMethod就会创建一个新的PostMethod实例
 * @author peter
 * @Date Jun 21, 2018 5:28:48 PM
 */
public class PostMethodFactory {

    /** 日志记录器*/
    private static final Logger logger            = LoggerFactory.getLogger(PostMethodFactory.class);

    /** content_type名称常量 */
    public final static String  CONTENT_TYPE_KEY  = "Content-Type";

    /** content_type值常量  */
    public final static String  CONTENT_TYPE_XML  = "text/html; charset=";

    /** content_type值常量  */
    public final static String  CONTENT_TYPE_FORM = "application/x-www-form-urlencoded; charset=";

    /**
     * 创建PostMethod
     * @param url 目标url
     * @param soTimeOut response超时时间
     * @param content 发送内容
     * @return PostMethod
     * @throws Exception 
     */
    public static PostMethod createPostMethod(final String url, final int soTimeOut,
                                              final String content, String requestCharset,
                                              String responseCharset) throws Exception {

        //validate first
        if (StringUtil.isBlank(url) || StringUtil.isBlank(content)) {
            throw new Exception("参数校验不通过");
        }

        final PostMethod postMethod = new PostMethod(url);

        postMethod.addRequestHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_XML + requestCharset);

        //对method设置超时时间
        final HttpMethodParams httpMethodParams = new HttpMethodParams();
        httpMethodParams.setSoTimeout(soTimeOut);
        httpMethodParams.setContentCharset(responseCharset);
        postMethod.setParams(httpMethodParams);

        try {
            postMethod.setRequestEntity(new StringRequestEntity(content, null, requestCharset));
        } catch (UnsupportedEncodingException e) {
            logger.error("不支持字符集" + requestCharset, e);
            throw new Exception("系统错误", e);
        }

        return postMethod;
    }

    /**
     * 创建PostMethod
     * @param url 目标url
     * @param soTimeOut response超时时间
     * @param params 发送内容
     * @return PostMethod
     */
    public static PostMethod createPostMethod(final String url, final int soTimeOut,
                                              NameValuePair[] params, String requestCharset,
                                              String responseCharset) {

        final PostMethod postMethod = new PostMethod(url);
        postMethod.addRequestHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_FORM + requestCharset);
        //对method设置超时时间
        final HttpMethodParams httpMethodParams = new HttpMethodParams();
        httpMethodParams.setSoTimeout(soTimeOut);
        httpMethodParams.setContentCharset(responseCharset);
        postMethod.setParams(httpMethodParams);
        postMethod.setRequestBody(params);
        return postMethod;
    }

}
