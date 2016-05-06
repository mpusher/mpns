package com.shinemo.mpns.core.async;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

@Service("internalEventBus")
public class InternalEventBus {
	
	private final Executor poolExecutor = Executors.newFixedThreadPool(10);

	/**
	 * 同步的消息通道，串行依注册顺序调用事件处理方法
	 */
	private final EventBus syncEventBus = new EventBus("_internel_sync_");

	/**
	 * 异步的消息通道，异步执行注意确保事件处理方法的同步
	 */
	private final AsyncEventBus asyncEventBus = new AsyncEventBus("_internel_async_", poolExecutor);

	public void postAsync(Object event) {
		asyncEventBus.post(event);
	}

	public void postSync(Object event) {
		syncEventBus.post(event);
	}

	public void post(Object event) {
		postAsync(event);
		postSync(event);
	}

	public void registerAsync(Object listener) {
		asyncEventBus.register(listener);
	}

	public void registerSync(Object listener) {
		syncEventBus.register(listener);
	}

	public void unregisterAsync(Object listener) {
		asyncEventBus.unregister(listener);
	}

	public void unregisterSync(Object listener) {
		syncEventBus.unregister(listener);
	}

}
