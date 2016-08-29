package org.jbuilder.dunker.service;

import org.jbuilder.dunker.entity.User;
import org.jbuilder.dunker.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by d05660ddw on 16/8/25.
 */

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserInfo(){
        User user=userMapper.findUserInfo();
        //User user=null;
        return user;
    }
}
