package com.jiayaxing.oauth2Resource.controller;

import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class User {

    @PostMapping("login")
    public Object login(@RequestParam String username, @RequestParam String password){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("client_id", "c1");
        param.add("client_secret", "secret");
        param.add("grant_type", "password");
        param.add("username", username);
        param.add("password", password);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(param, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity("http://127.0.0.1:8080/oauth/token", request , Map.class);
        Map result = response.getBody();

        HashMap<String, Object> returnMap = new HashMap<>();
        returnMap.put("code",0);
        returnMap.put("data",result.get("access_token"));
        returnMap.put("msg","登录成功");
        return  returnMap;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "getUserInfo")
    public Object getUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = authentication.getPrincipal().toString();
        String[] split = principal.split(",");

        List<String> authorityList = new ArrayList<>();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>)authentication.getAuthorities();
        for (int i = 0; i < authorities.size(); i++) {
            GrantedAuthority grantedAuthority = authorities.get(i);
            String authority = grantedAuthority.getAuthority();
            System.out.println(authority);
            authorityList.add(authority.replace("ROLE_",""));
        }

        HashMap<String, Object> returnMap = new HashMap<>();
        returnMap.put("username",split[0]);
        returnMap.put("userid",split[1]);
        returnMap.put("roles",authorityList);
        return returnMap;
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/getUserList")
    @ResponseBody
    public Object getUserList(HttpServletRequest httpRequest,String role) throws  Exception{

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://127.0.0.1:8080/userController/getUserList");
        if(!StringUtils.isEmpty(role)){
            stringBuilder.append("?role="+role);
        }
        String url = stringBuilder.toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String token = httpRequest.getHeader("Authorization");
        headers.add("Authorization",token);
        HttpEntity<String> request = new HttpEntity<String>(null, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET,request, List.class);
        List result = response.getBody();

        return result;
    }

    @PreAuthorize("hasAnyRole('admin')")
    @PostMapping(value = "saveUserInfo")
    public Object saveUserInfo(@RequestParam String username, @RequestParam String password, @RequestParam String role,HttpServletRequest httpRequest) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String token = httpRequest.getHeader("Authorization");
        headers.add("Authorization",token);
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("username", username);
        param.add("password", password);
        param.add("role", role);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(param, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity("http://127.0.0.1:8080/userController/saveUserInfo", request , Map.class);
        Map result = response.getBody();
        return result;
    }
}
