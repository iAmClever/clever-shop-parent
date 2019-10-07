package com.clever.service;

import com.clever.Constants.BaseResponse;
import com.clever.vo.PayMentTransacVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


public interface PayMentTransacInfoService {
	@GetMapping("/tokenByPayMentTransac")
	public BaseResponse<PayMentTransacVO> tokenByPayMentTransac(@RequestParam("token") String token);
}
