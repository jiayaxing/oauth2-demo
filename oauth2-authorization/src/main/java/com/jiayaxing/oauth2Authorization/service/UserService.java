package com.jiayaxing.oauth2Authorization.service;


import com.alibaba.fastjson.JSONObject;
import com.jiayaxing.oauth2Authorization.dao.UserDao;
import com.jiayaxing.oauth2Authorization.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;

    public UserEntity findByUsername(String username){

        UserEntity userEntity = null;
        try {
            userEntity = userDao.findByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userEntity;
    }

    public List<UserEntity> findByRole(String role){
        String roleAll = null;
        if(role!=null){
            roleAll="ROLE_"+role;
        }
        List<UserEntity>  users = null;
        if(roleAll!=null){
            users = userDao.findByRoleAndDeleteFlag1(roleAll,0);
        }else {
            users = userDao.findAllByDeleteFlag(0);
        }
        return users;
    }

    @Transactional
    public JSONObject saveUserInfo(String username, String password, String role)  throws Exception {

        JSONObject jsonObject = new JSONObject();

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        List<UserEntity> all = userDao.findAll(Example.of(userEntity));
        if(all!=null&&all.size()!=0){
            UserEntity userEntity1 = all.get(0);
            userEntity1.setEncodePassword(passwordEncoder.encode(password));
            userEntity1.setPassword(password);
            userEntity1.setRole("ROLE_"+role);
            userEntity1.setUuid(UUID.randomUUID().toString().replaceAll("-",""));
            userEntity1.setDeleteFlag(0);
            userEntity1.setGroupName("备用组");
            userDao.save(userEntity1);
            jsonObject.put("msg", username+"修改成功");

        }else {
            userEntity.setEncodePassword(passwordEncoder.encode(password));
            userEntity.setPassword(password);
            userEntity.setRole("ROLE_"+role);
            userEntity.setUuid(UUID.randomUUID().toString().replaceAll("-",""));
            userEntity.setDeleteFlag(0);
            userEntity.setGroupName("备用组");
            userDao.save(userEntity);
            jsonObject.put("msg", username+"新增成功");
        }

        jsonObject.put("state", 0);
        return jsonObject;
    }
}
