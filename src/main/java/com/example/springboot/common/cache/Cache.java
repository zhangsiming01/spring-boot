package com.example.springboot.common.cache;


import java.util.ArrayList;
import java.util.List;
/**
 * 
* @author 作者 zhangsiming: 
* @version 创建时间：2018年10月25日 下午1:51:10 
* 类说明  定义缓存信息
 */
public class Cache {
	private static List<String> columnList = new ArrayList<>();
	public static List<String> getColumnList() {
		return columnList;
	}
	public static void setColumnList(List<String> columnList) {
		Cache.columnList = columnList;
	}
}