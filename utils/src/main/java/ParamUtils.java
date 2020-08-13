import com.coreland.framework.core.money.Money;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParamUtils {

	public ParamUtils() {
	}

	public static Money getMoneyParameter(HttpServletRequest request, String name) {
		String m = getParameter(request, name, false);
		try {
			return new Money(m);
		} catch (Exception e) {

		}
		return new Money();
	}

	public static String getParameter(HttpServletRequest request, String name) {
		return getParameter(request, name, false);
	}

	public static String getParameter(HttpServletRequest request, String name, String defaultValue) {
		return getParameter(request, name, defaultValue, false);
	}

	public static String getParameter(HttpServletRequest request, String name, boolean emptyStringsOK) {
		return getParameter(request, name, null, emptyStringsOK);
	}

	public static String getParameter(HttpServletRequest request, String name, String defaultValue,
			boolean emptyStringsOK) {
		String temp = request.getParameter(name);

		if (temp != null) {
			if (temp.equals("") && !emptyStringsOK) {
				return defaultValue;
			} else {
				return temp;
			}
		} else {
			return defaultValue;
		}
	}

	public static String[] getParameters(HttpServletRequest request, String name) {
		if (name == null) {
			return new String[0];
		}
		String paramValues[] = request.getParameterValues(name);

		if (paramValues == null || paramValues.length == 0) {
			return new String[0];
		}

		List<Object> values = new ArrayList<Object>(paramValues.length);
		for (int i = 0; i < paramValues.length; i++) {
			if (paramValues[i] != null && !"".equals(paramValues[i])) {
				values.add(paramValues[i]);
			}
		}

		return (String[]) values.toArray(new String[0]);
	}

	public static boolean getBooleanParameter(HttpServletRequest request, String name) {
		return getBooleanParameter(request, name, false);
	}

	public static boolean getBooleanParameter(HttpServletRequest request, String name, boolean defaultVal) {
		String temp = request.getParameter(name);
		if ("true".equalsIgnoreCase(temp) || "on".equalsIgnoreCase(temp) || "T".equalsIgnoreCase(temp)
				|| "1".equalsIgnoreCase(temp)) {
			return true;
		}

		if ("false".equalsIgnoreCase(temp) || "off".equalsIgnoreCase(temp) || "F".equalsIgnoreCase(temp)
				|| "0".equalsIgnoreCase(temp)) {
			return false;

		} else {
			return defaultVal;
		}
	}

	public static int getIntParameter(HttpServletRequest request, String name, int defaultNum) {
		String temp = request.getParameter(name);
		if (temp != null && !temp.equals("")) {
			int num = defaultNum;
			try {
				num = Integer.parseInt(temp.trim());

			} catch (Exception ignored) {

			}
			return num;
		} else {
			return defaultNum;
		}
	}

	public static int[] getIntParameters(HttpServletRequest request, String name, int defaultNum) {
		String paramValues[] = request.getParameterValues(name);

		if (paramValues == null || paramValues.length == 0) {
			return new int[0];
		}

		int values[] = new int[paramValues.length];

		for (int i = 0; i < paramValues.length; i++) {
			try {
				values[i] = Integer.parseInt(paramValues[i].trim());
			} catch (Exception e) {
				values[i] = defaultNum;
			}
		}

		return values;
	}

	public static float getFloatParameter(HttpServletRequest request, String name, float defaultNum) {
		String temp = request.getParameter(name);
		if (temp != null && !temp.equals("")) {
			float num = defaultNum;
			try {
				num = Float.parseFloat(temp.trim());

			} catch (Exception ignored) {

			}
			return num;
		} else {
			return defaultNum;
		}
	}

	public static float[] getFloatParameters(HttpServletRequest request, String name, float defaultNum) {
		String paramValues[] = request.getParameterValues(name);

		if (paramValues == null || paramValues.length == 0) {
			return new float[0];
		}

		float values[] = new float[paramValues.length];
		for (int i = 0; i < paramValues.length; i++) {

			try {
				values[i] = Float.parseFloat(paramValues[i].trim());

			} catch (Exception e) {
				values[i] = defaultNum;
			}
		}

		return values;
	}

	public static double getDoubleParameter(HttpServletRequest request, String name, double defaultNum) {
		String temp = request.getParameter(name);
		if (temp != null && !temp.equals("")) {
			double num = defaultNum;
			try {
				num = Double.parseDouble(temp.trim());

			} catch (Exception ignored) {

			}
			return num;
		} else {
			return defaultNum;
		}
	}

	public static double[] getDoubleParameters(HttpServletRequest request, String name, double defaultNum) {
		String paramValues[] = getParameters(request, name);

		if (paramValues == null || paramValues.length == 0) {
			return new double[0];
		}

		double values[] = new double[paramValues.length];
		for (int i = 0; i < paramValues.length; i++) {

			try {
				values[i] = Double.parseDouble(paramValues[i].trim());

			} catch (Exception e) {
				values[i] = defaultNum;
			}
		}

		return values;
	}

	public static long getLongParameter(HttpServletRequest request, String name, long defaultNum) {
		String temp = request.getParameter(name);
		if (temp != null && !temp.equals("")) {
			long num = defaultNum;
			try {

				num = Long.parseLong(temp.trim());
			} catch (Exception ignored) {

			}
			return num;
		} else {
			return defaultNum;
		}
	}

	public static long[] getLongParameters(HttpServletRequest request, String name, long defaultNum) {
		String paramValues[] = request.getParameterValues(name);
		if (paramValues == null || paramValues.length == 0) {
			return new long[0];
		}
		long values[] = new long[paramValues.length];
		for (int i = 0; i < paramValues.length; i++) {
			try {
				values[i] = Long.parseLong(paramValues[i].trim());

			} catch (Exception e) {
				values[i] = defaultNum;
			}

		}

		return values;
	}

	public static String getAttribute(HttpServletRequest request, String name) {
		return getAttribute(request, name, false);
	}

	public static String getAttribute(HttpServletRequest request, String name, boolean emptyStringsOK) {
		String temp = (String) request.getAttribute(name);
		if (temp != null) {
			if (temp.equals("") && !emptyStringsOK) {

				return null;
			} else {

				return temp;
			}
		} else {
			return null;
		}
	}

	public static boolean getBooleanAttribute(HttpServletRequest request, String name) {
		String temp = (String) request.getAttribute(name);
		return temp != null && temp.equals("true");
	}

	public static int getIntAttribute(HttpServletRequest request, String name, int defaultNum) {
		String temp = (String) request.getAttribute(name);
		if (temp != null && !temp.equals("")) {
			int num = defaultNum;
			try {
				num = Integer.parseInt(temp.trim());

			} catch (Exception ignored) {
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	public static long getLongAttribute(HttpServletRequest request, String name, long defaultNum) {
		String temp = (String) request.getAttribute(name);
		if (temp != null && !temp.equals("")) {
			long num = defaultNum;
			try {
				num = Long.parseLong(temp.trim());

			} catch (Exception ignored) {

			}
			return num;
		} else {
			return defaultNum;
		}
	}

	public static Date getDateParameter(HttpServletRequest request, String name, Date defaultDate) {
		Date temp = getDateParameter(request, name);
		if (temp == null) {
			temp = defaultDate;
		}
		return temp;
	}

	public static Date getDateParameter(HttpServletRequest request, String name) {
		String temp = request.getParameter(name);
		if (temp == null) {
			return null;
		}
		try {
			if (temp.length() == 10) {
				return DateUtil.parseDateNoTime(temp, DateUtil.webFormat);
			} else if (temp.length() > 10) {
				return DateUtil.parseDateNoTime(temp, DateUtil.newFormat);
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public static String getReferer(HttpServletRequest request) {
		return request.getHeader("Referer");
	}

	public static String getHeader(HttpServletRequest request, String name) {
		String object = request.getHeader(name);
		return StringUtil.isNullOrEmpty(object) ? "" : object;
	}
}
