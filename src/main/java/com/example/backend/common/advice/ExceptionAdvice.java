package com.example.backend.common.advice;


import com.example.backend.common.CommonResponse;
import com.example.backend.common.exception.UserAuthorityException;
import com.example.backend.util.enumerator.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResponse badRequestException(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        return CommonResponse.failResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResponse internalServerErrorException(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        return CommonResponse.failResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected CommonResponse forbiddenException(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        return CommonResponse.failResult(HttpStatus.FORBIDDEN.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected CommonResponse unauthorizedException(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        return CommonResponse.failResult(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    @ExceptionHandler(value = UserAuthorityException.class)
    protected ResponseEntity userAuthorityException(UserAuthorityException e){
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.failResult(ResponseCode.USER_UNAUTHORIZED.getCode(), e.getMessage()));
    }
}