package com.clever.service;

import com.alibaba.fastjson.JSONObject;
import com.clever.Constants.BaseResponse;
import com.clever.Constants.Constants;
import com.clever.dto.VerificaCodeReq;
import com.clever.esResposiory.BaseResponseUtil;
import com.clever.esResposiory.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by clever on 2019/9/1.
 */
@Service
@Slf4j
@RestController
public class VerificaCodeServiceIImpl implements VerificaCodeService {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public BaseResponse<JSONObject> verificaWeixinCode(@RequestBody @Validated VerificaCodeReq req) {
        log.debug("hahah");
        String weixinCodeKey = Constants.WEIXIN_KEY + req.getPhone();
        String code = redisUtil.getString(weixinCodeKey);
        if(StringUtils.isEmpty(code)){
            return BaseResponseUtil.error("注册码为空或者已经过期！");
        }
        if(!code.equals(req.getWeixinCode())){
            return BaseResponseUtil.error("注册码不正确！");
        }
        return BaseResponseUtil.suc();
    }
}
