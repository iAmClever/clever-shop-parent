package com.clever.service;


import com.clever.mapper.PaymentChannelMapper;
import com.clever.mapper.entity.PaymentChannelEntity;
import com.clever.mapper.MapperUtils;
import com.clever.vo.PaymentChannelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentChannelServiceImpl implements PaymentChannelService {
	@Autowired
	private PaymentChannelMapper paymentChannelMapper;

	@Override
	public List<PaymentChannelVO> selectAll() {
		List<PaymentChannelEntity> paymentChanneList = paymentChannelMapper.selectAll();
		return MapperUtils.mapAsList(paymentChanneList, PaymentChannelVO.class);
	}

}
