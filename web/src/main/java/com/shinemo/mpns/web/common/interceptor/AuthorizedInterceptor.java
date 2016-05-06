package com.shinemo.mpns.web.common.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.shinemo.mpush.tools.MPushUtil;


public class AuthorizedInterceptor implements HandlerInterceptor{
	
	private static Logger log =  LoggerFactory.getLogger(AuthorizedInterceptor.class);
	
	private static List<String> whiteUrl = new ArrayList<String>();
	
	private static String ip = "112.16.91.49";
	
	static{
		whiteUrl.add("/push/server");
		whiteUrl.add("/checkstatus");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String url = request.getRequestURI();
		
		String context = request.getContextPath();
		
		boolean whiteUlr = isWhiteUrl(url.substring(context.length()));
		
		if(whiteUlr){
			return true;
		}
		
		String remoteAddr = getRemortIP(request);
		
		log.info("remoteAddr:{},url:{}",remoteAddr,url);
		
		boolean isLocal = MPushUtil.isLocalHost(remoteAddr);
		if(isLocal||remoteAddr.equals(ip)){
			return true;
		}
		
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "you ip is not allowed:"+remoteAddr);
		
		return false;
		
	}

	private boolean isWhiteUrl(String url){
		for(String temp:whiteUrl){
			if(url.startsWith(temp)){
				return true;
			}
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		  
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}
	
	private String getRemortIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");   
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
	        ip = request.getHeader("Proxy-Client-IP");   
	    }   
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
	        ip = request.getHeader("WL-Proxy-Client-IP");   
	  
	    }   
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
	        ip = request.getRemoteAddr();   
	    }   
	    return ip; 
	}
	

}

