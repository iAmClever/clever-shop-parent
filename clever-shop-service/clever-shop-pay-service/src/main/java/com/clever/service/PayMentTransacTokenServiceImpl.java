package com.clever.service;

import com.clever.mapper.PaymentTransactionMapper;
import com.clever.mapper.entity.PaymentTransactionEntity;
import com.alibaba.fastjson.JSONObject;
import com.clever.Constants.BaseResponse;
import com.clever.dto.PayCratePayTokenDto;
import com.clever.esResposiory.BaseResponseUtil;
import com.clever.esResposiory.GenerateToken;
import com.clever.snowFlaker.SnowflakeIdUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayMentTransacTokenServiceImpl  implements PayMentTransacTokenService {
	@Autowired
	private PaymentTransactionMapper paymentTransactionMapper;

	@Autowired
	private GenerateToken generateToken;

	@Override
	public BaseResponse<JSONObject> cratePayToken(PayCratePayTokenDto payCratePayTokenDto) {
		String orderId = payCratePayTokenDto.getOrderId();
		Long payAmount = payCratePayTokenDto.getPayAmount();
		Long userId = payCratePayTokenDto.getUserId();
		// 2.将输入插入数据库中 待支付记录
		PaymentTransactionEntity paymentTransactionEntity = new PaymentTransactionEntity();
		paymentTransactionEntity.setOrderId(orderId);
		paymentTransactionEntity.setPayAmount(payAmount);
		paymentTransactionEntity.setUserId(userId);
		// 使用雪花算法 生成全局id
		paymentTransactionEntity.setPaymentId(SnowflakeIdUtils.nextId());
		int result = paymentTransactionMapper.insertPaymentTransaction(paymentTransactionEntity);
		if (!(result>0)) {
			return BaseResponseUtil.error("系统错误!");
		}
		// 获取主键id
		Long payId = paymentTransactionEntity.getId();
		if (payId == null) {
			return BaseResponseUtil.error("系统错误!");
		}

		// 3.生成对应支付令牌
		String keyPrefix = "pay_";
		String token = generateToken.createToken(keyPrefix, payId + "");
		JSONObject dataResult = new JSONObject();
		dataResult.put("token", token);

		return BaseResponseUtil.suc(dataResult);
	}

}
