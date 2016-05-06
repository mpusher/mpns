package com.shinemo.mpns.mpush;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Maps;

public class MapTest {

	@Test
	public void test(){
		Map<Long,String> map = Maps.newTreeMap(new Comparator<Long>() {
			@Override
			public int compare(Long o1, Long o2) {
				return (int)(o1-o2);
			}
			
		});
		
		map.put(3L, "10.1.10.3:3000");
		map.put(5L, "10.1.10.5:3000");
		map.put(2L, "10.1.10.2:3000");
		
		Collection<String> ret = map.values();
		
		for(String str:ret){
			System.out.println(str);
		}
		
	}
	
}
