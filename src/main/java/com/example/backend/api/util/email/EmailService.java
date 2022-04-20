package com.example.backend.api.util.email;

import com.example.backend.api.auth.redis.RedisService;
import com.example.backend.common.DefaultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Message.RecipientType;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailService {

    @Value("${env.inspection.link}")
    private String link;
    private final long EMAIL_VERIFY_TIME = (1000L * 60) * 300; // 3분

    private final RedisService redisService;
    private final JavaMailSender javaMailSender;

    //@Async
    public String sendSignUpVerifyEmail(EmailVo emailVo){

        String verifyLink = link + "";
        StringBuffer message = new StringBuffer();


        message.append("<h2 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 인증</h2>");
        message.append("<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 인증 링크를 타고 들어가 인증을 완료해주세요.</p>");
        message.append("<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\">");
        message.append("<a href=\""+verifyLink+"\" style=\"text-decoration: none; color: #0063fc;\" rel=\"noreferrer noopener\" target=\"_blank\">"+verifyLink+"</a>");
        message.append("</div>");
        emailVo.setMessage(message.toString());
        emailVo.setTitle("옥타그노시스 - 회원 가입 인증 메일");
        String uUid = UUID.randomUUID().toString();
        redisService.setValuesExpire(uUid, DefaultCode.EMAIL_VERIFY_INCOMPLETE, EMAIL_VERIFY_TIME);
        sendMessage(emailVo);

        return uUid;

    }

    public boolean verifyEmailSuccess(String uUid){
        return false;
    }

    private MimeMessage createMessage(EmailVo email) {
        log.info("보내는 사람 : " + email.getTo());

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            mimeMessage.addRecipients(RecipientType.TO, email.getTo());
            mimeMessage.setSubject(email.getTitle());
            mimeMessage.setText(email.getMessage(), "utf-8", "html");
        }catch (Exception e){
            e.printStackTrace();
        }
        return mimeMessage;
    }

    private void sendMessage(EmailVo emailVo) {
        MimeMessage message = createMessage(emailVo);
        try{
            javaMailSender.send(message);
        }catch(MailException e){
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }
}
