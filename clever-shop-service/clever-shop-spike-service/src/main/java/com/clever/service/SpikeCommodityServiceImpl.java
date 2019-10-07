package com.clever.service;

import com.alibaba.fastjson.JSONObject;
import com.clever.Constants.BaseResponse;
import com.clever.esResposiory.BaseResponseUtil;
import com.clever.esResposiory.GenerateToken;
import com.clever.mapper.SeckillMapper;
import com.clever.mapper.entity.SeckillEntity;
import com.clever.producer.SpikeCommodityProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SpikeCommodityServiceImpl implements SpikeCommodityService {
	@Autowired
	private SeckillMapper seckillMapper;
	@Autowired
	private GenerateToken generateToken;
	@Autowired
	private SpikeCommodityProducer spikeCommodityProducer;

	@Override
	@Transactional
	public BaseResponse<JSONObject> spike(String phone, Long seckillId) {
		// 1.参数验证
		if (StringUtils.isEmpty(phone)) {
			return BaseResponseUtil.error("手机号码不能为空!");
		}
		if (seckillId == null) {
		return BaseResponseUtil.error("商品库存id不能为空!");
	}
	// 2.从redis从获取对应的秒杀token
	String seckillToken = generateToken.getListKeyToken(seckillId + "");
		if (StringUtils.isEmpty(seckillToken)) {
		log.info(">>>seckillId:{}, 亲，该秒杀已经售空，请下次再来!", seckillId);
		return BaseResponseUtil.error("亲，该秒杀已经售空，请下次再来!");
	}

		// 3.获取到秒杀token之后，异步放入mq中实现修改商品的库存
		sendSeckillMsg(seckillId, phone);
		return BaseResponseUtil.suc("正在排队中.......");
	}

	/**
	 * 获取到秒杀token之后，异步放入mq中实现修改商品的库存
	 */
	@Async
	private void sendSeckillMsg(Long seckillId, String phone) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("seckillId", seckillId);
		jsonObject.put("phone", phone);
		spikeCommodityProducer.send(jsonObject);
	}

	// 结业项目中采用rabbitmq实现秒杀
	/**
	 * 面试官 都喜欢问 你们项目中在那些地方使用到多线程
	 * 
	 * @param seckillId
	 * @param tokenQuantity
	 * @return
	 */

	// 采用redis数据库类型为 list类型 key为 商品库存id list 多个秒杀token
	@Override
	public BaseResponse<JSONObject> addSpikeToken(Long seckillId, Long tokenQuantity) {
		// 1.验证参数
		if (seckillId == null) {
			return BaseResponseUtil.error("商品库存id不能为空!");
		}
		if (tokenQuantity == null) {
			return BaseResponseUtil.error("token数量不能为空!");
		}
		SeckillEntity seckillEntity = seckillMapper.findBySeckillId(seckillId);
		if (seckillEntity == null) {
			return BaseResponseUtil.error("商品信息不存在!");
		}
		// 2.使用多线程异步生产令牌
		createSeckillToken(seckillId, tokenQuantity);
		return BaseResponseUtil.suc("令牌生成完成");
	}

	//@Async
	private void createSeckillToken(Long seckillId, Long tokenQuantity) {
		generateToken.createListToken("seckill_", seckillId + "", tokenQuantity);
	}

}
