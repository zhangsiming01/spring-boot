package com.example.springboot.common.utis.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @program: spring-boot
 * @description: excel 工具类
 * @author: zsm
 * @create: 2019-08-15 14:44
 **/
@Slf4j
public class ExcelUtil {
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    private static Sheet initSheet;

    static {
        initSheet = new Sheet(1, 0);
        initSheet.setSheetName("sheet");
        //设置自适应宽度
        initSheet.setAutoWidth(Boolean.TRUE);
    }

    /***
     *  读取小于1000行数据
     * @param filePath 文件绝对路径
     * @return
     */
    public static List<Object> readLessThan1000Row(String filePath) {
        return readLessThan1000RowBySheet(filePath, null);

    }

    /***
     *  读取小于1000行数据。带样式
     *  filePath 文件绝对路径
     *   initSheet ：
     *      sheetNo: sheet页码，默认为1
     *      headLineMun: 从第几行开始读取数据，默认为0, 表示从第一行开始读取
     *      clazz: 返回数据List<Object> 中Object的类名
     * @param filePath 文件绝对路径
     * @param sheet sheet
     * @return
     */
    public static List<Object> readLessThan1000RowBySheet(String filePath, Sheet sheet) {
        //如果里面的值是null、“”、“ ”，那么 返回的值是false,否则返回true。
        if (!StringUtils.hasText(filePath)) {
            return null;
        }
        //三元运算符，若sheet不为null 取传参，否则 取initSheet
        sheet = sheet != null ? sheet : initSheet;
        //字节输入流
        InputStream fileStream = null;

        try {
            fileStream = new FileInputStream(filePath);
            return EasyExcelFactory.read(fileStream, sheet);
        } catch (FileNotFoundException e) {
            logger.error("找不到文件或文件路径错误，文件：{}", filePath);
        } finally {
            try {
                if (fileStream != null) {
                    fileStream.close();
                }
            } catch (IOException e) {
                logger.error("excel读取小于1000行数据文件读取失败，失败原因：{}", e);
            }
        }
        return null;
    }


    /***
     *  读取大于1000行的数据
     * @param filePath 文件绝对路径
     * @return
     */
    public static List<Object> readMoreThan1000Row(String filePath) {
        return readMoreThan1000RowBySheet(filePath, null);
    }

    public static List<Object> readMoreThan1000RowBySheet(String filePath, Sheet sheet) {
        if (!StringUtils.hasText(filePath)) {
            return null;
        }
        sheet = sheet != null ? sheet : initSheet;
        InputStream fileStream = null;
        try {
            fileStream = new FileInputStream(filePath);
            ExcelListener excelListener = new ExcelListener();
            //读 07 版大于 1000 行数据返回 List
            EasyExcelFactory.readBySax(fileStream, sheet, excelListener);
            return excelListener.getDatas();
        } catch (FileNotFoundException e) {
            logger.error("找不到文件或文件路径错误，文件：{}", filePath);
        } finally {
            try {
                if (fileStream != null) {
                    fileStream.close();
                }
            } catch (IOException e) {
                logger.error("excel读取大于1000行的数据文件读取失败，失败原因：{}", e);
            }
        }
        return null;
    }

    /***
     *  生成Excel
     * @param filePath 文件绝对路径
     * @param data 数据源
     * @param head 表头
     */
    public static void writeBySimple(String filePath, List<List<Object>> data, List<String> head) {
        writeSimpleBySheet(filePath, data, head, null);
    }

    /***
     *  生成Excel
     * @param filePath 文件绝对路径
     * @param data 数据源
     * @param head 表头
     * @param sheet Excel页面样式
     */
    public static void writeSimpleBySheet(String filePath, List<List<Object>> data, List<String> head, Sheet sheet) {
        sheet = (sheet != null) ? sheet : initSheet;

        if (head != null) {
            List<List<String>> list = new ArrayList<>();
            head.forEach(h -> list.add(Collections.singletonList(h)));
            sheet.setHead(list);
        }
        //字节输出流
        OutputStream outputStream = null;
        ExcelWriter writer = null;
        try {
            outputStream = new FileOutputStream(filePath);
            writer = EasyExcelFactory.getWriter(outputStream);
            writer.write1(data, sheet);
        } catch (FileNotFoundException e) {
            logger.error("找不到文件或文件路径错误，文件：{}", filePath);
        } finally {
            try {
                if (writer != null) {
                    writer.finish();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                logger.error("excel文件导出失败，失败原因：{}", e);
            }
        }
    }

    /***
     *  生成Excel
     * @param filePath 绝对路径
     * @param data 数据源
     */
    public static void writeWithTemplate(String filePath, List<? extends BaseRowModel> data) {
        writeWithTemplateAndSheet(filePath,data,null);
    }

    /***
     *  生成Excel
     * @param filePath 文件绝对路径
     * @param data 数据源
     * @param sheet Excel页面样式
     */
    public static void writeWithTemplateAndSheet(String filePath, List<? extends BaseRowModel> data, Sheet sheet) {
        //CollectionUtils 使用  https://blog.csdn.net/chaoge321/article/details/83788667
        if (CollectionUtils.isEmpty(data)) {
            return;
        }
        sheet = (sheet != null) ? sheet : initSheet;
        sheet.setClazz(data.get(0).getClass());
        OutputStream outputStream = null;
        ExcelWriter writer = null;
        try {
            outputStream = new FileOutputStream(filePath);
            writer = EasyExcelFactory.getWriter(outputStream);
            writer.write(data, sheet);
        } catch (FileNotFoundException e) {
            logger.error("找不到文件或文件路径错误，文件{}", filePath);
        } finally {
            try {
                if (writer != null) {
                    writer.finish();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                logger.error("excel文件导出失败，失败原因{}", e);
            }
        }
    }


    /**
     * 生成多Sheet 的Excel
     * @param filePath 文件绝对路径
     * @param multipleSheetProperties
     */
    public static void writeWithMultipleSheet(String filePath, List<MultipleSheetProperty> multipleSheetProperties) {
        if (CollectionUtils.isEmpty(multipleSheetProperties)) {
            return;
        }
        OutputStream outputStream = null;
        ExcelWriter writer = null;
        try {
            outputStream = new FileOutputStream(filePath);
            writer = EasyExcelFactory.getWriter(outputStream);
            for (MultipleSheetProperty multipleSheetProperty : multipleSheetProperties) {
                Sheet sheet = multipleSheetProperty.getSheet() != null ? multipleSheetProperty.getSheet() : initSheet;
                if (!CollectionUtils.isEmpty(multipleSheetProperty.getData())){
                    sheet.setClazz(multipleSheetProperty.getData().get(0).getClass());
                }
                writer.write(multipleSheetProperty.getData(),sheet);
            }
        } catch (FileNotFoundException e) {
            logger.error("找不到文件或文件路径错误，文件：{}",filePath);
        }finally {
            try {
                if (writer !=null){
                    writer.finish();
                }
                if (outputStream != null){
                    outputStream.close();
                }
            } catch (IOException e) {
               logger.error("excel生成多Sheet文件导出失败，失败原因：{}",e);
            }
        }
    }

}
