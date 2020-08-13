package http;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * HttpClient的工厂类，主要的功能 1.创建单例的HttpClient 2.将httpClient默认的retry机制修改，改为不重试
 * 
 * @author peter
 * @Date Jun 21, 2018 5:26:32 PM
 */
public class HttpClientFactory {

	/** httpclient单例 */
	private static HttpClient httpClient;

	/** 设置对单host最大的连接数(默认所有链接都对同一个host) */
	private static int maxConnPerHost = 32;

	/** 设置最大链接数 */
	private static int maxTotalConn = 128;

	/** 链接url的超时时间,单位是毫秒(不是response超时时间) */
	private static int connectionTimeOut = 5000;

	/**
	 * 私有化构造函数
	 */
	private HttpClientFactory() {

	}

	/**
	 * 单例创建方法
	 * 
	 * @return httpClient
	 */
	public static HttpClient getHttpClient() {
		// double check
		if (httpClient == null) {
			synchronized (HttpClientFactory.class) {
				if (httpClient == null) {
					createHttpClient();
				}
			}
		}
		return httpClient;
	}

	/**
	 * HttpClient的创建方法
	 */
	private static void createHttpClient() {

		final HttpConnectionManager httpConManager = populateMultiThreadConnectionMananger();

		populateRetryHandler(httpConManager);
	}

	/**
	 * 组装ConenctionManager,这里组装的是 MultiThreadedHttpConnectionManager
	 * 
	 * @return MultiThreadedHttpConnectionManager
	 */
	private static HttpConnectionManager populateMultiThreadConnectionMananger() {
		final HttpConnectionManager httpConManager = new MultiThreadedHttpConnectionManager();
		final HttpConnectionManagerParams conManagerParam = new HttpConnectionManagerParams();
		// 设置超时时间等参数
		conManagerParam.setMaxTotalConnections(maxTotalConn);
		conManagerParam.setDefaultMaxConnectionsPerHost(maxConnPerHost);
		conManagerParam.setConnectionTimeout(connectionTimeOut);
		httpConManager.setParams(conManagerParam);
		return httpConManager;
	}

	/**
	 * 组装请求重试handler,将默认重试3次改为不重试
	 * 
	 * @param conManager
	 * @param retryhandler
	 */
	private static void populateRetryHandler(final HttpConnectionManager conManager) {
		// 将默认重试设为0
		final DefaultHttpMethodRetryHandler retryhandler = new DefaultHttpMethodRetryHandler(0, false);
		httpClient = new HttpClient(conManager);
		httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, retryhandler);
	}

}
