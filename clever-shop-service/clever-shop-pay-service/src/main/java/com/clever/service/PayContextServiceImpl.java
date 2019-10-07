package com.clever.service;

import com.alibaba.fastjson.JSONObject;
import com.clever.Constants.BaseResponse;
import com.clever.Constants.Constants;
import com.clever.esResposiory.BaseResponseUtil;
import com.clever.mapper.PaymentChannelMapper;
import com.clever.mapper.entity.PaymentChannelEntity;
import com.clever.vo.PayMentTransacVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayContextServiceImpl implements PayContextService {

	@Autowired
	private PaymentChannelMapper paymentChannelMapper;
	@Autowired
	private PayMentTransacInfoService payMentTransacInfoService;
	@Autowired
	private UnionPayStrategy unionPayStrategy;

	public String toPayHtml(String channelId, String payToken) {
		// 1.使用渠道id查询渠道信息
		PaymentChannelEntity pymentChannel = paymentChannelMapper.selectBychannelId(channelId);
		if (pymentChannel == null) {
			return "没有查询到该渠道信息";
		}

		// 2.使用payToken查询待支付信息
		BaseResponse<PayMentTransacVO> tokenByPayMentTransac = payMentTransacInfoService.tokenByPayMentTransac(payToken);
		if (tokenByPayMentTransac==null || tokenByPayMentTransac.getCode().equals(Constants.HTTP_RES_CODE_500)) {
			return tokenByPayMentTransac.getMsg();
		}
		// 3.使用Java反射机制初始化子类
		//String classAddres = pymentChannel.getClassAddres();
		//PayStrategy payStrategy = StrategyFactory.getPayStrategy(classAddres);
//		if (payStrategy == null) {
//			return BaseResponseUtil.error("支付系统网关错误!");
//		}
		PayMentTransacVO payMentTransacVO = tokenByPayMentTransac.getData();
		// 4.直接执行子类实现方法
		String payHtml = unionPayStrategy.toPayHtml(pymentChannel,payMentTransacVO);
		JSONObject data = new JSONObject();
		data.put("payHtml", payHtml);
		return payHtml;
	}
	// 设计模式 切合实际的项目运用场景

}
