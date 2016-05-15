package com.shinemo.mpns.core.mpush.service.impl;



import java.util.List;

import org.springframework.stereotype.Service;

import com.shinemo.mpns.core.mpush.service.UserService;
import com.mpush.api.RedisKey;
import com.mpush.tools.redis.manage.RedisManage;

@Service("userService")
public class UserServiceImpl  implements UserService {

	@Override
	public long onlineUserNum(String extranetAddress) {
		return RedisManage.zCard(RedisKey.getUserOnlineKey(extranetAddress));
	}

	@Override
	public List<String> onlineUserList(String extranetAddress, int start, int size) {
		if(size<10){
    		size = 10;
    	}
    	return RedisManage.zrange(RedisKey.getUserOnlineKey(extranetAddress), start, size-1, String.class);
	}

}

