package com.steel.carbon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWarningEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("carbon-emission@steel.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    public void sendReportReminder(String to, Integer year, Integer month) {
        String subject = year + "年" + month + "月碳排放报表已生成";
        String content = "您好，\n\n" + year + "年" + month + "月的碳排放统计报表已生成，请登录系统查看。\n\n钢铁企业碳排放数据管理系统";
        sendWarningEmail(to, subject, content);
    }
}
