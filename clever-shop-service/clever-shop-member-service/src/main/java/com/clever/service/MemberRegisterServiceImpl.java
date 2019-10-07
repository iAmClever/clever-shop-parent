package com.clever.service;

import com.clever.Constants.BaseResponse;
import com.clever.Constants.Constants;
import com.clever.dto.RegisterDTO;
import com.clever.dto.VerificaCodeReq;
import com.clever.entity.User;
import com.clever.feign.VerificaCodeServiceFeign;
import com.clever.mapper.UserMapper;
import com.clever.esResposiory.BaseResponseUtil;
import com.clever.esResposiory.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by clever on 2019/9/1.
 */
@RestController
@Slf4j
public class MemberRegisterServiceImpl implements MemberRegisterService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VerificaCodeServiceFeign verificaCodeServiceFeign;

    @Transactional
    @Override
    public BaseResponse register(@RequestBody @Validated RegisterDTO dto) {
        VerificaCodeReq req = VerificaCodeReq.builder().phone(dto.getUser().getMobile()).weixinCode(dto.getRegistCode()).build();
        BaseResponse baseResponse = verificaCodeServiceFeign.verificaWeixinCode(req);
        if(!baseResponse.getCode().equals(Constants.HTTP_RES_CODE_200)){
            return BaseResponseUtil.error(baseResponse.getMsg());
        }
        User user = dto.getUser();
        user.setPassword(MD5Util.MD5(user.getPassword()));
        return userMapper.register(user)>0 ? BaseResponseUtil.suc() : BaseResponseUtil.error("注册失败！");
    }
}
