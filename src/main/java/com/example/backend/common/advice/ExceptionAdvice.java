package com.example.backend.common.advice;


import com.example.backend.common.CommonResponse;
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
        return CommonResponse.failResult(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResponse internalServerErrorException(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        return CommonResponse.failResult(e.getMessage());
    }
}