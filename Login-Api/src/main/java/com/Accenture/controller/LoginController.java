package com.Accenture.controller;

import com.Accenture.pojo.User;
import com.Accenture.service.LoginService;
import com.Accenture.utils.CookieUtils;
import com.Accenture.utils.JsonResult;
import com.Accenture.utils.JsonUtils;
import com.Accenture.utils.RedisUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("accenture")
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private RedisUtils redis;

    @ApiOperation(value = "用户注册",notes = "持久化用户注册信息",httpMethod = "POST")
    @PostMapping("/regist")
    public JsonResult regist(@RequestParam String userName,
                             @RequestParam String password,
                             HttpServletRequest request,
                             HttpServletResponse response) {

        if (StringUtils.isBlank(userName) ||
                StringUtils.isBlank(password)) {
            return JsonResult.errorMsg("用户名或密码不能为空");
        }


        if (password.length() < 6){
            return JsonResult.errorMsg("密码长度不能少于6位");
        }

        loginService.register(userName, password);

        String token = UUID.randomUUID().toString().trim();
        redis.set("REDIS_TOKEN:" + userName, token);

        List<String> userLogin = new ArrayList<>();
        userLogin.add(userName);
        userLogin.add(token);

        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(userLogin),true);

        return JsonResult.ok(userName);
    }

    @ApiOperation(value = "用户登录",notes = "查询用户信息是否存在于数据库",httpMethod = "POST")
    @PostMapping("/login")
    public JsonResult login(@RequestParam String userName,
                            @RequestParam String password,
                            HttpServletRequest request,
                            HttpServletResponse response) {

        if (StringUtils.isBlank(userName) ||
                StringUtils.isBlank(password)) {
            return JsonResult.errorMsg("用户名或密码不能为空");
        }

        User userResult = loginService.login(userName, password);

        if (userResult == null){
            return  JsonResult.errorMsg("用户名或密码输入错误");
        }

        String token = UUID.randomUUID().toString().trim();
        redis.set("REDIS_TOKEN:" + userName, token);

        List<String> userLogin = new ArrayList<>();
        userLogin.add(userName);
        userLogin.add(token);

        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(userLogin),true);

        userResult.setPassword("******");

        return JsonResult.ok(userResult);
    }

}
