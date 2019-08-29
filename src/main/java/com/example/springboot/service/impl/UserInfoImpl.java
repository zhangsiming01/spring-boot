package com.example.springboot.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.example.springBoot.redis.RedisUtil;
//import com.example.springBoot.test.CustomAnnotation;
import com.example.springboot.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.springboot.common.utis.StringUtils;
import com.example.springboot.common.utis.UnifiedException;
import com.example.springboot.common.utis.download.DownloadAllFiles;
import com.example.springboot.common.utis.download.ExcelImportUtil;
import com.example.springboot.common.utis.download.Write;
import com.example.springboot.dal.mapper.UserInfoMapper;
import com.example.springboot.dal.model.UserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

import com.example.springboot.common.cache.Result;
import com.example.springboot.common.dto.UserInfoTo;
import com.example.springboot.common.enumutis.ErrorCodeConstants;
/**
 * 
* @author 作者 zhangsiming: 
* @version 创建时间：2018年10月25日 下午1:34:08 
* 类说明 获取用户信息实现类
 */
@Service("userInfoService")
public class UserInfoImpl implements UserInfoService{

	@Autowired
	private UserInfoMapper userInfoMapper;
	public UserInfo loadUser(String userCode) {
		Example example = new Example(UserInfo.class);
		example.and().andEqualTo("userCode",userCode);
		UserInfo userinfo= userInfoMapper.selectOneByExample(example);
		return userinfo;
	}

	@Override
//	@CustomAnnotation(name = "SSSSSSSSSSS")
	public PageInfo<UserInfo> listUser(UserInfoTo to) {
		Example example = new Example(UserInfo.class);
		example.and().andEqualTo("userCode",to.getUserCode());
		example.and().andEqualTo("userPassword",to.getUserPassword());
		example.orderBy("userCode").desc();
		PageHelper.startPage(to.getPage(), to.getPageSize());
		List<UserInfo> list = userInfoMapper.selectByExample(example);
		PageInfo resule = new PageInfo(list);
	    return resule;
	}

	@Override
	@Cacheable(cacheNames = "user", key = "#userCode")
	public UserInfo getList(String userCode) {
		Example example = new Example(UserInfo.class);
		example.and().andEqualTo("userCode",userCode);
		List<UserInfo> list = userInfoMapper.selectByExample(example);
		return list.get(0);
	}

	@Override
	@CachePut(cacheNames = "user", key = "#userinfo.userCode")
	public UserInfo saveUser(UserInfo userinfo) throws UnifiedException {
	UserInfo info=	loadUser(userinfo.getUserCode());
		if (info == null) {
			userInfoMapper.insertSelective(userinfo);
		}else {
			throw new UnifiedException(ErrorCodeConstants.USER_0001);
		}
//		redisUtil.set(userinfo.getUserCode(),userinfo);
		UserInfo info1= getList(userinfo.getUserCode());
		return info1;
	}

	@Override
	public void updateUser(UserInfo userinfo) throws UnifiedException{
		UserInfo info=	loadUser(userinfo.getUserCode());
		if (info == null) {
			userInfoMapper.updateByPrimaryKeySelective(userinfo);
		}else {
			throw new UnifiedException(ErrorCodeConstants.USER_0001);
		}
		
	}

	@Override
	public void removeUser(Integer id) {
		userInfoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void listUserExport(UserInfoTo to,
			HttpServletResponse response) throws UnifiedException{
//		ExcelExportUtil excelExportUtil =	new ExcelExportUtil();
		Example example = new Example(UserInfo.class);
		if (StringUtils.isNotEmpty(to.getUserCode())) {
			example.and().andEqualTo("userCode",to.getUserCode());
		}
		if (StringUtils.isNotEmpty(to.getUserPassword())) {
			example.and().andEqualTo("userPassword",to.getUserPassword());
		}
		List<UserInfo> list = userInfoMapper.selectByExample(example);
		List<Object> obList= new ArrayList<>(list);
		 LinkedHashMap<String, String> map = new LinkedHashMap<>();
		 map.put("id", "id");
		 map.put("userCode", "用户名");
		 map.put("userPassword", "密码");
		 Write.createExcel(obList,map,10000,"人员信息导入",400000,"人员信息",response);
	}

	@Override
	public Result saveUserImport(UserInfoTo to, MultipartFile file) throws UnifiedException, IOException {
		ExcelImportUtil  em = new ExcelImportUtil(file.getOriginalFilename(),file.getInputStream());
		Result<List> result = em.readFromExcel(UserInfo.class);
		if (result.isStatus()) {
			List<UserInfo> list = result.getResult();
			if (list != null && list.size()>0) {
				List<UserInfo> userinfoList = importVerification(list, to);
				for (UserInfo userinfo : userinfoList) {
					userInfoMapper.insertSelective(userinfo);
				}
			}else {
				throw new UnifiedException(ErrorCodeConstants.USER_0003);
			}
		}
		return null;
	}

	public List<UserInfo> importVerification(List<UserInfo> list,UserInfoTo to) throws UnifiedException{
		List<UserInfo> userinfoList = new ArrayList<>();
		for (UserInfo userinfo : list) {
			UserInfo info=	loadUser(userinfo.getUserCode());
			if (info == null) {
				userinfo.setUserCode(to.getUserCode());
				userinfo.setUserPassword(to.getUserPassword());
				userInfoMapper.insertSelective(userinfo);
			}else {
				throw new UnifiedException(ErrorCodeConstants.USER_0001);
			}
			userinfoList.add(info);
		}
		return userinfoList;
	}

	@Override
	public void downloadAll(HttpServletRequest request, HttpServletResponse response, String fileName) {
		DownloadAllFiles download = new DownloadAllFiles();
		download.tempDownLoad(request, response, fileName);
	}
}
