package com.clever.mapper;

import com.clever.mapper.entity.OrderEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface OrderMapper {

	@Insert("INSERT INTO `clever_order` VALUES (#{seckillId},#{userPhone},#{state}, now());")
	int insertOrder(OrderEntity orderEntity);

	@Select("SELECT seckill_id AS seckillid,user_phone as userPhone , state as state FROM clever_order WHERE USER_PHONE=#{phone}  and seckill_id=#{seckillId}  AND STATE='1';")
	OrderEntity findByOrder(@Param("phone") String phone, @Param("seckillId") Long seckillId);
}
