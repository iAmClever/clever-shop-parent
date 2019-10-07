package com.clever.mp.handler;

import com.clever.Constants.BaseResponse;
import com.clever.Constants.Constants;
import com.clever.dto.ExistMobileDTO;
import com.clever.feign.MemberServiceFeign;
import com.clever.mp.builder.TextBuilder;
import com.clever.esResposiory.RedisUtil;
import com.clever.esResposiory.RegexUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
@Slf4j
public class MsgHandler extends AbstractHandler {

	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private MemberServiceFeign memberServiceFeign;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService weixinService,
			WxSessionManager sessionManager) {

		if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
			// TODO 可以选择将消息保存到本地
		}

		// 当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
		try {
			if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
					&& weixinService.getKefuService().kfOnlineList().getKfOnlineList().size() > 0) {
				return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE().fromUser(wxMessage.getToUser())
						.toUser(wxMessage.getFromUser()).build();
			}
		} catch (WxErrorException e) {
			e.printStackTrace();
		}

		String fromContent = wxMessage.getContent();
		log.info("fromContent:" + fromContent);
		ExistMobileDTO dto = new ExistMobileDTO();
		dto.setMobile(fromContent);
		BaseResponse baseResponse = memberServiceFeign.existMobile(dto);
		if(baseResponse.getCode().equals(Constants.HTTP_RES_CODE_200)){
			return new TextBuilder().build("该手机号码已注册!", wxMessage, weixinService);
		}
		if(!baseResponse.getCode().equals(Constants.HTTP_RES_CODE_EXISTMOBILE_203)){
			return new TextBuilder().build("未知错误："+baseResponse.getMsg(), wxMessage, weixinService);
		}
		// TODO 组装回复消息
		//String content = "欢迎来到Keep Code！";
		if(RegexUtils.checkMobile(fromContent)){
			int registCode = (int) (Math.random() * 9000 + 1000);
			String reg = "注册码："+ registCode;
			// 将注册码存入在redis中 key为手机号码
			redisUtil.setString(Constants.WEIXIN_KEY + fromContent, registCode + "", Constants.WEIXINCODE_TIMEOUT);
			return new TextBuilder().build(reg, wxMessage, weixinService);
		}else{
			return new TextBuilder().build("验证出错！", wxMessage, weixinService);
		}

	}

}
