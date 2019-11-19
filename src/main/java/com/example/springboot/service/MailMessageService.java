package com.example.springboot.service;

import com.example.springboot.common.dto.mail.MailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.File;

/**
 * @author: zhangsiming
 * @Date: 2019/11/19 15:47
 * @Description: 发送邮件工具类
 */
@Component
public class MailMessageService {
    private static final Logger logger = LoggerFactory
            .getLogger(MailMessageService.class);
    @Autowired
    private MailSender mailSender;
    @Autowired
    private JavaMailSender javaMailSender;

    /***
     *  简单内容发送
     *  使用SimpleMailMessage来包装邮件信息，使用MailSender来执行发送事件
     * @param mailMessage
     */
    public void sendSimpleMessage(MailMessage mailMessage) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(mailMessage.getFrom());
        msg.setTo(mailMessage.getTo());
        msg.setSubject(mailMessage.getSubject());
        msg.setText(mailMessage.getText());
        mailSender.send(msg);
    }

    /***
     *  简单内容发送
     *  使用MimeMessagePreparator来包装MimeMailMessage邮件信息，使用JavaMailSender来执行发送事件
     * @param mailMessage
     */
    public void sendMimeMessagePropagator(MailMessage mailMessage) {
        MimeMessagePreparator propagator = mimeMessage -> {
            mimeMessage.setFrom(mailMessage.getFrom());
            mimeMessage.setRecipients(Message.RecipientType.TO, mailMessage.getTo());
            mimeMessage.setSubject(mailMessage.getSubject());
            mimeMessage.setText(mailMessage.getText());
        };
        javaMailSender.send(propagator);
    }

    /**
     * 简单内容发送
     * 使用MimeMessageHelper来包装MimeMailMessage邮件信息，使用JavaMailSender来执行发送事件
     *
     * @param mailMessage
     */
    public void sendBasicMimeMessage(MailMessage mailMessage) {
        MimeMailMessage mimeMailMessage = new MimeMailMessage(javaMailSender.createMimeMessage());
        try {
            MimeMessageHelper helper = mimeMailMessage.getMimeMessageHelper();
            helper.setTo(mailMessage.getTo());
            helper.setFrom(mailMessage.getFrom());
            helper.setSubject(mailMessage.getSubject());
            helper.setText(mailMessage.getText());
        } catch (MessagingException e) {
            logger.error("简单内容发送 build mail message error", e);
        }
        javaMailSender.send(mimeMailMessage.getMimeMessage());
    }

    /***
     *  发送附件内容
     *  附件功能只能使用MimeMailMessage进行邮件信息包装，可以添加单个邮件，多个邮件，这里以单个邮件为例，代码如下：
     * @param mailMessage
     */
    public void sendAttachmentsMimeMessage(MailMessage mailMessage) {
        MimeMailMessage mimeMailMessage = new MimeMailMessage(javaMailSender.createMimeMessage());
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage.getMimeMessage(), true);
            helper.setFrom(mailMessage.getFrom());
            helper.setTo(mailMessage.getTo());
            helper.setSubject(mailMessage.getSubject());
            helper.setText(mailMessage.getText());
            FileSystemResource file = new FileSystemResource(new File(mailMessage.getAttachment()));
            helper.addAttachment(file.getFilename(), file);
        } catch (MessagingException e) {
            logger.error("附件功能 build mail message error", e);
        }
        javaMailSender.send(mimeMailMessage.getMimeMessage());
    }

    /***
     *  嵌入资源内容
     *  嵌入资源功能亦只能使用MimeMailMessage进行邮件信息包装，这里以在邮件内容中嵌入一张图片为例
     *  ，“resource1234”是资源的id，确保在邮件嵌入资源中唯一，才能正确引入到html。代码如下：
     * @param mailMessage
     */
    public void sendInlineImageMimeMessage(MailMessage mailMessage) {
        MimeMailMessage mimeMailMessage = new MimeMailMessage(javaMailSender.createMimeMessage());
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage.getMimeMessage(), true);
            helper.setFrom(mailMessage.getFrom());
            helper.setTo(mailMessage.getTo());
            helper.setSubject(mailMessage.getSubject());
            String html =  "<html><body><img src='cid:resource1234'></body></html>";
            helper.setText(mailMessage.getText(),html);
            FileSystemResource resource = new FileSystemResource(new File(mailMessage.getAttachment()));
            helper.addInline("resource1234",resource);
        } catch (MessagingException e) {
        logger.error("嵌入资源内容 build mail message error",e);
        }
        javaMailSender.send(mimeMailMessage.getMimeMessage());
    }
}
