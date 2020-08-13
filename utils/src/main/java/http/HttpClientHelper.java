package http;

import com.coreland.framework.core.log.Logger;
import com.coreland.framework.core.log.LoggerFactory;
import com.coreland.framework.core.utils.StringUtil;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * 发送http请求的帮助类
 * 
 * @author peter
 * @Date Jun 21, 2018 5:26:49 PM
 */
public class HttpClientHelper {

	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(HttpClientHelper.class);

	public static HttpClientResponse executePost(final String url, final String content) throws Exception {
		return executePost(url, content, 5000, "utf-8", "utf-8");
	}

	public static HttpClientResponse executePost(final String url, final String content, final int timeOut)
			throws Exception {
		return executePost(url, content, timeOut, "utf-8", "utf-8");
	}

	/**
	 * 发送post类型的http请求
	 * 
	 * @param url
	 *            请求的url
	 * @param content
	 *            发送的内容
	 * @param timeOut
	 *            等待响应超时时间
	 * @return HttpClientResponse
	 * @throws Exception
	 */
	public static HttpClientResponse executePost(final String url, final String content, final int timeOut,
			final String requestCharset, final String responseCharset) throws Exception {
		// validate first
		if (StringUtil.isBlank(url) || StringUtil.isBlank(content)) {
			throw new Exception("参数校验不通过");
		}
		// httpClient为单例
		final HttpClient httpClient = HttpClientFactory.getHttpClient();

		// postMethod为prototype
		final PostMethod postMethod = PostMethodFactory.createPostMethod(url, timeOut, content, requestCharset,
				responseCharset);

		if (logger.isDebugEnabled()) {
			logger.debug("HTTP发送的URL为" + url + ",超时时间为(秒)" + timeOut + ",内容为" + content);
		}
		return execute(httpClient, postMethod);
	}

	/**
	 * 发送post类型的http请求
	 * 
	 * @param url
	 *            请求的url
	 * @param params
	 *            发送的内容
	 * @return HttpClientResponse
	 * @throws Exception
	 */
	public static HttpClientResponse executePost(final String url, NameValuePair[] params) throws Exception {
		// httpClient为单例
		final HttpClient httpClient = HttpClientFactory.getHttpClient();
		final int timeOut = 5000;
		final PostMethod postMethod = PostMethodFactory.createPostMethod(url, timeOut, params, "utf-8", "utf-8");
		if (logger.isDebugEnabled()) {
			logger.debug("HTTP发送的URL为" + url + ",超时时间为(秒)" + timeOut);
		}
		return execute(httpClient, postMethod);
	}

	public static String executeGet(final String url) throws Exception {
		// validate first
		if (StringUtil.isBlank(url)) {
			throw new Exception("参数校验不通过");
		}
		final HttpClient httpClient = HttpClientFactory.getHttpClient();
		// 设置连接超时时间(单位毫秒)
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
		final GetMethod getMethod = new GetMethod(url);
		// 对method设置超时时间
		final HttpMethodParams httpMethodParams = new HttpMethodParams();
		// 设置读数据超时时间(单位毫秒)
		httpMethodParams.setSoTimeout(10000);
		getMethod.setParams(httpMethodParams);

		String json = "";
		try {
			httpClient.executeMethod(getMethod);
			json = getMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	private static HttpClientResponse execute(HttpClient httpClient, PostMethod postMethod) throws Exception{
		try {
			final int returnCode = httpClient.executeMethod(postMethod);
			final HttpClientResponse response = new HttpClientResponse();
			if (logger.isDebugEnabled()) {
				logger.debug("HTTP返回的原始报文:" + postMethod.getResponseBodyAsString());
			}
			response.setResponseCode(returnCode);
			response.setResponseBody(postMethod.getResponseBodyAsString());
			return response;
		} catch (SocketTimeoutException e) {
			logger.error("Http等待返回报文超时,url=" + postMethod.getURI().getURI() + ",读取超时", e);
			throw new Exception("读取超时", e);
		} catch (ConnectTimeoutException e) {
			logger.error("Http连接服务器超时,url=" + postMethod.getURI().getURI() + ",连接超时", e);
			throw new Exception("连接超时", e);
		} catch (NoHttpResponseException e) {
			logger.error("Http连接,对方服务器过载,url=" + postMethod.getURI().getURI() + ",服务器过载", e);
			throw new Exception("服务器过载", e);
		} catch (Exception e) {
			logger.error("通过Http发送报文时异常,url=" + postMethod.getURI().getURI() + ",系统错误", e);
			throw new Exception("系统错误", e);
		} finally {
			postMethod.releaseConnection();
		}
	}

}
