package com.clever.feign;

import com.clever.service.MemberServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by clever on 2019/9/1.
 */
@FeignClient("clever-shop-member")
public interface MemberServiceFeign extends MemberServiceApi {
}
