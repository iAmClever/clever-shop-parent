package com.clever.mapper;

import com.clever.mapper.entity.SeckillEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


public interface SeckillMapper {

	@Select("SELECT seckill_id AS seckillId,name as name,inventory as inventory,start_time as startTime,end_time as endTime,create_time as createTime,version as version from clever_seckill where seckill_id=#{seckillId}")
	SeckillEntity findBySeckillId(Long seckillId);

	@Update("update clever_seckill set inventory=inventory-1, version=version+1 where  seckill_id=#{seckillId} and inventory>0  and version=#{version} ;")
	int inventoryDeduction(@Param("seckillId") Long seckillId, @Param("version") Long version);

	@Update("update clever_seckill set inventory=inventory-1, version=version+1 where  seckill_id=#{seckillId}  ")
	int modifyInventory(@Param("seckillId") Long seckillId, @Param("version") Long version);


}