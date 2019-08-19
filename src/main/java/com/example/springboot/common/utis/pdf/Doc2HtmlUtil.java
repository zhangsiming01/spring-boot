package com.example.springboot.common.utis.pdf;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ConnectException;

/**
 * @program: spring-boot
 * @description: pdf 公共类
 * @author: zsm
 * @create: 2019-08-16 16:03
 **/
@Slf4j
public class Doc2HtmlUtil {
    private static final Logger logger = LoggerFactory.getLogger(Doc2HtmlUtil.class);

    private static Doc2HtmlUtil doc2HtmlUtil;

    /***
     * 格式doc
     */
    private static String DOC = "doc";

    /***
     * 格式docx
     */
    private static String DOCX = "docx";

    /***
     * 格式xls
     */
    private static String XLS = "xls";

    /**
     * 格式xlsx
     */
    private static String XLSX = "xlsx";

    /***
     * 格式ppt
     */
    private static String PPT = "ppt";

    /***
     * 下划线
     */
    private static String UNDERLINE = "_";

    /***
     * 点
     */
    private static String SPOT = ".";

    /**
     * 格式html
     */
    private static String HTML = "html";
    /**
     * 格式pdf
     */
    private static String PDF = "pdf";

    /**
     * 获取Doc2HtmlUtil实例
     *
     * @return
     */
   public static synchronized Doc2HtmlUtil getDoc2HtmlUtilInstance() {
        if (doc2HtmlUtil == null) {
            doc2HtmlUtil = new Doc2HtmlUtil();
        }
        return doc2HtmlUtil;
    }

    public FileTypeName getType(String type, String convertType) {
        FileTypeName fileTypeName = new FileTypeName();
        String suffix = "测试";
        if (DOC.equals(type)) {
            fileTypeName.setConvertFrontType(DOC + UNDERLINE + suffix + SPOT + DOC);
            fileTypeName.setConvertAfterType(DOC + UNDERLINE + suffix + SPOT + convertType);
        } else if (DOCX.equals(type)) {
            fileTypeName.setConvertFrontType(DOCX + UNDERLINE + suffix + SPOT + DOCX);
            fileTypeName.setConvertAfterType(DOCX + UNDERLINE + suffix + SPOT + convertType);
        } else if (XLS.equals(type)) {
            fileTypeName.setConvertFrontType(XLS + UNDERLINE + suffix + SPOT + XLS);
            fileTypeName.setConvertAfterType(XLS + UNDERLINE + suffix + SPOT + convertType);
        } else if (XLSX.equals(type)) {
            fileTypeName.setConvertFrontType(XLSX + UNDERLINE + suffix + SPOT + XLSX);
            fileTypeName.setConvertAfterType(XLSX + UNDERLINE + suffix + SPOT + convertType);
        } else if (PPT.equals(type)) {
            fileTypeName.setConvertFrontType(PPT + UNDERLINE + suffix + SPOT + PPT);
            fileTypeName.setConvertAfterType(PPT + UNDERLINE + suffix + SPOT + convertType);
        }
        return fileTypeName;
    }

    public String fileFormatConversion(InputStream fromFileInputStream, String toFilePath, String type, String convertType) throws IOException {
        FileTypeName fileTypeName = getType(type, convertType);
        File htmlOutputFile = new File(toFilePath + File.separatorChar + fileTypeName.getConvertAfterType());
        File docInputFile = new File(toFilePath + File.separatorChar + fileTypeName.getConvertFrontType());
        if (htmlOutputFile.exists()) {
            htmlOutputFile.delete();
        }
        htmlOutputFile.createNewFile();
        if (docInputFile.exists()) {
            docInputFile.delete();
        }
        docInputFile.createNewFile();
        /**
         * 由fromFileInputStream构建输入文件
         */
        try {
            OutputStream os = new FileOutputStream(docInputFile);
            int bytesRead = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((bytesRead = fromFileInputStream.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

            os.close();
            fromFileInputStream.close();
        } catch (IOException e) {
            logger.error("文件转换出错" + e);
        }

        OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
        try {
            connection.connect();
        } catch (ConnectException e) {
            logger.error("文件转换出错，请检查OpenOffice服务是否启动。" + e);
        }
        // convert
        DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
        converter.convert(docInputFile, htmlOutputFile);
        connection.disconnect();
        // 转换完之后删除word文件
        docInputFile.delete();
        return fileTypeName.getConvertAfterType();
    }


}
