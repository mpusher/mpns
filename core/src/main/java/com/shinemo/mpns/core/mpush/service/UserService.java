package com.shinemo.mpns.core.mpush.service;

import java.util.List;

public interface UserService {

    //在线用户
    public long onlineUserNum(String extranetAddress);
    
    //在线用户列表
    public List<String> onlineUserList(String extranetAddress,int start,int size);
    
}
