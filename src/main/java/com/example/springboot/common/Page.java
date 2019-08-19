package com.example.springboot.common;

import com.example.springboot.common.cache.Cache;
import com.google.common.base.CaseFormat;

/**
 * 
* @author 作者 zhangsiming: 
* @version 创建时间：2018年10月25日 下午1:51:21 
* 类说明 分页公共处理实体
 */
public class Page {
    //起始页
    int page;
    //每页显示记录数
    int pageSize;
    //排序字段
    String orderBy = "id";
    //排序类型
    String orderByType = "asc";

    public int getPage() {
        return page<=1 ? 0 : (page-1)*pageSize;
    }
    public int getPageInfo() {
        return page;
    }
    public void setPageInfo(int page) {
        this.page = page;
    }
    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize ) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        for(String str:Cache.getColumnList()){
           String tmpOrderBy=CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,orderBy);
            if(str.equalsIgnoreCase(tmpOrderBy.trim())&&tmpOrderBy.length()<=30){
                this.orderBy = tmpOrderBy.trim();
                break;
            }
        }
    }

    public String getOrderByType() {
        return orderByType;
    }

    public void setOrderByType(String orderByType) {
        if("desc".equalsIgnoreCase(orderByType)||"asc".equalsIgnoreCase(orderByType)){
            this.orderByType = orderByType;
        }
    }
}
