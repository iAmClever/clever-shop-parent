package com.clever.feign;

import com.clever.service.WeiXinSerciceApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by clever on 2019/8/18.
 */
@FeignClient(name = "clever-shop-weixin")
public interface WeiXinServiceFeign extends WeiXinSerciceApi {
}
