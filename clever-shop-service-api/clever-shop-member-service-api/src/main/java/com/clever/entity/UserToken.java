package com.clever.entity;

import com.clever.Constants.BaseEntity;
import lombok.Data;

/**
 * Created by clever on 2019/9/1.
 */
@Data
public class UserToken extends BaseEntity {
    /**
     * id
     */
    private Long id;
    /**
     * 用户token
     */
    private String token;
    /**
     * 登陆类型
     */
    private String loginType;

    /**
     * 设备信息
     */
    private String deviceInfor;
    /**
     * 用户userId
     */
    private Long userId;


}
