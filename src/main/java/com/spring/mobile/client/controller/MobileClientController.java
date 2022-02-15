package com.spring.mobile.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.mobile.client.service.MobileClientService;

import msk.spring.boot.common.mobile.dto.MobileDto;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("mobile-client")
public class MobileClientController {
	@Autowired
	MobileClientService mobileClientService;

	@GetMapping
	public Flux<MobileDto> getAllMobileDetails() {
		return mobileClientService.getAllMobileInfo();
	}
}
