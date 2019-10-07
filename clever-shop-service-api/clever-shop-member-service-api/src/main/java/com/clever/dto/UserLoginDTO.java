package com.clever.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by clever on 2019/9/1.
 */
@Data
@ApiModel(value = "用户登陆入参")
public class UserLoginDTO {
    /**
     * 手机号码
     */
    @NotNull
    @ApiModelProperty(value = "手机号码")
    private String mobile;
    /**
     * 密码
     */
    @NotNull
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 登陆类型 PC、Android 、IOS
     */
    @NotNull
    @ApiModelProperty(value = "登陆类型(Android 1 、IOS 2、 PC 3)")
    private String loginType;
    /**
     * 设备信息
     */
    @NotNull
    @ApiModelProperty(value = "设备信息")
    private String deviceInfor;
}
