package com.jiayaxing.oauth2Resource.bean;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//无权限，禁止访问
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String msg = null;
        Throwable cause = e.getCause();
        if(cause instanceof InvalidTokenException) {
            msg = "身份验证失败,无效的令牌";//无效token
        }else{
            msg = "身份验证失败,未找到令牌";//未携带token
        }
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(401);
        httpServletResponse.getWriter().print("{\"code\":\"1\",\"msg\":\""+msg+"\"}");
    }
}
