package com.example.springboot.web;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.example.springboot.Application;
/**
 *
 * @author 作者 zhangsiming:
 * @version 创建时间：2018年10月25日 下午2:07:26
 * 类说明
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

}
