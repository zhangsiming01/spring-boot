package com.example.springboot.common.utis.download;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.springboot.common.enumutis.ErrorCodeConstants;
import com.example.springboot.common.utis.UnifiedException;
/**
 * 
* @author 作者 zhangsiming: 
* @version 创建时间：2018年12月20日 下午1:50:16 
* 类说明 通过流下载文件
 */
public class DownloadAllFiles {
	private static final Logger logger = LoggerFactory.getLogger(DownloadAllFiles.class);
	    public void tempDownLoad(HttpServletRequest request,HttpServletResponse response,String fileName) throws UnifiedException{
	        InputStream in = null;
	        try{
	            //1.设置文件	ContentType类型，这样设置，会自动判断下载文件类型
	            response.setContentType("multipart/form-data");  
	            //通过文件路径获得File对象
	            String a= "D://";
	            File file = new File(a+fileName);  
	            in = new FileInputStream(file);
				//2.设置文件头：最后一个参数是设置下载文件名
				response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(file.getName(),"UTF-8"));
				//3.通过response获取ServletOutputStream对象(out)  
				int b = 0;  
				byte[] buffer = new byte[512];  
				while (b != -1){  
				   b = in.read(buffer);
				   if(b != -1){
				   	//4.写到输出流(out)中
				        response.getOutputStream().write(buffer,0,b);
				   }
				}
	        } catch (Exception e) {  
	        }finally{
	            try {
	                if (in != null) {
	                    in.close();
	                }else {
	                	 logger.error("下载的文件不存在！！！");
	                	 throw new UnifiedException(ErrorCodeConstants.DOWNLOAD_0001); 
					}
					response.getOutputStream().flush();
	            } catch (IOException e) {
	                e.printStackTrace();
	                logger.error("关闭文件IOException!");
	            }
	        }

	    }
}
