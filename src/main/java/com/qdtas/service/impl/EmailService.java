package com.qdtas.service.impl;
import com.qdtas.entity.EmailVerification;
import com.qdtas.repository.EmailServiceRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.qdtas.utility.AppConstants;

import java.util.UUID;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender ms ;
    @Autowired
    private EmailServiceRepository emr;

    public void sendVerificationEmail(String toEmail) {
        String token = String.valueOf(UUID.randomUUID());
        String url=AppConstants.BASE_URL+"/user/verify/"+token;

        EmailVerification emv=new EmailVerification();
        emv.setToken(token);
        emv.setEmail(toEmail);
        emr.save(emv);

        String body="click on link to verify your account with QDTAS: "+url;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("lature7721@gmail.com");
        message.setTo(toEmail);
        message.setSubject("About Verification");
        message.setText(body);
        ms.send(message);
    }

    public void sendPasswordResetEmail(String email,String temp) {
        String url="https://qdtas-hrm-frontend.vercel.app/changeTempPassword";

        String body="<html><body>" +
                "<h3>Use this Temporary Password to change password : </h3><br>"+
                "<h6 style='color:red'>"+temp+"</h6><br>"+
                "<h6><span style='color:red'>NOTE:</span>You can not login using Temporary password you can change your password here</h6>" +
                "<a href='"+url+"'>Reset Password</a><br>"+
        "</body></html>";
        MimeMessage message = ms.createMimeMessage();
        MimeMessageHelper helper;
        try{
            helper = new MimeMessageHelper(message, true);
            helper.setFrom("services@qdtas.com");
            helper.setTo(email);
            helper.setSubject("About Password Reset");
            helper.setText(body,true);
            ms.send(message);
        }catch (MessagingException e){
            e.printStackTrace();
        }


    }
}
