package com.clever.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by clever on 2019/9/1.
 */
@Data
@Builder
public class VerificaCodeReq {
    @NotNull
    @ApiModelProperty("手机号码")
    private String phone;

    @NotNull
    @ApiModelProperty("注册码")
    private String weixinCode;
}
