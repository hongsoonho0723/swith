package com.sportsmania.swith.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {


    private final JavaMailSender javaMailSender;


    private final CacheManager cacheManager;

    public void sendVerificationEmail(String email) throws MessagingException {
        // Generate verification code
        String verificationCode = generateVerificationCode();

        // Save verification code in cache
        Cache cache = cacheManager.getCache("verificationCodes");
        cache.put(email, verificationCode);

        // Send verification email
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("rkddkql23@naver.com");
        helper.setTo(email);
        helper.setSubject("S-WITH 인증번호 입니다.");
        helper.setText("인증번호는 : " + verificationCode);
        javaMailSender.send(message);
    }

    public boolean verifyVerificationCode(String email, String verificationCode) {
        // Get verification code from cache
        Cache cache = cacheManager.getCache("verificationCodes");
        String cachedCode = cache.get(email, String.class);

        // Compare verification code
        return cachedCode != null && cachedCode.equals(verificationCode);
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(10000);
        return String.format("%04d", code);
    }
}