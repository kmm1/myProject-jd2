package com.itacademy.service;

import com.itacademy.entity.Message;
import com.itacademy.entity.SystemUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class MessageServiceTest extends BaseTest {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    @Test
    public void testSaveMessage() {
        SystemUser user1 = new SystemUser();
        SystemUser user2 = new SystemUser();
        user1.setName("zx");
        user1.setEmail("zx@gmail.com");
        user2.setName("zxzx");
        user2.setEmail("zxzx@gmail.com");
        userService.save(user1);
        userService.save(user2);
        Message message = new Message();
        message.setCreationDate(LocalDateTime.now());
        message.setText("Hello!");
        message.setUserReceiver(user1);
        message.setUserSender(user2);
        Long messageId = messageService.save(message);
        Message message1 = messageService.findById(messageId);
        assertEquals(message1.getText(), "Hello!");
        assertThat(message1.getCreationDate(), notNullValue());

    }

    @Test
    public void testGetMessageById() {
        Message message = new Message();
        Long messageId = messageService.save(message);
        Message message1 = messageService.findById(messageId);
        assertThat(message1, notNullValue());
        System.out.println(message1);
    }

    @Test
    public void testChatByTwoUsers() {
        SystemUser user1 = new SystemUser();
        user1.setName("t");
        user1.setEmail("t@gmail.com");
        SystemUser user2 = new SystemUser();
        user2.setName("tt");
        user2.setEmail("tt@gmail.com");
        Long userId1 = userService.save(user1);
        Long userId2 = userService.save(user2);
        Message message = new Message();
        message.setText("Hello!");
        message.setUserReceiver(user1);
        message.setUserSender(user2);
        List<Message> results = messageService.chatByTwoUsers(userId1, userId2);
        assertThat(results, notNullValue());
    }

    @Test
    public void testNames() {
        SystemUser user1 = new SystemUser();
        SystemUser user2 = new SystemUser();
        user1.setName("u");
        user1.setEmail("u@gmail.com");
        user2.setName("uu");
        user1.setEmail("uu@gmail.com");
        Long userId1 = userService.save(user1);
        Message message = new Message();
        message.setText("Hello!");
        message.setUserReceiver(user1);
        message.setUserSender(user2);
        List<String> result = messageService.names(userId1, "u");
        assertThat(result, notNullValue());
    }
}