package com.example.springboot.common.utis;
/**
 * 
* @author 作者 zhangsiming: 
* @version 创建时间：2018年10月25日 上午11:17:11 
* 类说明字符串工具类。
 */
public abstract class StringUtils {

	private StringUtils() {
	}

	/**
	 * 检查指定的字符串是否为空。
	 * <ul>
	 * <li>SysUtils.isEmpty(null) = true</li>
	 * <li>SysUtils.isEmpty("") = true</li>
	 * <li>SysUtils.isEmpty("   ") = true</li>
	 * <li>SysUtils.isEmpty("abc") = false</li>
	 * </ul>
	 * 
	 * @param value
	 *            待检查的字符串
	 * @return true/false
	 */
	public static boolean isEmpty(String value) {
		int strLen;
		if (value == null || (strLen = value.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(value.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}
	
    public static boolean isNotEmpty(String value) {
        return !StringUtils.isEmpty(value);
    }

	/**
	 * 检查对象是否为数字型字符串,包含负数开头的。
	 */
	public static boolean isNumeric(Object obj) {
		if (obj == null) {
			return false;
		}
		char[] chars = obj.toString().toCharArray();
		int length = chars.length;
		if (length < 1) {
			return false;
		}

		int i = 0;
		if (length > 1 && chars[0] == '-') {
			i = 1;
		}
		for (; i < length; i++) {
			if (!Character.isDigit(chars[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查指定的字符串列表是否不为空。
	 */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}

	/**
	 * 把通用字符编码的字符串转化为汉字编码。
	 */
	public static String unicodeToChinese(String unicode) {
		StringBuilder out = new StringBuilder();
		if (!isEmpty(unicode)) {
			for (int i = 0; i < unicode.length(); i++) {
				out.append(unicode.charAt(i));
			}
		}
		return out.toString();
	}

	public static String toUnderlineStyle(String name) {
		StringBuilder newName = new StringBuilder();
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if (Character.isUpperCase(c)) {
				if (i > 0) {
					newName.append("_");
				}
				newName.append(Character.toLowerCase(c));
			} else {
				newName.append(c);
			}
		}
		return newName.toString();
	}

	public static String convertString(byte[] data, int offset, int length) {
		if (data == null) {
			return null;
		} else {
			try {
				return new String(data, offset, length, "UTF-8");
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static byte[] convertBytes(String data) {
		if (data == null) {
			return null;
		} else {
			try {
				return data.getBytes("UTF-8");
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 姓名脱敏
	 * 
	 * @param userName
	 * @return
	 */
	public static String nameFilter(String userName) {
		if (StringUtils.isEmpty(userName) || userName.length() == 1) {
			return userName;
		} else if (userName.length() == 2) {
			return userName.substring(0, 1) + "*";
		} else if (userName.length() == 3) {
			return userName.substring(0, 1) + "*" + userName.substring(2, 3);
		} else {
			// 只保留首位字符
			int length = userName.length();
			String first = userName.substring(0, 1);
			String last = userName.substring(length - 1, length);
			for (int j = 0; j < length - 2; j++) {
				first = first + "*";
			}
			return first + last;
		}
	}

	/**
	 * 地址脱敏
	 * 
	 * @param address
	 * @return
	 */
	public static String addressFilter(String address) {
		if (StringUtils.isEmpty(address) || address.length() <= 3) {
			return address;
		}
		// 屏蔽一半的字符
		int length = address.length();
		int sizeNeedToReplace = length / 2;
		int firstIndex = (length - sizeNeedToReplace) / 2;
		int lastIndex = firstIndex + sizeNeedToReplace - 1;
		char data[] = address.toCharArray();
		// 循环替换字符
		for (int i = firstIndex; i <= lastIndex; i++) {
			data[i] = '*';
		}
		return new String(data);
	}

}
