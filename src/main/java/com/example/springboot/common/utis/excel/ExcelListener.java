package com.example.springboot.common.utis.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: spring-boot
 * @description: 解析监听器，
 * 每解析一行会回调invoke()方法。
 * 整个excel解析结束会执行doAfterAllAnalysed()方法
 * @author: zsm
 * @create: 2019-08-15 15:55
 **/
@Getter
@Setter
@Data
public class ExcelListener extends AnalysisEventListener {

    private List<Object> datas = new ArrayList<>();

    /***
     * 逐行解析
     * @param object 当前行的数据
     * @param context
     */
    @Override
    public void invoke(Object object, AnalysisContext context) {
        //当前行
        //context.getCurrentRowNum()
        if (object != null) {
            datas.add(object);
        }
    }

    /***
     *  解析完所有数据后会调用该方法
      * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //解析结束销毁不用参数
    }
}
