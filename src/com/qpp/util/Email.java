package com.qpp.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;

public class Email {
    private JavaMailSender javaMailSender;
    private SimpleMailMessage simpleMailMessage;
    protected final org.slf4j.Logger loger = org.slf4j.LoggerFactory.getLogger(this.getClass().getName());
    public void sendMail(String subject, String content, String[] to) {
        this.sendMail(subject,content,to,null,null,null);
    }
    public void sendMail(String subject, String content, String[] to,String[] cc) {
        this.sendMail(subject,content,to,cc,null,null);
    }
    public void sendMail(String subject, String content, String[] to,String[] cc,String bcc,Object attachment) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);
            messageHelper.setFrom(simpleMailMessage.getFrom()); //设置发件人Email
            messageHelper.setSubject(subject); //设置邮件主题
            messageHelper.setText(content,true);   //设置邮件主题内容
            messageHelper.setTo(to);          //设定收件人Email
            if (cc!=null)
                messageHelper.setCc(cc);
            if (bcc!=null)
                messageHelper.setBcc(cc);
            if (attachment!=null&&attachment instanceof String&&!"".equals(attachment)) {
                FileSystemResource file = new FileSystemResource(new File(String.valueOf(attachment)));
                messageHelper.addAttachment(file.getFilename(), file); //添加附件
            }else if(attachment instanceof InputStream){

                messageHelper.addAttachment("附件",new InputStreamResource((InputStream)attachment));
            }
            javaMailSender.send(mimeMessage);    //发送附件邮件
            loger.info("To:"+ Arrays.asList(to)+",subject='"+subject+"' is successful.");
        }catch (Exception e) {
            e.printStackTrace();
            loger.error("To:"+Arrays.asList(to)+",subject='"+subject+"' is failed , Message is "+e.getMessage());
        }
    }
    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    public static void main(String[] args) {
        String path=System.getProperty("user.dir");
        ApplicationContext ctx = new FileSystemXmlApplicationContext(path+"\\WebContent\\WEB-INF\\spring\\applicationContext-baseSet.xml");
        Email mail=(Email)ctx.getBean("Mail");
        mail.sendMail("tt","haha",new String[]{"79277490@qq.com"});
    }

}