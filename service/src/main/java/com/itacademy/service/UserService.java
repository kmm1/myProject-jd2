package com.itacademy.service;

import com.itacademy.entity.SystemUser;
import com.itacademy.service.common.BaseService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends BaseService<SystemUser>, UserDetailsService {

    SystemUser findById(Long id);

    Long save(SystemUser user);

    List<SystemUser> findAll();

    SystemUser findOneUserByName(String name);

    List<SystemUser> findOneUserByName2(String name);

    void addExistingRoleToExistingUser(Long roleId, Long userId);
}

