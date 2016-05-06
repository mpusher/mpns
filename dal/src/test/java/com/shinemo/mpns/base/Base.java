package com.shinemo.mpns.base;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;



@ContextConfiguration(locations = {"classpath:mpns-dal.xml"})
public class Base extends AbstractJUnit4SpringContextTests{
	
}
