package com.example.springboot.web.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.springboot.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.springboot.common.cache.Result;
import com.example.springboot.common.dto.UserInfoTo;
import com.example.springboot.dal.model.UserInfo;
import com.example.springboot.web.vo.UserInfoVo;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 *
 * @author 作者 zhangsiming:
 * @version 创建时间：2018年10月25日 下午2:09:26
 * 类说明 用户信息控制器
 */
@Api(description = "用户信息管理")
@RestController
@RequestMapping("/zsm")
public class UserInfoController {
	private static final Logger logger = LoggerFactory
			.getLogger(UserInfoController.class);
	@Autowired
	private UserInfoService userInfoService;
	/**
	 * 用于克隆
	 */
	//新增编辑时用于将to克隆为model
	private BeanCopier userModel = BeanCopier.create(UserInfoTo.class, UserInfo.class,false);
	
	private BeanCopier userVo = BeanCopier.create(UserInfo.class, UserInfoVo.class, false);
	
	@RequestMapping(value="listUser", method = RequestMethod.GET)
	@ApiOperation("用于获取用于信息进行分页查询")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userCode",value="账号",dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "userPassword",value="密码",dataType = "String", paramType = "query"),
	})
	public Result<List<UserInfoVo>> listUser(@ApiIgnore UserInfoTo to){
		PageInfo<UserInfo> userPage = userInfoService.listUser(to);
		List<UserInfo> list = userPage.getList();
		List<UserInfoVo> userVoList = new ArrayList<>();
		for (UserInfo userinfo : list) {
			UserInfoVo vo = new UserInfoVo();
			userVo.copy(userinfo, vo, null);
			userVoList.add(vo);
		}
		return Result.success(userVoList);
	}
	@RequestMapping(value="getList", method = RequestMethod.GET)
	@ApiOperation("根据userCode查询")
	public Result<UserInfo> getList(@ApiParam(value = "userCode",required = true) @RequestParam String userCode){
		UserInfo list = userInfoService.getList(userCode);
		List<UserInfoVo> userVoList = new ArrayList<>();
//		for (UserInfo userinfo : list) {
//			UserInfoVo vo = new UserInfoVo();
//			userVo.copy(userinfo, vo, null);
//			userVoList.add(vo);
//		}
		return Result.success(list);
	}
	public List<UserInfoVo> userInfoVoList(UserInfo userInfo){
		List<UserInfoVo> voList = new ArrayList<>();
		UserInfoVo vo = new UserInfoVo();
		vo.setId(userInfo.getId());
		vo.setUserCode(userInfo.getUserCode());
		vo.setUserPassword(userInfo.getUserPassword());
		voList.add(vo);
		return voList;
	}
	
	@RequestMapping(value="listUserExport", method = RequestMethod.GET)
	@ApiOperation("根据条件导出Excel")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userCode",value="账号",dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "userPassword",value="密码",dataType = "String", paramType = "query"),
	})
	public void listUserExport(@ApiIgnore UserInfoTo to,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		 userInfoService.listUserExport(to,response);
	}
	@RequestMapping(value="saveUserImport", method = RequestMethod.GET)
	@ApiOperation("导入用户信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userCode",value="账号",dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "userPassword",value="密码",dataType = "string", paramType = "query"),
		})
	   public Result saveUserImport(@RequestParam(value="upload") MultipartFile file,@ApiIgnore UserInfoTo to) throws Exception{
	        Result result=userInfoService.saveUserImport(to,file);
	        return result;
	    }
	
	
	@RequestMapping(value="saveUser", method = RequestMethod.GET)
	@ApiOperation("新增用户信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userCode",value="账号",dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "userPassword",value="密码",dataType = "string", paramType = "query"),
		})
	public Result<UserInfo> saveUser(@ApiIgnore UserInfoTo to) throws Exception{
		UserInfo userinfo = new UserInfo();
		userModel.copy(to, userinfo, null);
		UserInfo info = userInfoService.saveUser(userinfo);
		return Result.success(info);
	}

	@RequestMapping(value="updateUser", method = RequestMethod.GET)
	@ApiOperation("编辑用户信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",value="id",dataType = "Integer", paramType = "query"),
		@ApiImplicitParam(name = "userCode",value="账号",dataType = "String", paramType = "query"),
		@ApiImplicitParam(name = "userPassword",value="密码",dataType = "String", paramType = "query"),
		})
	public Result<Void> updateUser(@ApiIgnore UserInfoTo to) throws Exception{
		UserInfo userinfo = new UserInfo();
		userModel.copy(to, userinfo, null);
		userInfoService.updateUser(userinfo);
		return Result.success(null);
	}
	
	@RequestMapping(value="removeUser", method = RequestMethod.DELETE)
	@ApiOperation("删除用户信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",value="id",dataType = "Integer", paramType = "query"),
		})
	public Result<Void> removeUser(@ApiParam(value = "id",required = true) @RequestParam Integer id) {
		userInfoService.removeUser(id);
		return Result.success(null);
	}
	
	@RequestMapping(value="downloadAll", method = RequestMethod.GET)
	@ApiOperation("下载文件")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "fileName",value="fileName",dataType = "String", paramType = "query"),
		})
	public void downloadAll(@ApiParam(value = "fileName",required = true) @RequestParam String fileName,HttpServletResponse response, HttpServletRequest request) {
		userInfoService.downloadAll(request, response, fileName);
	}

}
