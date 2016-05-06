package com.shinemo.mpns.core.mpush.domain;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class App implements Comparable<App>{
	
	private final String ipAndPort;
	private final long weight;
	
	public App(String ipAndPort, Long weight) {
		this.ipAndPort = ipAndPort;
		if(weight == null){
			this.weight = 0L;
		}else{
			this.weight = weight;
		}
	}

	public String getIpAndPort() {
		return ipAndPort;
	}

	public long getWeight() {
		return weight;
	}
	
	@Override
	public int compareTo(App o) {
		return (int)(this.weight-o.weight);
	}
	
	public static void main(String[] args) {
		List<App> appList = Lists.newArrayList(new App("10.1.10.3:3000", 3L),new App("10.1.10.5:3000", 5L),new App("10.1.10.2:3000", 2L));
		
		Collections.sort(appList);
		
		for(App app:appList){
			System.out.println(ToStringBuilder.reflectionToString(app, ToStringStyle.JSON_STYLE));
		}
		
		
		System.out.println(Joiner.on(",").join(appList));
		
	}





}
