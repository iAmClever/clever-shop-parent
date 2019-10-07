package com.clever.feign;

import com.clever.service.VerificaCodeService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by clever on 2019/9/1.
 */
@FeignClient("clever-shop-weixin")
public interface VerificaCodeServiceFeign extends VerificaCodeService {
}
