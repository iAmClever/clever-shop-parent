package com.clever.service;

import com.clever.Constants.BaseResponse;
import com.clever.Constants.Constants;
import com.clever.dto.ExistMobileDTO;
import com.clever.entity.User;
import com.clever.feign.WeiXinServiceFeign;
import com.clever.mapper.UserMapper;
import com.clever.esResposiory.BaseResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by clever on 2019/8/18.
 */
@RestController
@Slf4j
public class MemberService implements MemberServiceApi {
    @Autowired
    private WeiXinServiceFeign weiXinServiceFeign;
    @Autowired
    private UserMapper userMapper;


    @Value("${test.value}")
    private String testValue;
    @Override
    public BaseResponse memberInvokeOtherService() {
        System.out.println(testValue);

        return weiXinServiceFeign.get();
    }

    @Override
    public BaseResponse existMobile(@RequestBody @Validated ExistMobileDTO dto) {
        User user = userMapper.existMobile(dto.getMobile());
        // 对特殊敏感字段需要做脱敏
        if(user!=null){
            user.setPassword(null);
            return BaseResponseUtil.suc(user);
        }
        return BaseResponseUtil.setResult(Constants.HTTP_RES_CODE_EXISTMOBILE_203,"用户信息不存在！",null);
    }
}
