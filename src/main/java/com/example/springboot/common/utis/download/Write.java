package com.example.springboot.common.utis.download;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.example.springboot.common.enumutis.ErrorCodeConstants;
import com.example.springboot.common.utis.UnifiedException;
/**
 * 
* @author 作者 zhangsiming: 
* @version 创建时间：2018年12月18日 上午11:09:31 
* 类说明 通过流的形式实现导出excel
 */ 
public class Write {
	private static SXSSFWorkbook workbook = null;
	public static void createExcel(List<Object> list, LinkedHashMap<String, String> map,int batchCount,String fileName,int maxCount,String sheetName,HttpServletResponse response) {
    	OutputStream out = null;
    	try {	 
    		out = response.getOutputStream();
    		Boolean  isSuccess = excelBigDataOutput(list,map,batchCount,fileName,maxCount,sheetName,response);
    		workbook.write(out);
    		if (!isSuccess) {
    			throw new UnifiedException(ErrorCodeConstants.USER_0002);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {  
		    try {  	
		        out.close();  
		    } catch (IOException e) {  
		        e.printStackTrace();
		    }  
		} 
	}
	
	public static Boolean excelBigDataOutput(List<Object> list, LinkedHashMap<String, String> map,int batchCount,String fileName,int maxCount,String sheetName,HttpServletResponse response) {
    	Boolean  isSuccess= true;
        try {
        	StringBuilder stringBuilder = new StringBuilder();  
      	    stringBuilder.append(fileName);  
      	    stringBuilder.append(".xls");
        	response.setContentType("application/x-msdownload");
    		response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(stringBuilder.toString(), "UTF-8"));
            //创建Excel工作簿对象
           workbook = new SXSSFWorkbook (maxCount);
            //在工作簿中创建工作表对象
            Sheet sheet = workbook.createSheet();
            //设置工作表的名称
            workbook.setSheetName(0, sheetName);
            //行
            Row row = null;
            //列
            Cell cell = null;
            //创建表头
            int title=0;
            //创建第1行
            row = sheet.createRow(0);
            for (String key : map.keySet()) {
                String value = map.get(key);
                String headValue = value;
                //新增一列
                cell=row.createCell(title);
                //设置单元格的数据格式为字符串
                //向单元格中写入数据
                cell.setCellValue(headValue);
                title++;
            }
            List<Object> newList = new ArrayList<Object>();
            int batchTotal = 0;
            //分批次处理
            for(int i=0;i<list.size();i++){
                newList.add(list.get(i));
                if(batchCount == newList.size()||i == list.size()-1){
                    writeFileContent(workbook,newList, map, sheet,batchTotal*batchCount);
                    batchTotal++;
                    newList.clear();
                }
            }
            return isSuccess;
        } catch (Exception e) {
            System.out.println("导出失败"+e);
            isSuccess = false;
            return  isSuccess;
        }
    }

    public static void writeFileContent(SXSSFWorkbook workbook, List<Object> list, LinkedHashMap<String, String> map, Sheet sheet, int j) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
        Row row;
        Cell cell;
        // 写入文件内容
        for(int i=0;i<list.size();i++){
            //创建第二行到最后一行
            row = sheet.createRow(i+j+1);
            //返回迭代的下一个元素。
            Object rowObje = list.get(i);
            //list集合的所有对象
            Class<?> clz = rowObje.getClass();
            //写入文件内容的列的开始
            int content=0;
            for (String key : map.keySet()) {
                //通过反射，通过键获取类的所有的名称和数据类型
                Field field = clz.getDeclaredField(key);
                //通过反射获取键的数据类型
                String fieldType = field.getGenericType().toString();
                String methodName = "get";
                //布尔类型的是特殊的
                if (("boolean").equals(fieldType)) {
                    methodName = "is";
                }

                //通过反射，得到对象的get方法和数据格式
                Method m = (Method) rowObje.getClass().getMethod(methodName + key.substring(0,1).toUpperCase() + key.substring(1));
                //反射调用对象里面的方法
                Object value = m.invoke(rowObje);
                //新增1列
                cell = row.createCell(content);
                //给单元格设置数据格式，为字符串
                cell.setCellValue(value.toString());
                content++;
            }
        }
    }
}
