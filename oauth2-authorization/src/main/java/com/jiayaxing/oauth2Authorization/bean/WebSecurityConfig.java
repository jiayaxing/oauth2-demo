package com.jiayaxing.oauth2Authorization.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/loginView.html").loginProcessingUrl("/login");
//                .and()
//                .authorizeRequests()
//                //.antMatchers("/r/**").hasAnyRole("p1")
//                .antMatchers("/loginView.html","/login","/css/**","/js/**","/images/**").permitAll()
//                .antMatchers("/error","/","/index1.html","/index.html","/favicon.ico").permitAll()
//                .antMatchers("/userController/*").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .csrf().disable();

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }


}
