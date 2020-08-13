package http;

import com.coreland.framework.core.log.Logger;
import com.coreland.framework.core.log.LoggerFactory;

import javax.servlet.http.HttpServletRequest;


/**
 * 取得客户端真实IP
 * @author peter
 * @Date Jun 21, 2018 5:28:05 PM
 */
public class HttpClientIp {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientIp.class);
	
	public static String getIpddr(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("proxy-client-ip");

		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("wl-proxy-client-ip");

		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getRemoteAddr();

		}
		logger.info("remote ip:"+ip);
		String[] arrIp = ip.split(",");
		return arrIp[0];

	}

}
