package com.example.backend.api.auth;

import com.example.backend.api.auth.model.AuthKakao;
import com.example.backend.api.auth.model.ResetPasswordVo;
import com.example.backend.api.group.Group;
import com.example.backend.api.group.code.GroupCodeService;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @Autowired
    GroupCodeService groupCodeService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Member user, HttpServletResponse response){

        String userName = user.getUsername();
        Member member = (Member) memberService.loadUserByUsername(userName);
        boolean checkPassword = memberService.checkPassword(user, member);

        if(!checkPassword){
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
        String userEmail = userDetail.get("email");

        if(!memberService.existsMemberById(userId)){
            Member member = new Member(userId, username, userEmail);
            return ResponseEntity.ok(CommonResponse.failResult(ResponseCode.NAVER_USER_NOT_SIGNED.getCode(), ResponseCode.NAVER_USER_NOT_SIGNED.getMsg(), memberService.memberInfo(member)));
        }
        Member member = (Member)memberService.loadUserByUsername(userId);
        authService.loginSuccess(member, response);
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

        if(!memberService.existsMemberById(userId)){
            Member member = new Member(userId, username);
            return ResponseEntity.ok(CommonResponse.failResult(ResponseCode.KAKAO_USER_NOT_SIGNED.getCode(), ResponseCode.KAKAO_USER_NOT_SIGNED.getMsg(), memberService.memberInfo(member)));
        }
        Member member = (Member)memberService.loadUserByUsername(userId);
        authService.loginSuccess(member, response);
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

        memberService.signUp(member);
        authService.loginSuccess(member, response);
        return ResponseEntity.ok(CommonResponse.successResult(member));
    }

    @GetMapping("/info")
    public ResponseEntity getUserInfo(Principal principal){
        return ResponseEntity.ok().body(principal);
    }

    @PostMapping("/validate-token")
    public ResponseEntity validateToken(@AuthenticationPrincipal Member member){

        if(member == null) throw new ApiException("토큰 검증 실패");
        return ResponseEntity.ok().body(CommonResponse.successResult(memberService.memberInfo(member)));
    }

    @PostMapping("/check-id")
    public ResponseEntity checkId(@RequestBody Member member){

        String memberId = member.getId();
        memberService.checkDuplicateMember(memberId);
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

    @PostMapping("/send-sign-up-email")
    public ResponseEntity sendSignUpVerifyEmail(@RequestBody EmailVo emailVo){
        Member member = memberService.loadUserByUserEmail(emailVo.getToEmail());
        if(member != null) {
            throw new ApiException("이미 가입된 이메일 입니다.");
        }
        emailService.sendSignUpVerifyEmail(emailVo);
        return ResponseEntity.ok(CommonResponse.successResult());
    }

    @PostMapping("/send-find-id-email")
    public ResponseEntity sendFindIdEmail(@RequestBody EmailVo emailVo){
        emailService.sendFindIdEmail(emailVo);
        return ResponseEntity.ok(CommonResponse.successResult());
    }

    @PostMapping("/verify-email")
    public ResponseEntity verifyEmail(@RequestBody EmailVo emailVo){
        emailService.verifyEmail(emailVo.getToEmail(), emailVo.getAuthKey());
        return ResponseEntity.ok(CommonResponse.successResult());
    }

    @PostMapping("/check-email")
    public ResponseEntity checkEmailVerified(@RequestBody EmailVo emailVo){
        emailService.checkEmailVerified(emailVo.getToEmail());
        return ResponseEntity.ok(CommonResponse.successResult());
    }

    @PostMapping("/reset-password")
    public ResponseEntity resetPassword(@AuthenticationPrincipal Member member, @RequestBody ResetPasswordVo resetPasswordVo){
        int memberIdx = member.getIdx();
        authService.ResetPassword(memberIdx, resetPasswordVo);
        return ResponseEntity.ok(CommonResponse.successResult());
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

    @PostMapping("/check-code")
    public ResponseEntity checkCode(@RequestBody Group group){
        String groupCode = group.getGroupCode();
        Group groupFromCode = groupCodeService.checkCode(groupCode);

        return ResponseEntity.ok(CommonResponse.successResult(groupFromCode));
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
