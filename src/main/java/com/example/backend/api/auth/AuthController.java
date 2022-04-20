package com.example.backend.api.auth;

import com.example.backend.api.auth.model.AuthKakao;
import com.example.backend.api.util.coolsms.Coolsms;
import com.example.backend.api.util.coolsms.CoolsmsService;
import com.example.backend.api.member.model.Member;
import com.example.backend.api.member.MemberService;
import com.example.backend.api.util.email.EmailService;
import com.example.backend.api.util.email.EmailVo;
import com.example.backend.common.exception.ApiException;
import com.example.backend.common.CommonResponse;
import com.example.backend.util.enumerator.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    MemberService memberService;
    @Autowired
    AuthService authService;
    @Autowired
    Oauth2KakaoService oauth2KakaoService;
    @Autowired
    Oauth2NaverService oauth2NaverService;
    @Autowired
    EmailService emailService;
    @Autowired
    CoolsmsService coolsmsService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Member user, HttpServletResponse response){

        String userName = user.getUsername();
        Member member = (Member) memberService.loadUserByUsername(userName);
        boolean checkPassword = memberService.checkPassword(user, member);

        List<String> roles = Arrays.asList(member.getRole());
        if(!checkPassword || !roles.contains(user.getRole())){
            throw new IllegalArgumentException("아이디 혹은 비밀번호가 잘못되었습니다.");
        }
        authService.loginSuccess(member, response);
        return ResponseEntity.ok(CommonResponse.successResult(memberService.memberInfo(member)));
    }

    @PostMapping("/login/naver")
    public ResponseEntity naverLogin(@RequestBody Map<String, String> params, HttpServletResponse response){
        String code = params.get("code");
        Map<String, Object> tokenInfo = oauth2NaverService.callTokenApi(code);
        String naverAccessToken = (String) tokenInfo.get("access_token");
        Map<String, Object> userInfo = oauth2NaverService.callUserByAccessToken(naverAccessToken);
        Map<String, String> userDetail = (Map<String, String>) userInfo.get("response");
        String userId = "naver_" + userDetail.get("id");
        String username = userDetail.get("name");

        Member member = (Member)memberService.loadUserByUsername(userId);

        if(member == null){
            member = new Member(userId, username);
            return ResponseEntity.ok(CommonResponse.failResult(ResponseCode.KAKAO_USER_NOT_SIGNED.getCode(), ResponseCode.KAKAO_USER_NOT_SIGNED.getMsg(), memberService.memberInfo(member)));
        }else{
            authService.loginSuccess(member, response);
        }
        return ResponseEntity.ok(CommonResponse.successResult(memberService.memberInfo(member)));
    }

    @PostMapping("/login/kakao")
    public ResponseEntity kakaoLogin(@RequestBody Map<String, String> params, HttpServletResponse response){
        String code = params.get("code");
        AuthKakao authKakao = oauth2KakaoService.callTokenApi(code);
        String kakaoAccessToken = authKakao.getAccess_token();

        Map<String, Object> userInfo = oauth2KakaoService.callUserByAccessToken(kakaoAccessToken);
        String userId = "kakao_" + userInfo.get("id");
        LinkedHashMap<String, String> userDetail = (LinkedHashMap<String, String>) userInfo.get("properties");
        String username = userDetail.get("nickname");

        Member member = (Member)memberService.loadUserByUsername(userId);

        if(member == null){
            member = new Member(userId, username);
            return ResponseEntity.ok(CommonResponse.failResult(ResponseCode.KAKAO_USER_NOT_SIGNED.getCode(), ResponseCode.KAKAO_USER_NOT_SIGNED.getMsg(), memberService.memberInfo(member)));
        }else{
            authService.loginSuccess(member, response);
        }
        return ResponseEntity.ok(CommonResponse.successResult(memberService.memberInfo(member)));

    }

    @PostMapping("/login/google")
    public ResponseEntity googleLogin(@RequestBody Map<String, String> params, HttpServletResponse response){
        String code = params.get("code");
        /*
        AuthKakao authKakao = oauth2KakaoService.callTokenApi(code);
        String kakaoAccessToken = authKakao.getAccess_token();

        Map<String, Object> userInfo = oauth2KakaoService.callUserByAccessToken(kakaoAccessToken);
        String userId = "kakao_" + userInfo.get("id");
        LinkedHashMap<String, String> userDetail = (LinkedHashMap<String, String>) userInfo.get("properties");
        String username = userDetail.get("nickname");

        Member member = (Member)memberService.loadUserByUsername(userId);

        if(member == null){
            member = new Member(userId, username);
            return ResponseEntity.ok(CommonResponse.failResult(ResponseCode.KAKAO_USER_NOT_SIGNED.getCode(), ResponseCode.KAKAO_USER_NOT_SIGNED.getMsg(), member));
        }else{
            authService.loginSuccess(member, response);
        }
        return ResponseEntity.ok(CommonResponse.successResult(member));*/

        //https://maivve.tistory.com/336

        return ResponseEntity.ok(CommonResponse.successResult(code));

    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response){

        authService.logoutSuccess(request, response);
        return ResponseEntity.ok(CommonResponse.successResult());
    }

    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody Member member, HttpServletResponse response){

        memberService.insertMember(member);
        authService.loginSuccess(member, response);
        return ResponseEntity.ok(CommonResponse.successResult(member));
    }

    @GetMapping("/info")
    public ResponseEntity getUserInfo(HttpServletRequest request, Principal principal){
        return ResponseEntity.ok().body(principal);
    }

    @PostMapping("/validate-token")
    public ResponseEntity validateToken(HttpServletRequest request, Principal principal){

        Member member = (Member) memberService.loadUserByUsername(principal.getName());
        return ResponseEntity.ok().body(CommonResponse.successResult(memberService.memberInfo(member)));
    }

    @PostMapping("/check-id")
    public ResponseEntity checkId(@RequestBody Member member){

        String memberId = member.getId();
        Member duplicateMember = memberService.duplicateMember(memberId);
        if(duplicateMember != null) throw new ApiException("이미 사용중인 아이디 입니다.");
        return ResponseEntity.ok(CommonResponse.successResult());

    }

    @PostMapping("/send-sms")
    public ResponseEntity sendSms(@RequestBody Coolsms coolSms, HttpServletRequest request){
        //int sessionValidateSecond = 60 * 2;
        //int sessionValidateSecond = 60 * 60;
        HttpSession session = request.getSession(true);
        //session.setMaxInactiveInterval(sessionValidateSecond);
        //session.removeAttribute("authNo");

        int authNo = (int)(Math.random() * (99999 - 10000 + 1)) + 10000;
        session.setAttribute("authNo", authNo);
        coolSms.setText("[humannx] 인증번호는 " + authNo + " 입니다");
       // coolsmsService.sendSms(coolSms);
        return ResponseEntity.ok(CommonResponse.successResult());
    }

    @PostMapping("/send-email")
    public ResponseEntity sendEmail(@RequestBody EmailVo emailVo){
        String uUid = emailService.sendSignUpVerifyEmail(emailVo);
        Map<String, Object> map = new HashMap<>();
        map.put("uUid", uUid);
        return ResponseEntity.ok(CommonResponse.successResult(map));
    }

    @PostMapping("/verify-email")
    public ResponseEntity verifyEmail(@RequestParam String uUid){
        return ResponseEntity.ok(uUid);
    }

    @PostMapping("/check-sms")
    public ResponseEntity checkPhone(@RequestBody Map<String, String> param, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        if(session.getAttribute("authNo") == null)
            throw new ApiException("세션이 유효하지 않습니다");

        String number = param.get("number");
        String authNo = session.getAttribute("authNo").toString();

        if(!number.equals(authNo))
            throw new ApiException("인증번호가 다릅니다");

        //session.invalidate();
        return ResponseEntity.ok().body(CommonResponse.successResult());

    }

    @GetMapping("/find-id/{searchType}")
    public ResponseEntity getUserId(@RequestParam Map<String, String> param, @PathVariable String searchType){
        /*Member member = new Member();
        if(searchType.equals(SearchTypes.INFO.getSearchType())){
            member = memberService.findIdByInfo(param.get("name"), param.get("email"));

        }else if(searchType.equals(SearchTypes.PHONE.getSearchType())){

        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponse.failResult("invalid search type"));
        }

        if(member == null) throw new ApiException("유효하지 않은 정보입니다.");
*/
        return ResponseEntity.ok().body(CommonResponse.successResult());
    }

}
