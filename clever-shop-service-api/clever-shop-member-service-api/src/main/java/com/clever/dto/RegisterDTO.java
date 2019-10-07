package com.clever.dto;

import com.clever.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by clever on 2019/9/1.
 */
@Data
@Builder
@ApiModel("用户注册入参")
public class RegisterDTO {
    @ApiModelProperty("用户信息")
    @NotNull
    private User user;

    @ApiModelProperty("注册码")
    @NotNull
    private String registCode;

}
