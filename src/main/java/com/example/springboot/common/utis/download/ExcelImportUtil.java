package com.example.springboot.common.utis.download;

import java.io.File;

import org.apache.poi.ss.usermodel.Workbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.springboot.common.cache.Result;
/**
 * 
* @author 作者 zhangsiming: 
* @version 创建时间：2018年10月26日 下午1:08:19 
* 类说明 Excel导入公共实现方法（通过反射进行验证）
 */
public class ExcelImportUtil {
	
	private Workbook workbook;
	
	//定义类型
	public static final String STRING = "java.lang.String";
	
	public static final String INTEGER = "java.lang.Integer";
	
	public static final String LONG = "java.lang.Long";
	

    public ExcelImportUtil(String fileDir) throws Exception {
        //这里的异常我是直接抛出去的,也就是你们自己要捕捉异常 因为我真的不知道异常以后要返回什么给你们
        //一般来说只有FileNotFoundException,IOException 这2种异常
        File file = new File(fileDir);
        //还有就是这里我不想判断的  但是需求要这样 我也就做一做 看别人在异常里面做处理 好low
        if(isExcel2003(fileDir)){
            workbook = new HSSFWorkbook(new FileInputStream(file));
        }else if(isExcel2007(fileDir)){
            workbook = new XSSFWorkbook(OPCPackage.open(fileDir));
        }
    }

    public ExcelImportUtil(String fileName, InputStream input) throws IOException {
        //这里的异常我是直接抛出去的,也就是你们自己要捕捉异常 因为我真的不知道异常以后要返回什么给你们
        //一般来说只有FileNotFoundException,IOException 这2种异常
        //还有就是这里我不想判断的  但是需求要这样 我也就做一做 看别人在异常里面做处理 好low
        if(isExcel2003(fileName)){
            workbook = new HSSFWorkbook(input);
        }else if(isExcel2007(fileName)){
            workbook = new XSSFWorkbook(input);
        }else {
            throw new IOException("文件类型错误");
        }
    }

    /**
     * 读取excel表中的数据.
     * @param classObject 转换的类对象(请注意使用数据的封装类型)
     * @return List 返回对象的List
     */
    public Result<List> readFromExcel(Class classObject) {

        if (classObject == null){
            throw new NullPointerException();
        }
        List result = new ArrayList();
        // 获得该类的所有属性
        Field[] fields = classObject.getDeclaredFields();

        // 读取excel数据
        // 获得第一个sheet
        Sheet sheet = workbook.getSheetAt(0);
        // 获取表格的总行数
        // 需要加一
        int rowCount = sheet.getLastRowNum() + 1;
        if (rowCount < 1) {
            return Result.error("请检查excel是否存在数据");
        }
        // 获取表头的列数
        int columnCount = sheet.getRow(0).getLastCellNum();
        // 读取表头信息,确定需要用的方法名---set方法
        // 用于存储方法名
        // 表头列数即为需要的set方法个数 也就是定义数组的长度
        String[] methodNames = new String[columnCount];
        // 用于存储属性类型
        String[] fieldTypes = new String[columnCount];
        // 获得表头行对象
        Row titleRow = sheet.getRow(0);
        // // 遍历表头列
        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            // 某一列的内容
            String data = titleRow.getCell(columnIndex).toString();
            // 使其首字母大写
            String methodData = Character.toUpperCase(data.charAt(0)) + data.substring(1, data.length());
            // set的具体属性
            methodNames[columnIndex] = "set" + methodData;
            // 遍历属性数组
            for (int i = 0; i < fields.length; i++) {
                // 属性与表头相等
                if (data.equals(fields[i].getName())) {
                    // 将属性类型放到数组中
                    fieldTypes[columnIndex] = fields[i].getType().getName();
                }
            }
        }
        // 逐行读取数据 从1开始 忽略表头
        for (int rowIndex = 2; rowIndex < rowCount; rowIndex++) {
            // 获得行对象
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                Object obj = null;
                // 实例化该泛型类的对象一个对象
                try {
                    obj = classObject.newInstance();
                } catch (Exception e1) {
                    //这里是内部异常处理
                    return Result.error("在第"+(rowIndex+1)+"行实例化对象出错");
                }

                // 获得本行中各单元格中的数据
                for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                    Cell cell = row.getCell(columnIndex);
                    if(null!=cell){
                        // 获取属性值 poi读取的数字默认返回的都是double类型
                        String data=getCellValue(cell);
                        // 获取要调用方法的方法名
                        String methodName = methodNames[columnIndex];
                        try {
                            // 这部分可自己扩展 这部分主要是对属性的类型进行转换,当然也可以实体定义String 后面自己转换
                            if (STRING.equals(fieldTypes[columnIndex])) {
                                // 设置要执行的方法--set方法参数为String 这里不需要转换 因为值默认Sting
                                Method method = classObject.getDeclaredMethod(methodName, String.class);
                                // 执行该方法
                                method.invoke(obj, data);
                            } else if (INTEGER.equals(fieldTypes[columnIndex])) {
                                // 设置要执行的方法--set方法参数为int
                                Method method = classObject.getDeclaredMethod(methodName, Integer.class);
                                //非空判断，否则会报java.lang.NumberFormatException: empty String
                                if(data!=null && data.length()>0){
                                    // 这里不要奇怪为什么要转一下 double 上面已经说了 poi读取的数字默认返回的都是double类型
                                    double dataDouble = Double.parseDouble(data);
                                    int dataInt = (int) dataDouble;
                                    // 执行该方法
                                    method.invoke(obj, dataInt);
                                }
                            }else if(LONG.equals(fieldTypes[columnIndex])){
                                // 设置要执行的方法--set方法参数为long
                                Method method = classObject.getDeclaredMethod(methodName, Long.class);
                                if(data!=null && data.length()>0){
                                    // 这里不要奇怪为什么要转一下 double 上面已经说了 poi读取的数字默认返回的都是double类型
                                    double dataDouble = Double.parseDouble(data);
                                    long dataLong=new Double(dataDouble).longValue();
                                    // 执行该方法
                                    method.invoke(obj,dataLong);
                                }
                            }
                        } catch (Exception e) {
                            return Result.error("在第"+(rowIndex+1)+"行"+(columnIndex+1)+"属性设置值出错");
                        }
                    }
                }
                result.add(obj);
            }
        }
        return Result.success(result);
    }
    /**
     * 是否是2003的excel，返回true是2003
     *
     * @param filePath 文件完整路径
     * @return boolean true代表是2003
     */
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * 是否是2007的excel，返回true是2007
     *
     * @param filePath 文件完整路径
     * @return boolean true代表是2007
     */
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
    /**
     * 对Excel的各个单元格的格式进行判断并转换
     */
    public String getCellValue(Cell cell) {
        String cellValue = "";
        DecimalFormat df = new DecimalFormat("#");
        switch (cell.getCellTypeEnum()) {
            case STRING:
                cellValue = cell.getRichStringCellValue().getString().trim();
                break;
            case NUMERIC:
                cellValue = df.format(cell.getNumericCellValue());
                break;
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
                break;
            case FORMULA:
                cellValue = cell.getCellFormula();
                break;
            default:
                cellValue = "";
        }
        return cellValue;
    }



}
