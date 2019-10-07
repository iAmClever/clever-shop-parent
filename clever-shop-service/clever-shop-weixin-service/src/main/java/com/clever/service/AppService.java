package com.clever.service;

import com.clever.Constants.BaseResponse;
import com.clever.entity.AppEntity;
import com.clever.esResposiory.BaseResponseUtil;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppService implements WeiXinSerciceApi {

    @Override
    public BaseResponse get() {
        System.out.println("heheheheh");
        return BaseResponseUtil.suc(new AppEntity("1","2"));
    }
}
