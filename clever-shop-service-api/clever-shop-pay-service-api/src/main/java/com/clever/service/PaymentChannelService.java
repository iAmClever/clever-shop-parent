package com.clever.service;

import com.clever.vo.PaymentChannelVO;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface PaymentChannelService {
	/**
	 * 查询所有支付渠道
	 * 
	 * @return
	 */
	@GetMapping("/selectAll")
	public List<PaymentChannelVO> selectAll();
}
