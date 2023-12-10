package com.dething.cloud.common.launch.msg.auth;

import com.dething.cloud.common.launch.constant.RestCodeConstants;
import com.dething.cloud.common.launch.msg.BaseResponse;

public class TokenForbiddenResponse  extends BaseResponse {
    public TokenForbiddenResponse(String message) {
        super(RestCodeConstants.TOKEN_FORBIDDEN_CODE, message);
    }
}
