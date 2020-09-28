package com.jiayaxing.oauth2Authorization.dao;

import com.jiayaxing.oauth2Authorization.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDao extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    List<UserEntity> findByRole(String role);

    @Query(value = "SELECT * FROM user WHERE role=?1 and delete_flag=?2", nativeQuery = true)
    List<UserEntity> findByRoleAndDeleteFlag1(String role, Integer deleteFlag);

    @Query(value = "SELECT * FROM user WHERE delete_flag=?1", nativeQuery = true)
    List<UserEntity> findAllByDeleteFlag(Integer deleteFlag);

    @Query(value = "SELECT username FROM user WHERE id=?1", nativeQuery = true)
    String findByID(long userId);

}
