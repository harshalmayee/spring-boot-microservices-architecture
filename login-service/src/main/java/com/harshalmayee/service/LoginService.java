package com.harshalmayee.service;

import com.harshalmayee.entity.LoginUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface LoginService extends UserDetailsService {

    void saveAll(List<LoginUser> loginUsers);
}
