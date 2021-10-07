package com.blog.util;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.entities.User;
import com.blog.services.UserService;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserService userService;
	
	@Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        
		User user =  userService.getUser(s);
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),new ArrayList<>());
		
    }
}
