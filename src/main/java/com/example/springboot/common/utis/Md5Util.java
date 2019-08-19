package com.example.springboot.common.utis;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * 
* @author 作者 zhangsiming: 
* @version 创建时间：2018年10月25日 上午10:56:32 
* 类说明 :md5加密解析
 */
public class Md5Util {
	 public static String MD5(String sourceStr) {
	        String result = "";
	        try {
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            md.update(sourceStr.getBytes());
	            byte b[] = md.digest();
	            int i;
	            StringBuffer buf = new StringBuffer("");
	            for (int offset = 0; offset < b.length; offset++) {
	                i = b[offset];
	                if (i < 0){
	                    i += 256;}
	                if (i < 16){
	                    buf.append("0");}
	                buf.append(Integer.toHexString(i));
	            }
	            result = buf.toString();
	        } catch (NoSuchAlgorithmException e) {
	            System.out.println(e);
	        }
	        return result;
	    }
	 
}
