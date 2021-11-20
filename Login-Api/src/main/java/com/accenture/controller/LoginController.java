package com.accenture.controller;

import com.accenture.cache.RedisCache;
import com.accenture.pojo.Jwt;
import com.accenture.pojo.User;
import com.accenture.service.LoginService;
import com.accenture.utils.*;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("accenture")
public class LoginController{

    @Autowired
    private LoginService loginService;
//    @Autowired
//    private RedisUtils redis;
    @Autowired
    private RedisCache redis;

    @Autowired
    private JwtUtils jwt;

    @ApiOperation(value = "用户注册",notes = "持久化用户注册信息",httpMethod = "POST")
    @PostMapping("/regist")
    public JsonResult regist(@RequestParam String userName,
                             @RequestParam String password) {

        if (StringUtils.isBlank(userName) ||
                StringUtils.isBlank(password)) {
            return JsonResult.errorMsg("用户名或密码不能为空");
        }


        if (password.length() < 6){
            return JsonResult.errorMsg("密码长度不能少于6位");
        }

        loginService.register(userName, password);

        return JsonResult.ok(userName);
    }

    @ApiOperation(value = "用户登录",notes = "查询用户信息是否存在于数据库",httpMethod = "POST")
    @PostMapping("/login")
    public JsonResult login(@RequestParam String userName,
                            @RequestParam String password) {

        if (StringUtils.isBlank(userName) ||
                StringUtils.isBlank(password)) {
            return JsonResult.errorMsg("用户名或密码不能为空");
        }

        User userResult = loginService.login(userName, password);

        if (userResult == null){
            return  JsonResult.errorMsg("用户名或密码输入错误");
        }

        Jwt jwtInfo = Jwt.builder()
                .userName(userName)
                .token(jwt.createJwtToken(userName))
                .refreshToken(UUID.randomUUID().toString())
                .build();

        //redis.set(jwtInfo.getRefreshToken(),JsonUtils.objectToJson(jwtInfo),60*60);
        redis.autoCacheManager(jwtInfo.getRefreshToken(), jwtInfo);

        return JsonResult.ok(jwtInfo);
    }

}
