package com.shinemo.mpns.core.async;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("asyncSupport")
public class AsyncSupport {

	@Resource
	private InternalEventBus internalEventBus;

	@PostConstruct
	private void init() {
		internalEventBus.registerAsync(this);
	}

}
