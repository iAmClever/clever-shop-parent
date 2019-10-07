package com.clever.callback.template.unionpay;

import com.alibaba.fastjson.JSONObject;
import com.clever.callback.template.AbstractPayCallbackTemplate;
import com.clever.constant.PayConstant;
import com.clever.mapper.PaymentTransactionMapper;
import com.clever.mapper.entity.PaymentTransactionEntity;
import com.clever.mq.producer.IntegralProducer;
import com.clever.sdk.AcpService;
import com.clever.sdk.LogUtil;
import com.clever.sdk.SDKConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
public class UnionPayCallbackTemplate extends AbstractPayCallbackTemplate {

	@Autowired
	private PaymentTransactionMapper paymentTransactionMapper;
	@Autowired
	private IntegralProducer integralProducer;

	@Override
	public Map<String, String> verifySignature(HttpServletRequest req, HttpServletResponse resp) {

		LogUtil.writeLog("BackRcvResponse接收后台通知开始");

		String encoding = req.getParameter(SDKConstants.param_encoding);
		// 获取银联通知服务器发送的后台通知参数
		Map<String, String> reqParam = getAllRequestParam(req);
		LogUtil.printRequestLog(reqParam);

		// 重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
		if (!AcpService.validate(reqParam, encoding)) {
			LogUtil.writeLog("验证签名结果[失败].");
			reqParam.put(PayConstant.RESULT_NAME, PayConstant.RESULT_PAYCODE_201);
		} else {
			LogUtil.writeLog("验证签名结果[成功].");
			// 【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
			String orderId = reqParam.get("orderId"); // 获取后台通知的数据，其他字段也可用类似方式获取
			reqParam.put("paymentId", orderId);
			reqParam.put(PayConstant.RESULT_NAME, PayConstant.RESULT_PAYCODE_200);
		}
		LogUtil.writeLog("BackRcvResponse接收后台通知结束");
		return reqParam;
	}

	// 异步回调中网络尝试延迟，导致异步回调重复执行 可能存在幂等性问题
	//
	@Override
	@Transactional
	public String asyncService(Map<String, String> verifySignature) {

		String orderId = verifySignature.get("orderId"); // 获取后台通知的数据，其他字段也可用类似方式获取
		String respCode = verifySignature.get("respCode");

		// 判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
		System.out.println("orderId:" + orderId + ",respCode:" + respCode);
		// 1.判断respCode是否为已经支付成功断respCode=00、A6后，
		if (!(respCode.equals("00") || respCode.equals("A6"))) {
			return failResult();
		}
		// 根据日志 手动补偿 使用支付id调用第三方支付接口查询
		PaymentTransactionEntity paymentTransaction = paymentTransactionMapper.selectByPaymentId(orderId);
		if (paymentTransaction.getPaymentStatus().equals(PayConstant.PAY_STATUS_SUCCESS)) {
			// 网络重试中，之前已经支付过
			return successResult();
		}
		// 2.将状态改为已经支付成功
		paymentTransactionMapper.updatePaymentStatus(PayConstant.PAY_STATUS_SUCCESS + "", orderId, "yinlian_pay");
		// 3.调用积分服务接口增加积分(处理幂等性问题) MQ
		addMQIntegral(paymentTransaction); // 使用MQ
		int i = 1 / 0; // 支付状态还是为待支付状态但是 积分缺增加
		return successResult();
	}

	/**
	 * 基于MQ增加积分
	 */
	@Async
	private void addMQIntegral(PaymentTransactionEntity paymentTransaction) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("paymentId", paymentTransaction.getPaymentId());
		jsonObject.put("userId", paymentTransaction.getUserId());
		jsonObject.put("integral", 100);
		integralProducer.send(jsonObject);
	}

	@Override
	public String failResult() {
		return PayConstant.YINLIAN_RESULT_FAIL;
	}

	@Override
	public String successResult() {
		return PayConstant.YINLIAN_RESULT_SUCCESS;
	}

	/**
	 * 获取请求参数中所有的信息 当商户上送frontUrl或backUrl地址中带有参数信息的时候，
	 * 这种方式会将url地址中的参数读到map中，会导多出来这些信息从而致验签失败，
	 * 这个时候可以自行修改过滤掉url中的参数或者使用getAllRequestParamStream方法。
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<String> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				// 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				if (res.get(en) == null || "".equals(res.get(en))) {
					// System.out.println("======为空的字段名===="+en);
					res.remove(en);
				}
			}
		}
		return res;
	}
	/**
	 * 回调机制 必须遵循规范 重试机制都是采用间隔新 错开的话 必须
	 */

}
