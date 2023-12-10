package com.dething.cloud.common.language.exception;

import com.dething.cloud.common.language.handler.MessageSourceHandler;

/**
 * 状态码，主要参考Http状态码，但并不完全对应
 * @author amigo
 *
 */
public enum StatusCode {

    /** 200请求成功 */
    OK(200),
    /** 400请求参数出错 */
    BAD_REQUEST(400),
    /** 401没有登录 */
    UNAUTHORIZED(401),
    /** 500服务器出错 */
    INTERNAL_SERVER_ERROR(500),
    /** 1000参数异常 */
    PARAM_ERROR(1000),
    ;


    private final Integer value;

    StatusCode(Integer value) {
        this.value = value;
    }

    public Integer getCode() {
        return this.value;
    }

    public String getMessage(Object... params) {
        return MessageSourceHandler.getMessage(this.value, params);
    }

}
