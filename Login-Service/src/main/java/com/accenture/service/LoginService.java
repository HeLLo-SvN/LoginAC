package com.accenture.service;

import com.accenture.pojo.User;

public interface LoginService {

    public void register(String userName, String password);

    public User login(String userName, String password);

}
