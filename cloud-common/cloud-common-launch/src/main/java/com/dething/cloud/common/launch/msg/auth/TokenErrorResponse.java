package com.dething.cloud.common.launch.msg.auth;

import com.dething.cloud.common.launch.constant.RestCodeConstants;
import com.dething.cloud.common.launch.msg.BaseResponse;

public class TokenErrorResponse extends BaseResponse {
    public TokenErrorResponse(String message) {
        super(RestCodeConstants.TOKEN_ERROR_CODE, message);
    }
}
