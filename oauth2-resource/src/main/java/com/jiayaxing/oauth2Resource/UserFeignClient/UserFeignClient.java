package com.jiayaxing.oauth2Resource.UserFeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user",url = "http://127.0.0.1:8080")
public interface UserFeignClient {
    @RequestMapping(value = "/userController/getUserList",method = RequestMethod.GET,consumes = "applacation/json")
    List getUserList(@RequestParam("role") String role);


}
