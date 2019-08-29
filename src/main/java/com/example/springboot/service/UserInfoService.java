package com.example.springboot.service;

import com.example.springboot.common.cache.Result;
import com.example.springboot.common.dto.UserInfoTo;
import com.example.springboot.common.utis.UnifiedException;
import com.example.springboot.dal.model.UserInfo;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author 作者 zhangsiming:
 * @version 创建时间：2018年10月25日 下午1:20:16
 * 类说明:用户信息接口类
 */

public interface UserInfoService {
    /**
     *  查询所有用户信息
     * @return List<Userinfo>
     */
    PageInfo<UserInfo> listUser(UserInfoTo to);

    UserInfo getList(String userCode);

    /**
     *  根据条件导出用户信息
     * @param to
     * @param response
     * @throws UnifiedException
     */
    void listUserExport(UserInfoTo to,
                        HttpServletResponse response) throws UnifiedException;

    /**
     * 导入数据
     * @param to
     * @param file
     * @return
     * @throws UnifiedException
     * @throws IOException
     */
    Result saveUserImport(UserInfoTo to , MultipartFile file) throws UnifiedException, IOException;

    /**
     * 新增用户信息
     * @param userinfo
     * @throws UnifiedException
     */
    UserInfo saveUser(UserInfo userinfo)  throws UnifiedException;

    /**
     *  编辑用户信息
     * @param userinfo
     * @throws UnifiedException
     */
    void updateUser(UserInfo userinfo)  throws UnifiedException;
    /**
     * 根据id删除
     * @param id
     */
    void removeUser(Integer id);
    /**
     * 下载文件
     * @param request
     * @param response
     * @param fileName
     * @throws UnifiedException
     */
    void downloadAll(HttpServletRequest request, HttpServletResponse response, String fileName) throws UnifiedException;
}
