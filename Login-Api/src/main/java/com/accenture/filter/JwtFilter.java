package com.accenture.filter;

import com.accenture.utils.JsonResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

@WebFilter(urlPatterns = "/jwt/*")
@Slf4j
public class JwtFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String userName = httpServletRequest.getHeader("userName");
        String token = httpServletRequest.getHeader("token");

        if (StringUtils.isBlank(userName) || StringUtils.isBlank(token)) {
            log.error("无访问权限");
            ObjectMapper objectMapper = new ObjectMapper();
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.setCharacterEncoding(Charset.defaultCharset().name());
            httpServletResponse.getWriter().print(objectMapper.writeValueAsString(JsonResult.errorMsg("无访问权限")));
            return;
        }

        log.info("有权限访问");
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }


}
