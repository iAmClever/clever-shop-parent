package com.clever.service;

import com.clever.Constants.BaseResponse;
import com.clever.esResposiory.BaseResponseUtil;
import com.clever.mapper.OrderMapper;
import com.clever.mapper.entity.OrderEntity;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class OrderSeckillServiceImpl implements OrderSeckillService {
	@Autowired
	private OrderMapper orderMapper;

	@Override
	public BaseResponse getOrder(String phone, Long seckillId) {
		if (StringUtils.isEmpty(phone)) {
			return BaseResponseUtil.error("手机号码不能为空!");
		}
		if (seckillId == null) {
			return BaseResponseUtil.error("商品库存id不能为空!");
		}
		OrderEntity orderEntity = orderMapper.findByOrder(phone, seckillId);
		if (orderEntity == null) {
			return BaseResponseUtil.error("正在排队中.....");
		}
		return BaseResponseUtil.suc("恭喜你秒杀成功!");
	}

}
