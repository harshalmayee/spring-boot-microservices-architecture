package com.harshalmayee.service.impl;

import com.harshalmayee.entity.LoginUser;
import com.harshalmayee.repository.UserRepository;
import com.harshalmayee.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveAll(List<LoginUser> loginUsers) {
       userRepository.saveAll(loginUsers);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        LoginUser loginUser=userRepository.findByUserName(userName);
        if(loginUser==null){
            throw new UsernameNotFoundException("Invalid UserName or Password...");
        }
        return new org.springframework.security.core.userdetails.User(loginUser.getUserName(),loginUser.getPassword(),new ArrayList<>());
    }
}
