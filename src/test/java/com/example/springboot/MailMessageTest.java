package com.example.springboot;

import com.example.springboot.common.dto.mail.MailMessage;
import com.example.springboot.service.MailMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: zhangsiming
 * @Date: 2019/11/19 16:26
 * @Description: 短信发送测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailMessageTest {
    @Autowired
    private MailMessageService mailMessageService;

    private MailMessage.MailMessageBuilder commonMail() {
        return MailMessage.builder()
                .from("m18571415580@163.com")
                .to("fangylxy@163.com")
                .subject("龙哥");
    }

    @Test
    public void testSendSimpleMessage() {
        MailMessage mailMessage = commonMail().text("龙哥真帅").build();
        mailMessageService.sendSimpleMessage(mailMessage);
    }

    @Test
    public void testSendMimeMessagePropagator() {
        MailMessage mailMessage = commonMail().text("Hello, preparator mime message ! ")
                .build();
        mailMessageService.sendMimeMessagePropagator(mailMessage);
    }


    @Test
    public void sendBasicMimeMessage() {
        MailMessage message = commonMail()
                .text("Hello, basic mime email ! ")
                .build();
        mailMessageService.sendBasicMimeMessage(message);
    }

    @Test
    public void sendAttachmentsMimeMessage() {
        MailMessage message = commonMail()
                .text("Hello, attachment mime email ! ")
                .attachment("D://gallery/1.jpg")
                .build();
        mailMessageService.sendAttachmentsMimeMessage(message);
    }

    @Test
    public void sendInlineImageMimeMessage() {
        MailMessage message = commonMail()
                .text("Hello, inline image mime email ! ")
                .inlineResource("D://gallery/1.jpg")
                .build();
        mailMessageService.sendInlineImageMimeMessage(message);
    }

}
