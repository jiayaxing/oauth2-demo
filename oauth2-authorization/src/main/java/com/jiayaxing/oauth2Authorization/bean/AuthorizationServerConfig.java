package com.jiayaxing.oauth2Authorization.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig  extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private  ClientDetailsService inMemoryClientDetailsService;

    @Autowired
    private  ClientDetailsService JdbcClientDetailsService;





    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //内存模式
//        clients.inMemory()
//                .withClient("c1")
//                .secret(passwordEncoder.encode("secret"))
//                .resourceIds("res1")//该客户端可以访问这些资源
//                .authorizedGrantTypes("authorization_code","password","client_credentials","implicit","refresh_token")//该客户端可以使用这些授权模式
//                .scopes("all")//该客户端可以访问这些scope范围
//                .autoApprove(false)//该客户端是否跳转到授权页面，这里是跳转
//                .redirectUris("https://www.baidu.com/");//验证回调地址

        //数据库模式
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);

    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        //在内存中存储授权码，集群不可用
        //InMemoryAuthorizationCodeServices authorizationCodeServices = new InMemoryAuthorizationCodeServices();

        //在数据库中存储授权码，集群可用
        JdbcAuthorizationCodeServices authorizationCodeServices = new JdbcAuthorizationCodeServices(dataSource);
        return authorizationCodeServices;
    }

    @Bean
    public AuthorizationServerTokenServices tokenServices(){


        DefaultTokenServices tokenServices = new DefaultTokenServices();
        //内存模式
//        tokenServices.setClientDetailsService(inMemoryClientDetailsService);
//        tokenServices.setAccessTokenValiditySeconds(7200);
//        tokenServices.setRefreshTokenValiditySeconds(259200);
        //数据库模式
        tokenServices.setClientDetailsService(JdbcClientDetailsService);

        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore);


        //设置令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter));
        tokenServices.setTokenEnhancer(tokenEnhancerChain);

        return tokenServices;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)//密码模式需要
                .authorizationCodeServices(authorizationCodeServices())//授权码服务
                .tokenServices(tokenServices())//令牌管理服务
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);//端点可以post提交
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()");//  oauth/token_key   jwt非对称加密  获取公钥密钥接口   完全公开
        security.checkTokenAccess("permitAll()");//   oauth/check_token检测token接口 认证后可以访问
        security.allowFormAuthenticationForClients();//允许表单模式申请令牌

        //认证地址
        //http://127.0.0.1:8080/oauth/authorize?response_type=code&client_id=c1&scope=all&redirect_uri=https://www.baidu.com/
    }



}

