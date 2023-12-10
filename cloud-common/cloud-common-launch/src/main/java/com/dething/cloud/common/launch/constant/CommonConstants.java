package com.dething.cloud.common.launch.constant;

public class CommonConstants {
    public final static String RESOURCE_TYPE_MENU = "menu";
    public final static String RESOURCE_TYPE_BTN = "button";
    // 用户token异常
    public static final Integer EX_USER_INVALID_CODE = 40101;
    public static final Integer EX_USER_PASS_INVALID_CODE = 40001;
    // 客户端token异常
    public static final Integer EX_CLIENT_INVALID_CODE = 40301;
    public static final Integer EX_CLIENT_FORBIDDEN_CODE = 40331;
    public static final Integer EX_OTHER_CODE = 500;
    public static final String CONTEXT_KEY_USER_ID = "currentUserId";
    public static final String CONTEXT_KEY_USERNAME = "currentUserName";
    public static final String CONTEXT_KEY_USER_NAME = "currentUser";
    public static final String CONTEXT_KEY_USER_TOKEN = "currentUserToken";
    public static final String JWT_KEY_USER_ID = "userId";
    public static final String JWT_KEY_NAME = "name";
    
    public static final int DB_STATUS_NORMAL = 1;
    public static final int DB_NOT_DELETED = 0;
    public static final int DB_IS_DELETED = 1;
    public static final int DB_ADMIN_NON_LOCKED = 0;
    public static final int DB_ADMIN_LOCKED = 1;
    
    
    public static final Integer EX_TOKEN_ERROR_CODE = 40102;
    
    public static final String GATE_WAY_PREFIX = "/api";
    
    public static final String WHITELIST_SERVICE_ID = "whitelist";
    
}
