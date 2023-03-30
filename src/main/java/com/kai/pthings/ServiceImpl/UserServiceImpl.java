package com.kai.pthings.ServiceImpl;

import com.kai.pthings.entity.User;
import com.kai.pthings.repository.UserRepository;
import com.kai.pthings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public User updateUserByUsername(User user) {
        return null;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUname(username);
    }
}
