package http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @author peter
 * @Date Jun 21, 2018 5:27:12 PM
 */
public class HttpClientImg {
	/**
	 * 抓取流
	 * 
	 * @param url
	 * @return
	 */
	public static InputStream getInputStream(String url) {
		HttpClient client = HttpClientFactory.getHttpClient();
		GetMethod get = new GetMethod(url);
		try {
			client.executeMethod(get);
			InputStream inputStream = get.getResponseBodyAsStream();
			return inputStream;
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
