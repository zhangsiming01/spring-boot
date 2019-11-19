package com.example.springboot.common.utis;

import java.security.MessageDigest;

//import sun.misc.BASE64Encoder;

/**
 * @author 作者 zhangsiming:
 * @version 创建时间：2018年10月25日 上午11:18:35
 * 类说明 生成摘要工具类
 */
@SuppressWarnings("restriction")
public class DigestUtil {
    public static final String GBK = "GBK";
    public static final String UTF8 = "UTF-8";

    /**
     * base64
     *
     * @param md5
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] md5) throws Exception {
//        return (new BASE64Encoder()).encodeBuffer(md5);
        return null;
    }

    /**
     * MD5
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptMD5(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        return md5.digest();
    }

    /**
     * 摘要生成
     *
     * @param data    请求数据
     * @param sign    签名秘钥(key或者parternID)
     * @param charset 编码格式
     * @return 摘要
     * @throws Exception
     */
    public static String digest(String data, String sign, String charset) throws Exception {
        String t = encryptBASE64(encryptMD5((data + sign).getBytes(charset)));
        return t.trim();
    }

    /**
     * 调用测试
     * @param args
     * @throws Exception
     */
//    public static void main(String[] args) throws Exception {
//       // System.out.println(digest("[\"762825916522\"]","E2C1308F7C4C6E18D4D3DAE58C8A4348",UTF8));
//    	System.out.println("合肥市".split("市")[0]);
//    }
}