package com.clever.service;

import com.clever.Constants.BaseResponse;
import com.clever.dto.VerificaCodeReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by clever on 2019/9/1.
 */
@Api(tags = "微信注册码验证码接口")
public interface VerificaCodeService {

    @ApiOperation(value = "根据手机号码验证注册码是否正确")
    @PostMapping("/verificaWeixinCode")
    public BaseResponse verificaWeixinCode(@RequestBody @Validated VerificaCodeReq req);
}