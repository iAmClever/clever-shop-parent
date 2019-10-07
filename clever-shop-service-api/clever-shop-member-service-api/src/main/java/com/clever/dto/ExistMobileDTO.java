package com.clever.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by clever on 2019/9/1.
 */
@Data
@ApiModel("检查手机号码是否存在入参")
public class ExistMobileDTO {
    @ApiModelProperty("手机号码")
    @NotNull
    private String mobile;
}
