package com.clever.service;

import com.clever.Constants.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
@Api(tags = "微信服务")
public interface WeiXinSerciceApi {

    @ApiOperation("测试接口")
    @GetMapping("/getApp")
    public BaseResponse get();
}
