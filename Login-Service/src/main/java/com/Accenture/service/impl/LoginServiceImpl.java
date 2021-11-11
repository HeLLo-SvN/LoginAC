package com.Accenture.service.impl;

import com.Accenture.mapper.UserMapper;
import com.Accenture.pojo.User;
import com.Accenture.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
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
