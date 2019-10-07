package com.clever.mapper;

import com.clever.mapper.entity.PaymentChannelEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface PaymentChannelMapper {

	@Select("SELECT channel_Name  AS channelName , channel_Id AS channelId, merchant_Id AS merchantId,sync_Url AS syncUrl, asyn_Url AS asynUrl,public_Key AS publicKey, private_Key AS privateKey,channel_State AS channelState ,class_ADDRES as classAddres  FROM payment_channel WHERE CHANNEL_STATE='0';")
	public List<PaymentChannelEntity> selectAll();

	@Select("SELECT channel_Name  AS channelName , channel_Id AS channelId, merchant_Id AS merchantId,sync_Url AS syncUrl, asyn_Url AS asynUrl,public_Key AS publicKey, private_Key AS privateKey,channel_State AS channelState ,class_addres as classAddres  FROM payment_channel WHERE CHANNEL_STATE='0' AND  channel_Id=#{channelId};")
	public PaymentChannelEntity selectBychannelId(String channelId);

}
