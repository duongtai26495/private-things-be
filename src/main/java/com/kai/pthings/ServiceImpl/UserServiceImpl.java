package com.kai.pthings.ServiceImpl;

import com.kai.pthings.config.Snippets;
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
        User getUser = userRepository.findUserByUname(user.getUsername());

        if(user.getPassword() != null) {
            user.setUpassword(passwordEncoder.encode(user.getPassword()));
            if (!user.getPassword().equals(getUser.getPassword())) {
                getUser.setUpassword(user.getPassword());
            }
        }

        if(user.getProfile_image() != null && !user.getProfile_image().equals(getUser.getProfile_image())){
            getUser.setProfile_image(user.getProfile_image());
        }

        if(user.getName() != null){
            getUser.setName(user.getName());
        }

//        if(user.getGender()>0 && user.getGender() <=2){
//            getUser.setGender(user.getGender());
//        }
//        getUser.setLast_edited(sdf.format(date));

        return userRepository.save(getUser);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUname(username);
    }

    @Override
    public User changeProfileImage(User user) {
        User getUser = userRepository.findUserByUname(user.getUsername());
        getUser.setProfile_image(user.getProfile_image());
        return userRepository.save(getUser);
    }

}
