package com.clever.Constants;

import lombok.Data;

import java.util.Date;

/**
 * Created by clever on 2019/9/1.
 */
@Data
public class BaseEntity {
    /**
     * 注册时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * id
     */
    private Long id;

    /**
     * 是否可用 0可用 1不可用
     */
    private Long isAvailability;
}
