package com.accenture.service.impl;

import com.accenture.mapper.UserMapper;
import com.accenture.pojo.User;
import com.accenture.service.LoginService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    public UserMapper userMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void register(String userName, String password) {

        userMapper.registerInsert(userName, password);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public User login(String userName, String password) {

        return userMapper.loginSelect(userName,password);
    }


}
