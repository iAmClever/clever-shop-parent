package com.clever.service;

import com.alibaba.fastjson.JSONObject;
import com.clever.Constants.BaseResponse;
import com.clever.Constants.Constants;
import com.clever.dto.UserLoginDTO;
import com.clever.entity.User;
import com.clever.entity.UserToken;
import com.clever.mapper.UserMapper;
import com.clever.mapper.UserTokenMapper;
import com.clever.esResposiory.BaseResponseUtil;
import com.clever.esResposiory.MD5Util;
import com.clever.esResposiory.RedisDataSoureceTransaction;
import com.clever.esResposiory.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Created by clever on 2019/9/1.
 */
@RestController
@Slf4j
public class MemberLoginServiceImpl implements MemberLoginService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserTokenMapper userTokenMapper;
    @Autowired
    private RedisUtil redisUtil;
    /**
     * 手动事务工具类
     */
    @Autowired
    private RedisDataSoureceTransaction manualTransaction;

    @Override
    public BaseResponse<JSONObject> login(@RequestBody @Validated UserLoginDTO dto) {
        String loginType = dto.getLoginType();
        if (!(loginType.equals(Constants.MEMBER_LOGIN_TYPE_ANDROID) || loginType.equals(Constants.MEMBER_LOGIN_TYPE_IOS)
                || loginType.equals(Constants.MEMBER_LOGIN_TYPE_PC))) {
            return BaseResponseUtil.error("登陆类型出现错误!");
        }

        // 设备信息
        String deviceInfor = dto.getDeviceInfor();
        String newPassWord = MD5Util.MD5(dto.getPassword());
        // 2.用户名称与密码登陆
        User user = userMapper.login(dto.getMobile(), newPassWord);
        if (user == null) {
            return BaseResponseUtil.error("用户名称或者密码错误!");
        }
        TransactionStatus transactionStatus = null;
        try {

            // 1.获取用户UserId
            Long userId = user.getUserid();
            // 2.生成用户令牌Key
            String keyPrefix = Constants.MEMBER_TOKEN_KEYPREFIX + loginType;
            // 5.根据userId+loginType 查询当前登陆类型账号之前是否有登陆过，如果登陆过 清除之前redistoken
            UserToken userToken = userTokenMapper.selectByUserIdAndLoginType(userId, loginType);
            transactionStatus = manualTransaction.begin();
            // // ####开启手动事务
            if (userToken != null) {
                // 如果登陆过 清除之前redistoken
                String oriToken = userToken.getToken();
                // 移除Token
                redisUtil.delKey(oriToken);
                int updateTokenAvailability = userTokenMapper.updateTokenAvailability(oriToken);
                if (updateTokenAvailability < 0) {
                    manualTransaction.rollback(transactionStatus);
                    return BaseResponseUtil.error("系统错误");
                }
            }

            // 4.将用户生成的令牌插入到Token记录表中
            String newToken = keyPrefix + UUID.randomUUID().toString().replace("-", "");
            redisUtil.setString(newToken, userId+"", 1800l);

            UserToken uToken = new UserToken();
            uToken.setUserId(userId);
            uToken.setLoginType(dto.getLoginType());
            uToken.setToken(newToken);
            uToken.setDeviceInfor(deviceInfor);
            int result = userTokenMapper.insertUserToken(uToken);
            if (result<=0) {
                manualTransaction.rollback(transactionStatus);
                return BaseResponseUtil.error("系统错误!");
            }

            // #######提交事务
            JSONObject data = new JSONObject();
            data.put("token", newToken);
            manualTransaction.commit(transactionStatus);
            return BaseResponseUtil.suc(data);
        } catch (Exception e) {
            try {
                // 回滚事务
                manualTransaction.rollback(transactionStatus);
            } catch (Exception e1) {
            }
            return BaseResponseUtil.error("系统错误!");
        }

    }

    @Override
    public BaseResponse<User> ssoLogin(@RequestBody @Validated UserLoginDTO dto) {
        // 判断登陆类型
        String loginType = dto.getLoginType();
        // 目的是限制范围
        if (!(loginType.equals(Constants.MEMBER_LOGIN_TYPE_ANDROID) || loginType.equals(Constants.MEMBER_LOGIN_TYPE_IOS)
                || loginType.equals(Constants.MEMBER_LOGIN_TYPE_PC))) {
            return BaseResponseUtil.error("登陆类型出现错误!");
        }

        // 2.对登陆密码实现加密
        String newPassWord = MD5Util.MD5(dto.getPassword());
        // 3.使用手机号码+密码查询数据库 ，判断用户是否存在
        User user = userMapper.login(dto.getMobile(), newPassWord);
        if (user == null) {
            return BaseResponseUtil.error("用户名称或者密码错误!");
        }
        return BaseResponseUtil.suc(user);
    }
}
