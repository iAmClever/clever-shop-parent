package com.clever.service;

import com.clever.Constants.BaseResponse;
import com.clever.dto.RegisterDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "会员注册接口")
public interface MemberRegisterService {
	/**
	 * 注册
	 * @param dto
	 * @return
	 */
	@PostMapping("/register")
	@ApiOperation(value = "用户注册接口")
	BaseResponse register(@RequestBody @Validated RegisterDTO dto);

}
