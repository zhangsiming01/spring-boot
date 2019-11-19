package com.example.springboot.common.dto.mail;

import lombok.*;

/**
 * @author: zhangsiming
 * @Date: 2019/11/15 17:33
 * @Description: 发送邮件实体类
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailMessage {
    /***
     * 收件人
     */
    private String to;

    /***
     *  发件人
     */
    private String from;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String text;

    /**
     * 附件
     */
    private String attachment;

    /***
     * 嵌入资源
     */
    private String inlineResource;

}
