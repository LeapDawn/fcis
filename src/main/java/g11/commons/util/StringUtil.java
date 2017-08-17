package g11.commons.util;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具
 */
public class StringUtil {
	
	public static Date parseDate(String str) throws ParseException {
		Date date = null;
		try {
			if (!str.startsWith("20")) {
				date = new SimpleDateFormat("yyyy-MM-dd").parse("20" + str);
			} else {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
			}
		} catch (ParseException e) {
			throw e;
		}
		return date;
	}
	
	public static boolean search(String[] array, String key) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(key)) {
				return true;
			}
		}
		return false;
	}
	
	// 讲字符串转为Double,空字符串转为0
	public static Double parseDouble(String str) {
		if (str == null || str.equals("")) {
			return 0D;
		} else {
			return Double.valueOf(str);
		}
	}
	
	public static String changeEncoding(String str) {
		if (!checkNotNull(str)){
			return str;
		}
		try {
			return new String(str.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return str;
		}
	}

	public static boolean checkNotNull(String str) {
		if (str != null && !"".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isInt(String string) {
		Pattern pattern = Pattern.compile("[0-9]{1}\\d*");
		Matcher matcher = pattern.matcher(string);
		return matcher.matches();
	}
	
	public static boolean isDouble(String string) {
		Pattern pattern = Pattern.compile("\\d+.\\d+");
		Matcher matcher = pattern.matcher(string);
		return matcher.matches();
	}
	
	public static boolean isNum(String str) {
		if (!checkNotNull(str)) {
			return true;
		}
		return isInt(str) || isDouble(str);
	}
}
