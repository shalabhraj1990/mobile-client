package com.spring.mobile.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.mobile.client.integration.MobileServiceIntegration;

import msk.spring.boot.common.mobile.dto.MobileDto;
import reactor.core.publisher.Flux;

@Service
public class MobileClientService {
	@Autowired
	MobileServiceIntegration mobileServiceIntegration;

	public Flux<MobileDto> getAllMobileInfo() {
		return mobileServiceIntegration.getAllMobiles();
	}
}
