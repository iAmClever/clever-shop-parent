package com.clever.mapper;

import com.clever.mapper.entity.PaymentTransactionLogEntity;
import org.apache.ibatis.annotations.Insert;

public interface PaymentTransactionLogMapper {

	@Insert("INSERT INTO `payment_transaction_log` VALUES (NULL, NULL, #{asyncLog},NULL, #{transactionId}, null, null, NOW(), null, NOW(),NULL);")
	public int insertTransactionLog(PaymentTransactionLogEntity paymentTransactionLog);

}
