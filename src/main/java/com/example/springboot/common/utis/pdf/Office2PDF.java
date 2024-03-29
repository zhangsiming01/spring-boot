package com.example.springboot.common.utis.pdf;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
/**
 * @program: spring-boot
 * @description:  这是一个工具类，主要是为了使Office2003-2007全部格式的文档(.doc|.docx|.xls|.xlsx|.ppt|.pptx)
 * 转化为pdf文件<br>
 * Office2010的没测试<br>
 * pdfTempPath为pdf存放地址，在项目目录下
 * @author: zsm
 * @create: 2019-08-16 17:36
 **/
@Slf4j
public class Office2PDF {
  private static   String PDF = "pdf";
    public static File convertFileToPdf(File sourceFile,String name,String type, String pdfTempPath) throws Exception {
        File pdfFile = new File(pdfTempPath+"/"+name+".pdf");
//        File pdfFile = new File(pdfTempPath+"/"+name);
        // 临时pdf文件
        if (!pdfFile.getParentFile().exists()) {
            pdfFile.getParentFile().mkdirs();
        }
        InputStream inputStream = null;
        OutputStream outputStream = null;

        if (!type.equals(PDF)) {

            // 获得文件格式
            DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();
            DocumentFormat pdfFormat = formatReg.getFormatByFileExtension("pdf");
            DocumentFormat docFormat = formatReg.getFormatByFileExtension(type);


            /**
             * 在此之前需先开启openoffice服务，用命令行打开cd C:\Program Files\OpenOffice.org 3\program （openoffice安装的路径）
             * 输入 soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard
             */
            OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);

            try {
                // stream 流的形式
                inputStream = new FileInputStream(sourceFile);
                outputStream = new FileOutputStream(pdfFile);
                connection.connect();
                DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);

//                File outputFile = new File(pdfTempPath+"/xx.pdf");
                // 假如目标路径不存在,则新建该路径
//                    if (!outputFile.getParentFile().exists()) {
//                        outputFile.getParentFile().mkdirs();
//                    }

                converter.convert(inputStream, docFormat, outputStream, pdfFormat);
//                converter.convert(sourceFile,pdfFile);

            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception(e.getMessage());
            } finally {
                if (connection != null) {
                    connection.disconnect();
                    connection = null;
                }

                try {
                    inputStream.close();
                    outputStream.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }

            }
            System.out.println("转化pdf成功....");

        } else {
            // 复制pdf文件到新的文件
            FileUtils.copyFile(sourceFile, pdfFile);

        }
        return pdfFile;
    }

}
