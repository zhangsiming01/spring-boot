package com.example.springboot;

import com.alibaba.excel.metadata.Sheet;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.example.springboot.common.utis.excel.ExcelUtil;
import com.example.springboot.common.utis.excel.MultipleSheetProperty;
import com.example.springboot.common.utis.pdf.Doc2HtmlUtil;
import com.example.springboot.common.utis.pdf.FileToImgUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationTests.class);

    /**
     * https://www.cnblogs.com/huanghuanghui/p/9365849.html
     */
    @Test
    public void contextLoads() {
    }

    /***
     * 读取少于1000行的Excel
     */
    @Test
    public void readLessThan1000Row() {
        String filePath = "";
        List<Object> objects = ExcelUtil.readLessThan1000Row(filePath);
        objects.forEach(System.out::println);
    }

    /***
     * 读取少于1000行的Excel，可以指定Sheet 和从第几行读取
     */
    @Test
    public void readLessThan1000RowBySheet() {
        String filePath = "";
        Sheet sheet = new Sheet(1, 1);
        List<Object> objects = ExcelUtil.readLessThan1000RowBySheet(filePath, sheet);
        objects.forEach(System.out::println);
    }

    /***
     * 读取大于1000行的Excel
     * 带sheet参数的方法可参照测试方法readLessThan1000RowBySheet()
     */
    @Test
    public void readMoreThan1000Row() {
        String filePath = "";
        List<Object> objects = ExcelUtil.readMoreThan1000Row(filePath);
        objects.forEach(System.out::println);
    }

    /**
     * 生成excle
     * 带sheet参数的方法可参照测试方法readLessThan1000RowBySheet()
     */
    @Test
    public void writeBySimple() {
        String filePath = "D://测试.xlsx";
        List<List<Object>> data = new ArrayList<>();
        List<String> head = new ArrayList<>();
        List<Object> data1 = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            data1.add(i);
            head.add("表头" + i);
        }
        for (int i = 1; i <= 300000; i++) {
            data.add(data1);
        }
        ExcelUtil.writeBySimple(filePath, data, head);
//        head.add(data);
//        data.add(Arrays.asList());
//        data.add(Arrays.asList("111","222","333"));
//        data.add(Arrays.asList("111","222","333"));
//        data.add(Arrays.asList("111","222","333"));
//        List<String> head = Arrays.asList("表头1", "表头2", "表头3");
    }

    /**
     * 生成excle, 带用模型
     * 带sheet参数的方法可参照测试方法readLessThan1000RowBySheet()
     */
    @Test
    public void writeWithTemplate() {
        String filePath = "D://School.xlsx";
        ArrayList<TableHeaderExcelProperty> data = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            TableHeaderExcelProperty tableHeaderExcelProperty = new TableHeaderExcelProperty();
            tableHeaderExcelProperty.setName("000.1154");
            tableHeaderExcelProperty.setAge(i);
            tableHeaderExcelProperty.setSchool("Harvard" + i);
            data.add(tableHeaderExcelProperty);
        }
        ExcelUtil.writeWithTemplate(filePath, data);
    }

    /**
     * 生成Excel, 带用模型,带多个sheet
     */
    @Test
    public void writeWithMultipleSheet() {
        String filePath = "D://多Sheet.xlsx";
        ArrayList<MultipleSheetProperty> list1 = new ArrayList<>();
        for (int j = 1; j < 4; j++) {
            ArrayList<TableHeaderExcelProperty> data = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                TableHeaderExcelProperty property = new TableHeaderExcelProperty();
                property.setName("Spring-boot" + i);
                property.setAge(i);
                property.setSchool("Harvard" + i);
                data.add(property);
            }
            Sheet sheet = new Sheet(1, 0);
            sheet.setSheetName("sheet" + 1);
            MultipleSheetProperty multipleSheetProperty = new MultipleSheetProperty();
            multipleSheetProperty.setData(data);
            multipleSheetProperty.setSheet(sheet);
            list1.add(multipleSheetProperty);
        }
        ExcelUtil.writeWithMultipleSheet(filePath, list1);
    }

    @Test
    public void doc2HtmlUtil() {
        try {
            String filePath = "D:\\";
            File file = null;
            FileInputStream fileInputStream = null;
            file = new File("D:\\功能模块原型图.docx");
            fileInputStream = new FileInputStream(file);
            Doc2HtmlUtil doc2HtmlUtil = Doc2HtmlUtil.getDoc2HtmlUtilInstance();
            try {
                String str = doc2HtmlUtil.fileFormatConversion(fileInputStream, filePath, "docx","pdf");
            } catch (IOException e) {
                logger.error("解析失败，失败原因->{}", e);
            }
        } catch (FileNotFoundException e) {
            logger.error("上传失败，失败原因->{}", e);
        }

    }

    @Test
    public void test01() {
        OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
        try {
            connection.connect();
        } catch (ConnectException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(connection);
        connection.disconnect();
    }

    @Test
    public void test02() {
        String docPath = "D:\\功能模块原型图.docx";
        String pdfPath = "D:\\";
        FileToImgUtil.doc2Imags(docPath, pdfPath, "哈哈");
    }

    @Test
    public void pdfDemo1(){
        String docPath = "D:\\功能模块原型图.docx";
        String pdfPath = "D:\\";
//        PdfDemo.doc2Pdf(docPath,pdfPath);
    }
}
