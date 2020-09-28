package com.jiayaxing.oauth2Resource.controller;

import com.jiayaxing.oauth2Resource.UserFeignClient.UserFeignClient;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "testContrller")
public class TestContrller {

    @Resource
    private UserFeignClient userFeignClient;

    @GetMapping(value = "test")
    public String test(){
        return "aaa";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "test1")
    public String test1(){
        return "aaa";
    }

    @PreAuthorize("hasRole('user')")
    @GetMapping(value = "test2")
    public String test2(){
        return "bbb";
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping(value = "test3")
    public String test3(){
        return "ccc";
    }

    @PreAuthorize(value = "#oauth2.hasScope('all')")
    @GetMapping(value = "test4")
    public String test4(){
        return "aaa";
    }

    @PreAuthorize(value = "#oauth2.hasAnyScope('servera','serverb','serverc')")
    @GetMapping(value = "test5")
    public String test5(){
        return "aaa";
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping(value = "getUserInfo")
    public Object getUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getPrincipal().toString();




        List<GrantedAuthority> authorities = (List<GrantedAuthority>)authentication.getAuthorities();
        for (int i = 0; i < authorities.size(); i++) {
            GrantedAuthority grantedAuthority = authorities.get(i);
            String authority = grantedAuthority.getAuthority();
            System.out.println(authority);
        }
        return authentication;
    }


    @PreAuthorize("hasRole('admin')")
    @GetMapping("/getUserList")
    @ResponseBody
    public Object getUserList(String role) throws  Exception{

        List userList = userFeignClient.getUserList(role);

        return userList;
    }
}
