package com.jiayaxing.oauth2Authorization;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Oauth2AuthorizationApplicationTests {

    @Test
    public void contextLoads() {
        String secret = BCrypt.hashpw("secret", BCrypt.gensalt());
        System.out.println("secret:"+secret);
    }
}
