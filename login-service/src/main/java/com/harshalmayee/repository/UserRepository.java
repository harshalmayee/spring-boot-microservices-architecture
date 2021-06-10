package com.harshalmayee.repository;

import com.harshalmayee.entity.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<LoginUser,Long> {
    LoginUser findByUserName(String userName);
}
