import com.coreland.framework.core.api.cache.ICacheManager;
import com.coreland.framework.core.context.SiteContext;
import com.coreland.framework.core.exception.ApplicationException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.MessageFormat;

/**
 * @Description Cookie 工具类
 * @Author liubo
 * @Date 2019年5月17日 下午3:14:26
 */
public class CookieUtil {

	/**
	 * 得到Cookie的值, 不编码
	 * 
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		return getCookieValue(request, cookieName, false);
	}

	/**
	 * 得到Cookie的值,
	 * 
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName, boolean isDecoder) {
		Cookie[] cookieList = request.getCookies();
		if (cookieList == null || cookieName == null) {
			return null;
		}
		String retValue = null;
		try {
			for (int i = 0; i < cookieList.length; i++) {
				if (cookieList[i].getName().equals(cookieName)) {
					if (isDecoder) {
						retValue = URLDecoder.decode(cookieList[i].getValue(), "UTF-8");
					} else {
						retValue = cookieList[i].getValue();
					}
					break;
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return retValue;
	}

	/**
	 * 得到Cookie的值,
	 * 
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName, String encodeString) {
		Cookie[] cookieList = request.getCookies();
		if (cookieList == null || cookieName == null) {
			return null;
		}
		String retValue = null;
		try {
			for (int i = 0; i < cookieList.length; i++) {
				if (cookieList[i].getName().equals(cookieName)) {
					retValue = URLDecoder.decode(cookieList[i].getValue(), encodeString);
					break;
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return retValue;
	}

	/**
	 * 设置Cookie的值 不设置生效时间默认浏览器关闭即失效,也不编码
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue) {
		setCookie(request, response, cookieName, cookieValue, -1);
	}

	/**
	 * 设置Cookie的值 在指定时间内生效,但不编码
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue, int cookieMaxage) {
		setCookie(request, response, cookieName, cookieValue, cookieMaxage, false);
	}

	/**
	 * 设置Cookie的值 不设置生效时间,但编码
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue, boolean isEncode) {
		setCookie(request, response, cookieName, cookieValue, -1, isEncode);
	}

	/**
	 * 设置Cookie的值 在指定时间内生效, 编码参数
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue, int cookieMaxage, boolean isEncode) {
		doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, isEncode);
	}

	/**
	 * 设置Cookie的值 在指定时间内生效, 编码参数(指定编码)
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue, int cookieMaxage, String encodeString) {
		doSetCookie(request, response, cookieName, cookieValue, cookieMaxage, encodeString);
	}

	/**
	 * 删除Cookie带cookie域名
	 */
	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
		doSetCookie(request, response, cookieName, "", -1, false);
	}

	/**
	 * 设置Cookie的值，并使其在指定时间内生效
	 * 
	 * @param cookieMaxage cookie生效的最大秒数
	 */
	private static final void doSetCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue, int cookieMaxage, boolean isEncode) {
		try {
			if (cookieValue == null) {
				cookieValue = "";
			} else if (isEncode) {
				cookieValue = URLEncoder.encode(cookieValue, "utf-8");
			}
			Cookie cookie = new Cookie(cookieName, cookieValue);
			if (cookieMaxage > 0)
				cookie.setMaxAge(cookieMaxage);
			if (null != request) {// 设置域名的cookie
				String domainName = getDomainName(request);
				cookie.setDomain(domainName);
				if (!"localhost".equals(domainName)) {
					// cookie.setDomain(domainName);
				}
			}
			cookie.setPath("/");

			response.addCookie(cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置Cookie的值，并使其在指定时间内生效
	 * 
	 * @param cookieMaxage cookie生效的最大秒数
	 */
	private static final void doSetCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue, int cookieMaxage, String encodeString) {
		try {
			if (cookieValue == null) {
				cookieValue = "";
			} else {
				cookieValue = URLEncoder.encode(cookieValue, encodeString);
			}
			Cookie cookie = new Cookie(cookieName, cookieValue);
			if (cookieMaxage > 0)
				cookie.setMaxAge(cookieMaxage);
			if (null != request) {// 设置域名的cookie
				String domainName = getDomainName(request);

				cookie.setDomain(domainName);
			}
			cookie.setPath("/");

			response.addCookie(cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到cookie的域名
	 */
	private static final String getDomainName(HttpServletRequest request) {
		String serverName = getDomain(request.getRequestURL().toString());

		return serverName;
	}

	/**
	 * @Description: 根据url获取域名
	 * @param url
	 * @param     @return 参数说明
	 * @return String 返回类型
	 */
	private static String getDomain(String url) {
		String domainName = "";
		try {
			if (StringUtil.isEmpty(url)) {
				return "";
			} else {
				if (!url.startsWith("http")) {
					url = "http://" + url;
				}
				URL _url = new URL(url);
				domainName = _url.getHost();
				if ("localhost".equals(domainName) || isIp(_url)) {
					return domainName;
				} else {
					return domainName.substring(domainName.indexOf(".") + 1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return domainName;
	}

	/**
	 * 是否为IP
	 * 
	 * @param @param url
	 * @param @return 参数说明
	 * @return boolean 返回类型
	 */
	private static boolean isIp(URL url) {
		try {

			String host = url.getHost();
			InetAddress address = InetAddress.getByName(host);
			if (host.equalsIgnoreCase(address.getHostAddress())) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 设置cookie，跨域
	 * 
	 * @param response     响应
	 * @param cookieName   名
	 * @param cookieValue  值
	 * @param cookieMaxage 过期时间
	 * @param otherUrl     带其他域名的url
	 * @return void 返回类型
	 */
	public static void setOtherCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String cookieValue, int cookieMaxage, String otherUrl) {
		try {
			Cookie cookie = new Cookie(cookieName, cookieValue);
			if (cookieMaxage > 0) {
				cookie.setMaxAge(cookieMaxage);
			}

			String domainName = getDomain(otherUrl);
			cookie.setDomain(domainName);
			cookie.setPath("/");

			response.addCookie(cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description: 获取临时键
	 * @param 键匹配的值
	 * @return 键
	 */
	public static String getTempKey(String value) {
		return getTempKey(value, "");
	}

	/**
	 * @Description: 获取临时键
	 * @param 键匹配的值
	 * @return 键
	 */
	public static String getTempKey(String value, String key) {
		if (StringUtil.isNullOrEmpty(key)) {
			key = UUIDUtil.get().replaceAll("-", "");
		}
		SiteContext.getBean(ICacheManager.class).put(key, value, 60 * 5);
		return key;
	}

	/**
	 * @Description: 获取临时值
	 * @param tempKey 临时键
	 * @return 值
	 */
	public static String getTempValue(String tempKey) {
		return getTempValue(tempKey, true);
	}

	/**
	 * @Description: 获取临时值
	 * @param tempKey 临时键
	 * @param isThrow 是否将异常抛出
	 * @return 值
	 */
	public static String getTempValue(String tempKey, boolean isThrow) {
		try {
			ICacheManager cacheManager = SiteContext.getBean(ICacheManager.class);
			Object value = cacheManager.get(tempKey);
			if (StringUtil.isNullOrEmpty(value)) {
				throw new ApplicationException(MessageFormat.format("无效的令牌：{0}。", tempKey));
			}
			cacheManager.remove(tempKey);// 用一次干掉
			return String.valueOf(value);
		} catch (Exception e) {
			e.printStackTrace();
			if (isThrow) {
				throw e;
			} else {
				return "";
			}
		}
	}

}
