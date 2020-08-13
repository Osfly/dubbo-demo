import com.coreland.framework.core.exception.ApplicationException;

/**
 * 
 * @author peter
 * @Date Jun 21, 2018 5:18:56 PM
 */
public class NumberToChinese {

	private static final String NO2 = "-0123456789.";
	private static final String LOWERCASE = "0123456789";
	private static final String UPPERCASE = "零壹贰参肆伍陆柒捌玖";
	private static final String UNIT = "圆万亿";// 角分
	private static final String FOUR_BIT = "仟佰拾个";

	public NumberToChinese() {
	}

	public static String[] getCapitalization(String strNumber) {
		if (strNumber == null || strNumber.trim().length() == 0 || strNumber.equals("0"))
			return null;

		String[] StrA = null;
		String StrB = "";
		String StrC = "";
		String StrD = "";
		if (strNumber.length() > 7) {
			StrB = strNumber.substring(strNumber.length() - 7, strNumber.length());
			StrC = strNumber.substring(0, strNumber.length() - 7);
			try {
				StrC = getLowerToUpper(StrC);
				StrC = StrC.replaceAll("圆整", "");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("[INFO :]StrC string value is :" + StrC);
				System.out.println("[INFO :]StrB string value is :" + StrB);
			}
		} else {
			StrB = strNumber;
		}
		for (int i = StrB.length() - 1; i >= 0; i--) {
			if (!StrB.substring(i, i + 1).equals(".")) {
				StrD += UPPERCASE.charAt(Integer.parseInt(String.valueOf(StrB.charAt(i)))) + "-";
			}
		}
		if (StrC.length() > 0) {
			StrD += StrC;
		}
		StrA = StrD.split("-");
		return StrA;
	}

	public static String getLowerToUpper(String strNumber) throws ApplicationException {
		int len = 0;
		int count = 0;
		int dotLocation = 0;
		int startLocation = 0;
		int currentLocation = 0;

		String strTemp = "";
		String temp = "";

		if (strNumber == null || strNumber.trim().length() == 0)
			return null;
		if (censorNumber(strNumber))
			throw new ApplicationException("invalid number string :" + strNumber + " ");
		// -------------执行转换
		else {

			len = strNumber.length(); // 取得长度
			dotLocation = strNumber.indexOf("."); // 取得小数点的位置
			// ***maer*使其格式化为 ####.00****//
			if (dotLocation == -1) {
				strNumber = strNumber + ".00";
			}
			int tempp = len - dotLocation;
			if (tempp < 3) {
				for (int j = 0; j < 3 - tempp; j++) {
					strNumber = strNumber + "0";
				}
			}
			// 确定整数的起始位置
			if (dotLocation == -1) {
				startLocation = len;
			} else
			strTemp = "";
			for (int i = 0; i < count; i++) {
				currentLocation = startLocation - 4;
				if (currentLocation < 0)
					currentLocation = 0;
				temp = numberParse(strNumber.substring(currentLocation, startLocation), String.valueOf(UNIT.charAt(i)));
				strTemp = (temp + strTemp);
				startLocation = startLocation - 4;
			}

			if (dotLocation == -1)
				return strTemp + "整";
			if (!strNumber.substring(dotLocation + 1).equals("00")) {
				temp = String.valueOf(strNumber.charAt(dotLocation + 1));
				if (temp.equals("0")) {
					currentLocation = LOWERCASE.indexOf(temp);
					strTemp = strTemp + UPPERCASE.charAt(currentLocation);
				} else {
					currentLocation = LOWERCASE.indexOf(temp);
					strTemp = strTemp + UPPERCASE.charAt(currentLocation);
					strTemp = strTemp + "角";
				}
				temp = String.valueOf(strNumber.charAt(dotLocation + 2));
				if (!temp.equals("0")) {
					currentLocation = LOWERCASE.indexOf(temp);
					strTemp = strTemp + UPPERCASE.charAt(currentLocation);
					strTemp = strTemp + "分";
				}
			}
		}
		return strTemp + "整";

	}

	/**
	 *
	 * @param number
	 * @param unit
	 * @return
	 */
	public static String numberParse(String number, String unit) {
		int startLocation = 0;
		String strTemp = "";
		String temp = "";
		number = Integer.valueOf(number).toString();
		strTemp = "";
		if (number.equals("0"))
			return strTemp;
		if (number.length() == 4 && number.substring(1, 4).equals("000")) {
			strTemp = upperCase(number.substring(0, 1)) + "仟" + unit;
			return strTemp;
		}
		startLocation = 4 - number.length();
		for (int i = 0; i < number.length(); i++) {
			temp = String.valueOf(number.charAt(i));
			if (!temp.equals("0")) {
				strTemp = strTemp + String.valueOf(UPPERCASE.charAt(LOWERCASE.indexOf(temp)));
				strTemp = strTemp + String.valueOf(FOUR_BIT.charAt(startLocation));
			} else if (i < number.length() - 1 && number.charAt(i + 1) != '0') {
				strTemp = strTemp + String.valueOf(UPPERCASE.charAt(LOWERCASE.indexOf(temp)));

			} else if (i == number.length() - 1) {
				strTemp = strTemp + unit;

			}
			startLocation++;
		}
		startLocation = strTemp.indexOf("个");
		if (startLocation != -1)
			strTemp = strTemp.substring(0, startLocation) + unit;
		return strTemp;
	}

	private static String upperCase(String number) {
		int currentLocation;
		currentLocation = LOWERCASE.indexOf(number);
		return String.valueOf(UPPERCASE.charAt(currentLocation));
	}

	private static boolean censorNumber(String strNo) {
		for (int i = 0; i < strNo.length(); i++) {
			if (NO2.indexOf(strNo.substring(i, ++i)) == -1)
				return true;
		}
		return false;
	}

}
