package com.jiayaxing.oauth2Authorization.bean;

import com.jiayaxing.oauth2Authorization.entity.UserEntity;
import com.jiayaxing.oauth2Authorization.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class SecurityUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         UserEntity userEntity = userService.findByUsername(username);
        String password = userEntity.getEncodePassword();//这个加密是在注册时加密的，这里实际只用查询数据库中已经加密好的密码即可
        String role = userEntity.getRole();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(username).append(",").append(userEntity.getUuid());

        Collection<GrantedAuthority> grantedAuthorities =  AuthorityUtils.commaSeparatedStringToAuthorityList(role);
        UserDetails userDetails = User.withUsername(stringBuilder.toString())
                                .password(password)
                                .authorities(grantedAuthorities)
                                .disabled(userEntity.getDeleteFlag()==1?true:false)// 账户失效配置，这里是不失效，即账户没被删除
                                .accountLocked(false)//账户被锁定配置，这里是没被锁定
                                .accountExpired(false)// 账户过期配置，这里是没有过期
                                .credentialsExpired(false)//凭证过期配置，这里是没有过期
                                .build();


        return userDetails;

    }
}
