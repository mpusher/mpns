package com.shinemo.mpns.web.sys.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StatusController{
	
	@RequestMapping(value="/checkstatus",method=RequestMethod.GET)
	@ResponseBody
	public String check() {
        return "success";
	}
	
	
	@RequestMapping(value="/myjingjiniao",method=RequestMethod.GET)
	@ResponseBody
	public String test() {
        return "myjingjiniao";
	}
	
}
