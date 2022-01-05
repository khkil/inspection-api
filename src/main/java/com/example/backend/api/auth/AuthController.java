package com.example.backend.api.auth;

import com.example.backend.api.util.coolsms.Coolsms;
import com.example.backend.api.util.coolsms.CoolsmsService;
import com.example.backend.api.member.Member;
import com.example.backend.api.member.MemberService;
import com.example.backend.common.exception.ApiException;
import com.example.backend.common.exception.UserAuthorityException;
import com.example.backend.config.secutiry.JwtTokenProvider;
import com.example.backend.common.CommonResponse;
import com.example.backend.util.enumerator.SearchTypes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.struts.chain.commands.UnauthorizedActionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    MemberService memberService;
    @Autowired
    CoolsmsService coolsmsService;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Member user, HttpServletResponse response){

        String userName = user.getUsername();
        Member member = (Member) memberService.loadUserByUsername(userName);
        boolean checkPassword = memberService.checkPassword(user, member);

        List<String> roles = Arrays.asList(member.getRole());
        if(!checkPassword || !roles.contains(user.getRole())){
            throw new IllegalArgumentException("아이디 혹은 비밀번호가 잘못되었습니다.");
        }

        String accessToken =  jwtTokenProvider.generateAccessToken(user.getId(), roles);
        String refreshToken =  jwtTokenProvider.generateRefreshToken(user.getId(), roles);

        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);
        jwtTokenProvider.saveRefreshToken2Redis(member.getId(), refreshToken);

        return ResponseEntity.ok(CommonResponse.successResult(member));
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request){

        jwtTokenProvider.removeRefreshToken2Redis(request);
        return ResponseEntity.ok(CommonResponse.successResult());
    }

    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody Member member){

        memberService.insertMember(member);
        List<String> roles = Arrays.asList(member.getRole());
        String accessToken =  jwtTokenProvider.generateAccessToken(member.getId(), roles);
        String refreshToken =  jwtTokenProvider.generateRefreshToken(member.getId(), roles);
        jwtTokenProvider.saveRefreshToken2Redis(member.getId(), refreshToken);
        return ResponseEntity.ok(CommonResponse.successResult(member));
    }

    @GetMapping("/info")
    public ResponseEntity getUserInfo(HttpServletRequest request){

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        return ResponseEntity.ok().body(authentication.getPrincipal());
    }

    @PostMapping("/validate-token")
    public ResponseEntity validateToken(HttpServletRequest request) throws UnauthorizedActionException {
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        Member member = (Member)authentication.getPrincipal();
        return ResponseEntity.ok().body(CommonResponse.successResult(member));
    }

    @PostMapping("/check-id")
    public ResponseEntity checkId(@RequestBody Member member){

        Member duplicateMember = memberService.duplicateMember(member);
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
        System.out.println(session.getAttribute("authNo").toString());
       // coolsmsService.sendSms(coolSms);
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

    @PostMapping("/reissue-token")
    public ResponseEntity reissueAccessToken(HttpServletRequest request, HttpServletResponse response){

        String refreshToken = request.getHeader("refresh-token");
        String accessToken = jwtTokenProvider.reissueAccessToken(refreshToken);
        jwtTokenProvider.setHeaderAccessToken(response, accessToken);

        return ResponseEntity.ok(CommonResponse.successResult());
    }
}
