package com.example.springboot.service;
/**
 * 
* @author 作者 zhangsiming: 
* @version 创建时间：2018年12月18日 下午3:31:17 
* 类说明 书籍接口类
 */

import java.util.List;

import com.example.springboot.common.dto.BookDto;
import com.example.springboot.dal.model.BookInfo;

public interface BookInfoService {
	/**
	 *  分页查询数据信息
	 * @return List<BookInfo>  
	 */
	List<BookInfo> bookList(BookDto to);
	
	/**
	 *  导入数据
	 * @param to
	 */
	void importBoot(BookDto to);
}

