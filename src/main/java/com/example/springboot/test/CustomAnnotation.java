package com.example.springboot.test;

import java.lang.annotation.*;

/**
 * @Author: zhagnsiming
 * @CreateDate: 2019-03-25 9:01
 * @UpdateUser: 更新者
 * @UpdateDate: 2019-03-25 9:01
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomAnnotation {
    String name();
}
