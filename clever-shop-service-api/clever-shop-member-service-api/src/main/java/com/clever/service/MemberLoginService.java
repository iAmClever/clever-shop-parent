package com.clever.service;

import com.alibaba.fastjson.JSONObject;
import com.clever.Constants.BaseResponse;
import com.clever.dto.UserLoginDTO;
import com.clever.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by clever on 2019/9/1.
 */
@Api(tags = "用户登陆服务接口")
public interface MemberLoginService {

    @PostMapping("/login")
    @ApiOperation(value = "用户登陆接口")
    BaseResponse<JSONObject> login(@RequestBody @Validated UserLoginDTO userLoginInpDTO);

    /**
     * SSO认证系统登陆接口
     *
     * @param dto
     * @return
     */
    @ApiOperation(value = "sso登陆接口")
    @PostMapping("/ssoLogin")
    public BaseResponse<User> ssoLogin(@RequestBody @Validated UserLoginDTO dto);

}