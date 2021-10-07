package com.blog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.blog.entities.User;
import com.blog.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    public void save(User user){
    	user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        userRepository.save(user);
    }

    public User getUser(String username){
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}