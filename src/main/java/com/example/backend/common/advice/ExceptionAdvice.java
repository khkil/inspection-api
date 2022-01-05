package com.example.backend.common.advice;


import com.example.backend.common.CommonResponse;
import com.example.backend.common.exception.UserAuthorityException;
import com.example.backend.util.enumerator.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        return CommonResponse.failResult(ResponseCode.BAD_REQUEST.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResponse internalServerErrorException(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        return CommonResponse.failResult(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected CommonResponse forbiddenException(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        return CommonResponse.failResult(ResponseCode.FORBIDDEN.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = UserAuthorityException.class)
    protected CommonResponse userAuthorityException(Exception e){
        e.printStackTrace();
        return CommonResponse.failResult(ResponseCode.USER_UNAUTHORIZED.getCode(), e.getMessage());
    }


}