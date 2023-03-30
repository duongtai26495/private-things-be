package com.kai.pthings.service;

import com.kai.pthings.entity.User;

public interface UserService {
    User updateUserByUsername(User user);

    User findByUsername(String username);
}
