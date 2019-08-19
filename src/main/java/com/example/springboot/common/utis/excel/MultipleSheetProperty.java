package com.example.springboot.common.utis.excel;

import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import lombok.Data;

import java.util.List;

/**
 * @program: spring-boot
 * @description: ${description}
 * @author: zsm
 * @create: 2019-08-15 15:54
 **/
@Data
public class MultipleSheetProperty {
    private List<? extends BaseRowModel> data;

    private Sheet sheet;
}
