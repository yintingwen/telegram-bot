package com.dething.cloud.common.language.handler;


import com.dething.cloud.common.language.exception.ApiException;
import com.dething.cloud.common.language.exception.StatusCode;
//import com.dething.cloud.tools.R;
//import org.springframework.validation.BindException;
//import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler(value = ApiException.class)
//    @ResponseBody
//    public R<Object> apiExceptionHandler(ApiException e){
//        return R.fail(e.getCode(), e.getMessage());
//    }
//
//    @ExceptionHandler(Exception.class)
//    public R<Object> ExecptionHandler(Exception e) {
//        e.printStackTrace();
//        return R.fail(StatusCode.INTERNAL_SERVER_ERROR.getCode(), e.getMessage());
//    }
//
//    //处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
//    @ExceptionHandler(BindException.class)
//    @ResponseBody
//    public R<Object> BindExceptionHandler(BindException e) {
////        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
//        FieldError fe = e.getFieldError();
//        String message = MessageSourceHandler.getMessage(StatusCode.PARAM_ERROR.getCode(), fe.getField().concat(":").concat(fe.getDefaultMessage()));
//        return R.fail(StatusCode.PARAM_ERROR.getCode(), message);
//    }
//
//    //处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
//    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseBody
//    public R<Object> ConstraintViolationExceptionHandler(ConstraintViolationException e) {
//        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
//        message = MessageSourceHandler.getMessage(StatusCode.PARAM_ERROR.getCode(), message);
//        return R.fail(StatusCode.PARAM_ERROR.getCode(), message);
//    }
//
//    //处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseBody
//    public R<Object> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
////        String message = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
//        FieldError fe = e.getBindingResult().getFieldError();
//        String message = MessageSourceHandler.getMessage(StatusCode.PARAM_ERROR.getCode(), fe.getField().concat(":").concat(fe.getDefaultMessage()));
//        return R.fail(StatusCode.PARAM_ERROR.getCode(), message);
//    }

}
