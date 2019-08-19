package com.example.springboot.web.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.springboot.common.redis.CacheLock;
import com.example.springboot.common.redis.CacheParam;
import org.springframework.web.bind.annotation.*;

import com.example.springboot.common.cache.Result;
import com.example.springboot.common.dto.testVo;
import com.example.springboot.common.utis.download.DownloadAllFiles;
import com.example.springboot.common.utis.download.Write;

@RestController
@RequestMapping("/zsm")
public class TestController {

/**
	 * 下载2
	 * @param request
	 * @param response
	 * @return
	 * @throws FileNotFoundException 
	 */
//	@RequestMapping(value = "/download2")
	@GetMapping(value = "/download2")
	@ResponseBody
	public void download2(HttpServletRequest request,
			HttpServletResponse response) throws FileNotFoundException {
	     Write write = new Write();
	     List<Object> list = new ArrayList<>();
	      for (int i = 0; i < 10; i++) {
	          testVo vo = new testVo();
	          vo.setId(i);
	          vo.setName("测试"+i);
	          vo.setPassword("测试"+i);
	          vo.setUnitPrice(15891.5+i);
	          list.add(vo);
	      }
	      LinkedHashMap<String, String> map = new LinkedHashMap<>();
	      map.put("id", "id");
	      map.put("name", "名字");
	      map.put("password", "密码");
	      map.put("unitPrice", "单价");
	     Write.createExcel(list,map,10000,"测试导入",400000,"测试",response);
	}
//	@RequestMapping(value = "/domload1")
	@GetMapping("domload1")
	public Result<Void> domload1(HttpServletResponse response, HttpServletRequest request) throws Exception  {
		DownloadAllFiles download = new DownloadAllFiles();
		download.tempDownLoad(request, response, "爱情.txt");
		return Result.success(null);
	}
	@CacheLock(prefix = "test")
	@GetMapping("query")
	public Result<String> query(@CacheParam(name = "token") @RequestParam String token){
		return 	Result.success("success-"+token);
	}
}
