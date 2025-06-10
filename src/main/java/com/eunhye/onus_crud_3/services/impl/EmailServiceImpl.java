package com.eunhye.onus_crud_3.services.impl;

import com.eunhye.onus_crud_3.services.EmailService;
import jakarta.mail.internet.MimeMessage;
//import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import java.util.concurrent.Executor;

@Service
//@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final Executor executor;
    private final SpringTemplateEngine templateEngine;

    public EmailServiceImpl(
            JavaMailSender mailSender,
            SpringTemplateEngine templateEngine,
            @Qualifier("emailExecutor") Executor executor
    ) {
           this.mailSender = mailSender;
           this.templateEngine = templateEngine;
           this.executor = executor;
    }

    @Override
    public void sendEmail(String to, String name) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject("Welcome to Onus IT");
//        message.setText("안녕하세요");
//
//        mailSender.send(message);
        executor.execute(() -> {
            try {
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                // 템플릿 html 처리
                Context context = new Context();
                context.setVariable("name", name);
                String content = templateEngine.process("welcome-email", context);

                helper.setTo(to);
                helper.setSubject("Welcome to Onus IT");
                helper.setText(content, true);

                mailSender.send(mimeMessage);

            } catch (Exception e) {
                System.err.println("이메일 전송 에러" + e.getMessage());
                e.printStackTrace();
            }
        });

    }

    @Override
    public void sendVerificationCodeEmail(String to, String code) {
        executor.execute(() -> {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

                Context context = new Context();
                context.setVariable("code", code);
                String htmlContent = templateEngine.process("verification-email", context);

                helper.setTo(to);
                helper.setSubject("이메일 인증 코드");
                helper.setText(htmlContent, true);

                mailSender.send(message);
            } catch (Exception e) {
                log.error("이메일 인증 코드 전송 실패: {}", e.getMessage(), e);
            }
        });
    }
}
