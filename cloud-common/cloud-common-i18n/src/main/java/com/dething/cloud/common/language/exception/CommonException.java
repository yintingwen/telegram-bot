package com.dething.cloud.common.language.exception;

/**
 * 所有的自定义异常全部继承自ApiException
 * @author heian
 * @date 2021/10/25 9:23
 * @description
 */
public class CommonException extends ApiException{
    public CommonException(Integer code, String message) {
        super(code, message);
    }
}
