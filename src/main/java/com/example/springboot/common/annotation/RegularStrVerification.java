package com.example.springboot.common.annotation;

import tk.mybatis.mapper.util.StringUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author: zhagnsiming
 * @CreateDate: 2019-04-02 14:00
 * @UpdateUser: 更新者
 * @UpdateDate: 2019-04-02 14:00
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * 定义一个验证器
 */
public class RegularStrVerification implements ConstraintValidator<RegularStrAnnotation,String>{
    /**
     * 定义一个验证属性
     */
     private  String verificationStr;
    @Override
    public void initialize(RegularStrAnnotation constraintAnnotation) {
        //通过初始化获取到注解的验证方法verification()，并将值赋给验证属性
        this.verificationStr = constraintAnnotation.verification();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //传入字段参数和验证属性进行业务处理
        return isVerification(value,verificationStr);
    }

    /**
     * 处理业务判断
     * @param value
     * @param verification
     * @return
     */
    public boolean isVerification(String value,String verification){
        if (StringUtil.isEmpty(value)){
            return  false;
        }
        return  value.matches(verification);
    }
}
