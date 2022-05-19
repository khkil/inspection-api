package com.example.backend.api.util.email;

import com.example.backend.api.auth.redis.RedisService;
import com.example.backend.api.member.MemberService;
import com.example.backend.api.member.model.Member;
import com.example.backend.common.DefaultCode;
import com.example.backend.config.secutiry.JwtTokenProvider;
import com.example.backend.util.URLUtil;
import edu.emory.mathcs.backport.java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Message.RecipientType;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailService {

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    MemberService memberService;

    @Value("${env.inspection.link}")
    private String link;
    private final long EMAIL_VERIFY_TIME = (1000L * 60) * 300; // 5분

    private final String HEADER_LOGO_IMAGE_URL = "https://storage.googleapis.com/careercompany/성향검사/문항/logo-octagnosis.png";

    private final RedisService redisService;
    private final JavaMailSender javaMailSender;

    @Async
    public void sendSignUpVerifyEmail(EmailVo emailVo){

        String toEmail = emailVo.getToEmail();
        String authKey = UUID.randomUUID().toString();
        Map<String, Object> param = new HashMap<>();
        param.put("authKey", authKey);
        param.put("toEmail", toEmail);
        String verifyLink = link + "/auth/verify-email?" + URLUtil.paramToQueryString(param);
        StringBuffer message = new StringBuffer();

        message.append("<hr style=\"display: block; height: 1px; background-color: #ebebeb; margin: 14px 0; padding: 0; border: 0;\">");
        setHeader(message);
        message.append("<div style=\"margin-top: 7px; margin-bottom: 22px; color: #222222;\">");
        message.append(" <p style=\"margin-block-start: 0; margin-block-end: 0; margin-inline-start: 0; margin-inline-end: 0; line-height: 1.47; letter-spacing: -0.22px; font-size: 15px; margin: 8px 0 0;\">옥타그노시스 회원가입을 위해 이메일 인증이 필요합니다.</p>");
        message.append("<p style=\"margin-block-start: 0; margin-block-end: 0; margin-inline-start: 0; margin-inline-end: 0; line-height: 1.47; letter-spacing: -0.22px; font-size: 15px; margin: 8px 0 0;\">이메일 인증 완료를 위해 아래 버튼을 눌러주세요. </p>");
        message.append("<a href=\"" + verifyLink + "\" style=\"text-decoration: none; color: white; display: inline-block; font-size: 15px; font-weight: 500; font-stretch: normal; font-style: normal; line-: normal; letter-spacing: normal; border-radius: 2px; background-color: #141517; margin: 24px 0 19px; padding: 11px 6px;\" rel=\"noreferrer noopener\" target=\"_blank\">이메일 인증하기</a>");
        message.append("<p style=\"margin-block-start: 0; margin-block-end: 0; margin-inline-start: 0; margin-inline-end: 0; line-height: 1.47; letter-spacing: -0.22px; font-size: 15px; margin: 20px 0;\">감사합니다.</p>");
        message.append("<hr style=\"display: block; height: 1px; background-color: #ebebeb; margin: 14px 0; padding: 0; border: 0;\">");
        message.append("</div>");
        setFooter(message);

        emailVo.setMessage(message.toString());
        emailVo.setTitle("옥타그노시스 - 회원 가입 인증 메일");

        redisService.setValuesExpire(toEmail, authKey, EMAIL_VERIFY_TIME);
        sendMessage(emailVo);
    }

    public void sendFindIdEmail(EmailVo emailVo){

        String toEmail = emailVo.getToEmail();
        Map<String, Object> param = new HashMap<>();
        Member member = memberService.loadUserByUserEmail(toEmail);
        String authKey = jwtTokenProvider.createToken(toEmail, Arrays.asList(new String[]{ member.getRole() }), 5);
        emailVo.setAuthKey(authKey);
        param.put("authKey", authKey);
        param.put("toEmail", toEmail);
        String verifyLink = link + "/auth/verify-email?" + URLUtil.paramToQueryString(param);
        StringBuffer message = new StringBuffer();

        message.append("<hr style=\"display: block; height: 1px; background-color: #ebebeb; margin: 14px 0; padding: 0; border: 0;\">");
        setHeader(message);
        message.append("<div style=\"margin-top: 7px; margin-bottom: 22px; color: #222222;\">");
        message.append(" <p style=\"margin-block-start: 0; margin-block-end: 0; margin-inline-start: 0; margin-inline-end: 0; line-height: 1.47; letter-spacing: -0.22px; font-size: 15px; margin: 8px 0 0;\">옥타그노시스 아이디 찾기 메일 입니다.</p>");
        message.append("<p style=\"margin-block-start: 0; margin-block-end: 0; margin-inline-start: 0; margin-inline-end: 0; line-height: 1.47; letter-spacing: -0.22px; font-size: 15px; margin: 8px 0 0;\">이메일 인증 완료를 위해 아래 버튼을 눌러주세요. </p>");
        message.append("<a href=\"" + verifyLink + "\" style=\"text-decoration: none; color: white; display: inline-block; font-size: 15px; font-weight: 500; font-stretch: normal; font-style: normal; line-: normal; letter-spacing: normal; border-radius: 2px; background-color: #141517; margin: 24px 0 19px; padding: 11px 6px;\" rel=\"noreferrer noopener\" target=\"_blank\">이메일 인증하기</a>");
        message.append("<p style=\"margin-block-start: 0; margin-block-end: 0; margin-inline-start: 0; margin-inline-end: 0; line-height: 1.47; letter-spacing: -0.22px; font-size: 15px; margin: 20px 0;\">감사합니다.</p>");
        message.append("<hr style=\"display: block; height: 1px; background-color: #ebebeb; margin: 14px 0; padding: 0; border: 0;\">");
        message.append("</div>");
        setFooter(message);

        emailVo.setMessage(message.toString());
        emailVo.setTitle("옥타그노시스 - 회원 가입 인증 메일");

        redisService.setValuesExpire(toEmail, authKey, EMAIL_VERIFY_TIME);
        sendMessage(emailVo);
    }

    public void verifyEmail(String toEmail, String authKey){
        Object value = redisService.getValues(toEmail);
        if(value == null || !value.equals(authKey)){
            throw new IllegalArgumentException("이메일 인증 실패");
        }
        redisService.setValues(toEmail, DefaultCode.EMAIL_VERIFY_COMPLETE);
    }

    public void checkEmailVerified(String toEmail){
        Object value = redisService.getValues(toEmail);
        boolean success = value.equals(DefaultCode.EMAIL_VERIFY_COMPLETE);
        if(!success) throw new IllegalArgumentException("이메일 인증 실패");

    }

    private MimeMessage createMessage(EmailVo email) {
        log.info("받는 사람 : " + email.getToEmail());

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            mimeMessage.addRecipients(RecipientType.TO, email.getToEmail());
            mimeMessage.setSubject(email.getTitle());
            mimeMessage.setText(email.getMessage(), "utf-8", "html");
        }catch (Exception e){
            log.error("메시지 생성 실패", e);
        }
        return mimeMessage;
    }

    private void sendMessage(EmailVo emailVo) {
        MimeMessage message = createMessage(emailVo);
        try{
            javaMailSender.send(message);
        }catch(MailException e){
            log.error("메시지 전송 실패", e);
            throw new IllegalArgumentException();
        }
    }

    private void setHeader(StringBuffer message){
        message.append(" <img src=\"" + HEADER_LOGO_IMAGE_URL + "\" loading=\"lazy\">");
        message.append("<h1 style=\"margin-top: 23px; margin-bottom: 9px; color: #222222; font-size: 19px; line-height: 25px; letter-spacing: -0.27px;\">안녕하세요.</h1>");

    }
    private void setFooter(StringBuffer message){
        message.append("<div>");
        message.append("<p style=\"margin-block: 0; margin-inline: 0; font-weight: normal; font-size: 13px; font-stretch: normal; font-style: normal; line-height: 1.43; letter-spacing: normal; color: #8a8a8a; margin: 5px 0 0;\">본 메일은 발신 전용입니다.</p>");
        message.append("<p style=\"margin-block: 0; margin-inline: 0; font-weight: normal; font-size: 14px; font-stretch: normal; font-style: normal; line-height: 1.43; letter-spacing: normal; color: #8a8a8a; margin: 5px 0 0;\">© 2022. <a href=\"https://aptitude-x.com\" style=\"text-decoration: none; font-weight: 600; color: #8a8a8a;\" rel=\"noreferrer noopener\" target=\"_blank\">한국진로적성센터</a> All rights reserved.</p>");
        message.append(" </div>");


    }
}
