package com.accenture.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtUtils {

    public String createJwtToken(String userName) {

        Date now = new Date();

        return JWT.create()
                .withIssuer("SJH")
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + 60000))
                .withClaim("userName", userName)
                .sign(Algorithm.HMAC256("algorithm"));

    }

    public boolean verifyJwtToken(String userName, String token) {

        try {
            JWTVerifier verifier =
                    JWT.require(Algorithm.HMAC256("algorithm"))
                            .withIssuer("SJH")
                            .withClaim("userName", userName)
                            .build();

            verifier.verify(token);
            return true;
        } catch (Exception e) {
            log.error("TOKEN VERIFY FAILED", e);
            return false;
        }

    }

}
