package com.cqcskj.lbc.utils;

import jakarta.annotation.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Random;
@Component
public class VerifyCodeUtil {
    @Resource
    private JavaMailSender javaMailSender;
    public String outCode(){
        Random random=new Random();
        return random.nextInt(10)+""+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(10);
    }
    public void sendEmail(String mailAddress,String bowserCode){
        try {
            SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
            simpleMailMessage.setFrom("3137749807@qq.com");
            simpleMailMessage.setTo(mailAddress);
            simpleMailMessage.setSubject("[left-behind children]");
            simpleMailMessage.setText("您的验证码:"+bowserCode+"(五分钟内有效)。"+"请勿将验证码泄露给其他人。");
            javaMailSender.send(simpleMailMessage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
