package com.example.springboot.common.utis.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Component;

/**
 * 
* @author 作者 zhangsiming: 
* @version 创建时间：2018年10月26日 下午1:35:20 
* 类说明 Excel导出公共方法（反射控制）
 */
@Component
public class ExcelExportUtil {
//	public static void main(String[] args) {
//        List<Object> list = new ArrayList<>();
//        for (int i = 0; i < 100000; i++) {
//            testVo vo = new testVo();
//            vo.setId(i);
//           vo.setUnitPrice(15891.5+i);
//            list.add(vo);
//        }
//        LinkedHashMap<String, String> map = new LinkedHashMap<>();
//        map.put("id", "id");
//        map.put("name", "名字");
//        map.put("password", "密码");
//        map.put("unitPrice", "单价");
//        ExcelExportUtil.excelBigDataOutput(list,map,10000,"测试导入",400000,"测试");
//    }

    public  Boolean excelBigDataOutput(List<Object> list, LinkedHashMap<String, String> map,int batchCount,String fileName,int maxCount,String sheetName) {
    	Boolean  isSuccess= true;
        try {
    	   StringBuilder stringBuilder = new StringBuilder();  
    	   stringBuilder.append(fileName);  
    	   stringBuilder.append(".xls");
            //文件保存路径
            String filePath = "D:";
            File xlsFile = new File(filePath,stringBuilder.toString());
            //创建一个向指定 File 对象表示的文件中写入数据的文件输出流。
            FileOutputStream fos = new FileOutputStream(xlsFile);
            //创建Excel工作簿对象
            SXSSFWorkbook workbook = new SXSSFWorkbook (maxCount);
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
            //将文档对象写入文件输出流
            workbook.write(fos);
            if(fos!=null){
                fos.close();//关闭流
            }
            System.out.println("导出完成");
            return isSuccess;
        } catch (Exception e) {
            System.out.println("导出失败"+e);
            isSuccess = false;
            return  isSuccess;
        }
    }

    public  void writeFileContent(SXSSFWorkbook workbook, List<Object> list, LinkedHashMap<String, String> map, Sheet sheet, int j) throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
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
