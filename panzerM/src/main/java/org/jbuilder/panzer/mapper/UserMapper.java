package org.jbuilder.panzer.mapper;

import org.jbuilder.panzer.entity.User;

import java.util.List;

public interface UserMapper {
    List<User> getUsers();

    User getUser(Integer id);

    long addUser(User user);

    long deleteUser(long id);
}
