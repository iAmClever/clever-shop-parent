package com.clever.service;

import org.springframework.web.bind.annotation.GetMapping;


public interface PayContextService {
	@GetMapping("/toPayHtml")
	public String toPayHtml(String channelId, String payToken);
}
