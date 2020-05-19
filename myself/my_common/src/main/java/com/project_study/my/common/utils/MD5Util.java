package com.project_study.my.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5工具类
 * @author 浩
 *
 */
public class MD5Util
{
	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	
	/**
	 * MD5 加密
	 * @param origin 原始字符串
	 * @param charsetname 字符集
	 * @return
	 */
	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname)) {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			} else {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
			}
		} catch (Exception exception) {
		}
		return resultString;
	}
	
	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}  
  
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n += 256;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
    
	/**
	 * MD5 加密
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	public static String getPassword4MD5(String inStr)
	{
		try{
			MessageDigest md5 = null;
			md5 = MessageDigest.getInstance("MD5");
	
			char[] charArray = inStr.toCharArray();
			byte[] byteArray = new byte[charArray.length];
	
			for (int i = 0; i < charArray.length; i++) {
				byteArray[i] = (byte)charArray[i];
			}
			byte[] md5Bytes = md5.digest(byteArray);
	
			StringBuffer hexValue = new StringBuffer();
	
			for (int i = 0; i < md5Bytes.length; i++)
			{
				int val = ((int)md5Bytes[i]) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
			return hexValue.toString();
		}catch(Throwable e){
			return inStr;
		}
	}
}
