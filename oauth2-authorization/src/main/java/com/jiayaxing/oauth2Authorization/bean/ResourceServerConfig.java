package com.jiayaxing.oauth2Authorization.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;


@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启方法注解
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

        //配置本地认证服务器
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);//调用本地jwt转化器验证令牌的服务

        resources.resourceId("res2")//定义本资源的id，接收到请求然后校验token后会验证客户端是否有访问本资源的权限
                .tokenServices(tokenServices)
                .stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //.antMatchers("/r/**").hasAnyRole("p1")
//                .antMatchers("/loginView.html","/login","/css/**","/js/**","/images/**").permitAll()
//                .antMatchers("/error","/","/index1.html","/index.html","/favicon.ico").permitAll()
//                .antMatchers("/**").access("#oauth2.hasAnyScope('all')")
//                .antMatchers("/**").access("#oauth2.hasAnyScope('all')")
//                .anyRequest().authenticated()
                .anyRequest().permitAll()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//无状态模式 不会创建session  默认状态
    }





}
