package com.itacademy.service;

import com.itacademy.entity.Role;
import com.itacademy.entity.SystemUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class UserServiceTest extends BaseTest {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Test
    public void testSaveUser() {
        SystemUser user = new SystemUser();
        user.setName("testgggName");
        user.setEmail("testgggName@gmail.com");
        user.setPassword("test");
        user.setRegistrationDate(LocalDateTime.now());
        Long userId = userService.save(user);
        SystemUser user1 = userService.findById(userId);
        assertEquals(user1.getName(), "testgggName");
        assertEquals(user1.getEmail(), "testgggName@gmail.com");
        assertEquals(user1.getPassword(), "test");
        assertThat(user1.getRegistrationDate(), notNullValue());
    }

    @Test
    public void testGetUserById() {
        SystemUser user = new SystemUser();
        user.setName("name");
        user.setEmail("emvvail111@gmail.com");
        Long userId = userService.save(user);
        SystemUser user1 = userService.findById(userId);
        assertThat(user1, notNullValue());
    }

    @Test
    public void testFindAllUsers() {
        SystemUser user1 = new SystemUser();
        SystemUser user2 = new SystemUser();
        user1.setName("anotherNameooo");
        user1.setEmail("o@gmail.com");
        user2.setName("edc");
        user2.setEmail("edc@gmail.com");
        userService.save(user1);
        userService.save(user2);
        List<SystemUser> results = userService.findAll();
        assertEquals(results.size(), 2);
    }

    @Test
    public void testFindOneUserByName() {
        SystemUser user1 = new SystemUser();
        user1.setName("c");
        user1.setEmail("c@gmail.com");
        userService.save(user1);
        SystemUser result = userService.findOneUserByName("c");
        assertThat(result, notNullValue());
    }

    @Test
    public void testFindOneUserByName2() {
        SystemUser user1 = new SystemUser();
        user1.setName("fd");
        user1.setEmail("fd@gmail.com");
        userService.save(user1);
        List<SystemUser> result = userService.findOneUserByName2("fd");
        assertThat(result, notNullValue());
    }

    @Test
    public void testAddExistingRoleToExistingUser() {
        Role role = new Role();
        role.setName("ADMIN");
        SystemUser user1 = new SystemUser();
        user1.setName("vvv");
        user1.setEmail("vvv@gmail.com");
        Long userId = userService.save(user1);
        user1.getRoles().add(role);
        SystemUser result = userService.findById(userId);
        assertThat(result.getRoles(), notNullValue());
    }
}

