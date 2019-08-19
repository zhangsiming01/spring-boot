package com.example.springboot.common.utis.pdf;

import lombok.Data;

/**
 * @program: spring-boot
 * @description: ${description}
 * @author: zsm
 * @create: 2019-08-16 16:53
 **/
@Data
public class FileTypeName {
    /***
     * 转换前的type
     */
    private String convertFrontType;
    /**
     * 转换后的type
     */
    private String convertAfterType;
}
