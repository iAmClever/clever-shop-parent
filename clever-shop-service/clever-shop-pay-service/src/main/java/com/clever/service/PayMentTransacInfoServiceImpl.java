package com.clever.service;


import com.clever.mapper.PaymentTransactionMapper;
import com.clever.mapper.entity.PaymentTransactionEntity;
import com.clever.Constants.BaseResponse;
import com.clever.esResposiory.BaseResponseUtil;
import com.clever.esResposiory.GenerateToken;
import com.clever.esResposiory.MyBeanUtils;
import com.clever.vo.PayMentTransacVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayMentTransacInfoServiceImpl implements PayMentTransacInfoService {
	@Autowired
	private GenerateToken generateToken;
	@Autowired
	private PaymentTransactionMapper paymentTransactionMapper;

	@Override
	public BaseResponse<PayMentTransacVO> tokenByPayMentTransac(String token) {
		// 1.验证token是否为空
		if (StringUtils.isEmpty(token)) {
			return BaseResponseUtil.error("token参数不能空!");
		}
		// 2.使用token查询redisPayMentTransacID
		String value = generateToken.getToken(token);
		if (StringUtils.isEmpty(value)) {
			return BaseResponseUtil.error("该Token可能已经失效或者已经过期");
		}
		// 3.转换为整数类型
		Long transactionId = Long.parseLong(value);
		// 4.使用transactionId查询支付信息
		PaymentTransactionEntity paymentTransaction = paymentTransactionMapper.selectById(transactionId);
		if (paymentTransaction == null) {
			return BaseResponseUtil.error("未查询到该支付信息");
		}
		return BaseResponseUtil.suc(MyBeanUtils.doToDto(paymentTransaction, PayMentTransacVO.class));
	}

}
