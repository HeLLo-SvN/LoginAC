package com.accenture.controller;

import com.accenture.cache.RedisCache;
import com.accenture.pojo.Jwt;
import com.accenture.utils.JsonResult;
import com.accenture.utils.JsonUtils;
import com.accenture.utils.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("jwt")
public class JwtController {

//    @Autowired
//    private RedisUtils redis;
    @Autowired
    private RedisCache redis;
    @Autowired
    private JwtUtils jwt;

    @PostMapping("/refresh")
    public JsonResult refreshJwtToken(@RequestParam String token) {

        Jwt jwtInfo = redis.autoCacheManager(token, null);
//        Jwt jwtInfo = (Jwt) JsonUtils.jsonToPojo(redis.get(token),Jwt.class);

        if (jwtInfo == null) {
            return JsonResult.errorMsg("访问权限已过期,请重新登录");
        }

        jwtInfo.setToken(jwt.createJwtToken(jwtInfo.getUserName()));
        jwtInfo.setRefreshToken(UUID.randomUUID().toString());

        redis.autoCacheManager(jwtInfo.getRefreshToken(), jwtInfo);
//        redis.set(jwtInfo.getRefreshToken(), JsonUtils.objectToJson(jwtInfo), 60 * 60);

        return JsonResult.ok(jwtInfo);
    }

    @GetMapping("/verify")
    public JsonResult verifyJwtToken(@RequestParam String userName,
                                     @RequestParam String token) {

        return jwt.verifyJwtToken(userName, token) ? JsonResult.ok("验证成功") : JsonResult.errorMsg("验证失败");
    }

}
