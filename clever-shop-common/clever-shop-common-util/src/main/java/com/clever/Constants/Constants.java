package com.clever.Constants;

/**
 * Created by clever on 2019/9/1.
 */
public interface Constants {
    //验证码
    String  WEIXIN_KEY = "clever-shop-weixin:code";

    //redis过期时间1分钟
    long WEIXINCODE_TIMEOUT = 60l;

    // 响应请求成功
    String HTTP_RES_CODE_200_VALUE = "success";

    // 系统错误
    String HTTP_RES_CODE_500_VALUE = "fial";

    // 响应请求成功code
    Integer HTTP_RES_CODE_200 = 200;

    // 系统错误
    Integer HTTP_RES_CODE_500 = 500;

    // 用户信息不存在
    Integer HTTP_RES_CODE_EXISTMOBILE_203 = 203;

    // 安卓的登陆类型1
    String MEMBER_LOGIN_TYPE_ANDROID = "1";

    // IOS的登陆类型2
    String MEMBER_LOGIN_TYPE_IOS = "2";

    // PC的登陆类型3
    String MEMBER_LOGIN_TYPE_PC = "3";

    // token
    String MEMBER_TOKEN_KEYPREFIX = "clever-shop-member:login";

}
