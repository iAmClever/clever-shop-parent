package com.clever.service;

import com.clever.Constants.BaseResponse;
import com.clever.dto.ExistMobileDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by clever on 2019/8/18.
 */
@Api(tags = "会员服务")
public interface MemberServiceApi {

    @ApiOperation("调用其他服务接口")
    @GetMapping("/get")
    public BaseResponse memberInvokeOtherService();


    @ApiOperation(value = "查询手机号码是否已经存在")
    @PostMapping("/existMobile")
    public BaseResponse existMobile(@RequestBody @Validated ExistMobileDTO dto);
}
