package com.jiayaxing.oauth2Authorization.controller;

import com.jiayaxing.oauth2Authorization.entity.UserEntity;
import com.jiayaxing.oauth2Authorization.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping(value = "userController")
public class UserController {

    @Autowired
    private UserService userService;

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


    @PreAuthorize("hasAnyRole('admin')")
    @PostMapping(value = "saveUserInfo")
    public Object saveUserInfo(@RequestParam String username, @RequestParam String password, @RequestParam String role) throws Exception {

        return userService.saveUserInfo(username,password,role);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "getUserInfo")
    public Object getUserInfo(Authentication authentication){
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
    public List<UserEntity> getUserList(String role) throws  Exception{
        return userService.findByRole(role);
    }

}
