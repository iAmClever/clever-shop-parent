package com.clever.service;

import com.alibaba.fastjson.JSONObject;
import com.clever.Constants.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 查询秒杀记录
 *
 */
public interface OrderSeckillService {

	@GetMapping("/getOrder")
	public BaseResponse<JSONObject> getOrder(String phone, Long seckillId);

}
