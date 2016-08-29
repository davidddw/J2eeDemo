package org.jbuilder.panzer.service;

import org.jbuilder.panzer.entity.User;
import org.jbuilder.panzer.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserService {
    @Autowired
    private UserMapper mapper;

    public UserMapper getMapper() {
        return mapper;
    }

    public User getUserById(Integer userId) {
        // return mapper.getUserById(userId);
        return mapper.getUser(userId);
    }

    public List<User> getUsers() {
        return mapper.getUsers();
    }

    public long addUser(User user) {
        return mapper.addUser(user);
    }

    public long deleteUser(long id) {
        return mapper.deleteUser(id);
    }
}
