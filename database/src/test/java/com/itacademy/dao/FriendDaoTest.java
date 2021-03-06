package com.itacademy.dao;

import com.itacademy.entity.Friend;
import com.itacademy.entity.SystemUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class FriendDaoTest extends BaseTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private FriendDao friendDao;

    @Test
    public void testSaveFriend() {
        SystemUser sender = new SystemUser();
        sender.setName("Name1");
        sender.setEmail("email1@gmail.com");
        SystemUser reciver = new SystemUser();
        reciver.setName("Name2");
        reciver.setEmail("email2@gmail.com");
        userDao.save(sender);
        userDao.save(reciver);
        Friend friend = new Friend("fri", sender, reciver);
        Long friendId = friendDao.save(friend);
        Friend friend1 = friendDao.findById(friendId);
        assertEquals(friend1.getStatus(), "fri");
        assertEquals(friend1.getUserSender(), sender);
        assertEquals(friend1.getUserReceiver(), reciver);
    }

    @Test
    public void testGetFriendById() {
        Friend friend = new Friend();
        Long friendId = friendDao.save(friend);
        Friend friend1 = friendDao.findById(friendId);
        assertThat(friend1, notNullValue());
    }

    @Test
    public void testUpdateFriend() {
        Friend friend = new Friend();
        friend.setStatus("req");
        Long friendId = friendDao.save(friend);
        Friend friend1 = friendDao.findById(friendId);
        assertThat(friend1, notNullValue());
        friend1.setStatus("fri");
        friendDao.update(friend1);
        assertEquals(friend1.getStatus(), "fri");
    }

    @Test
    public void testDeleteFriend() {
        Friend friend = new Friend();
        Long friendId = friendDao.save(friend);
        friendDao.delete(friend);
        Friend friend1 = friendDao.findById(friendId);
        assertThat(friend1, nullValue());
    }

    @Test
    public void testFindFriendById() {
        Friend friend1 = new Friend();
        Friend friend2 = new Friend();
        Long friendId1 = friendDao.save(friend1);
        Long friendId2 = friendDao.save(friend2);
        Friend friend = friendDao.findById(friendId1);
        assertThat(friend1, notNullValue());
    }

    @Test
    public void testFindAllFriendsByUserId() {
        SystemUser user1 = new SystemUser();
        SystemUser user2 = new SystemUser();
        SystemUser user3 = new SystemUser();
        user1.setName("111");
        user1.setEmail("111@gmail.com");
        user2.setName("222");
        user2.setEmail("222@gmail.com");
        user3.setName("333");
        user3.setEmail("333@gmail.com");
        userDao.save(user1);
        userDao.save(user2);
        userDao.save(user3);
        Friend friend1 = new Friend("fri", user1, user2);
        Friend friend2 = new Friend("fri", user1, user3);
        friendDao.save(friend1);
        friendDao.save(friend2);
        List<Friend> results = friendDao.findAllFriendsByUserName("111");
        assertEquals(results.size(), 2);
    }

    @Test
    public void testFindAllMyFriendRequestsSent() {
        SystemUser user1 = new SystemUser();
        user1.setName("Name1");
        user1.setEmail("email1@gmail.com");
        SystemUser user2 = new SystemUser();
        SystemUser user3 = new SystemUser();
        user2.setName("Name2");
        user2.setEmail("email2@gmail.com");
        user3.setName("Name3");
        user3.setEmail("email3@gmail.com");
        userDao.save(user1);
        userDao.save(user2);
        userDao.save(user3);
        Friend friend1 = new Friend("req", user1, user2);
        Friend friend2 = new Friend("req", user1, user3);
        friendDao.save(friend1);
        friendDao.save(friend2);
        List<Friend> results = friendDao.findAllMyFriendRequestsSent("Name1");
        assertEquals(results.size(), 2);
    }

    @Test
    public void testFindAllMyFriendRequestsReceived() {
        SystemUser user1 = new SystemUser();
        user1.setName("SomeName");
        user1.setEmail("email111@gmail.com");
        SystemUser user2 = new SystemUser();
        user2.setName("SecondName");
        user2.setEmail("email222@gmail.com");
        SystemUser user3 = new SystemUser();
        user3.setName("thirdName");
        user3.setEmail("email333@gmail.com");
        userDao.save(user1);
        userDao.save(user2);
        userDao.save(user3);
        Friend friend1 = new Friend("req", user1, user2);
        Friend friend2 = new Friend("req", user1, user3);
        friendDao.save(friend1);
        friendDao.save(friend2);
        List<Friend> results = friendDao.findAllMyFriendRequestsResived("SecondName");
        assertEquals(results.size(), 1);
    }

    @Test
    public void testFindOneFriendByUsersNames() {
        SystemUser user1 = new SystemUser();
        user1.setName("Name");
        user1.setEmail("email1@gmail.com");
        SystemUser user2 = new SystemUser();
        user2.setName("secondName");
        user2.setEmail("email2@gmail.com");
        SystemUser user3 = new SystemUser();
        user3.setName("thirdName");
        user3.setEmail("email3@gmail.com");
        userDao.save(user1);
        userDao.save(user2);
        userDao.save(user3);
        Friend friend1 = new Friend("req", user1, user2);
        Friend friend2 = new Friend("req", user1, user3);
        friendDao.save(friend1);
        friendDao.save(friend2);
        Friend result = friendDao.findOneFriendByUsersNames("Name", "secondName");
        System.out.println(result);
        assertEquals(result.getUserSender().getName(), "Name");
    }

    @Test
    public void testFindOneFriendByUsersNames2() {
        SystemUser user1 = new SystemUser();
        user1.setName("Name");
        user1.setEmail("email@gmail.com");
        SystemUser user2 = new SystemUser();
        user2.setName("secondName");
        user2.setEmail("email2@gmail.com");
        SystemUser user3 = new SystemUser();
        user3.setName("thirdName");
        user3.setEmail("email3@gmail.com");
        userDao.save(user1);
        userDao.save(user2);
        userDao.save(user3);
        Friend friend1 = new Friend("fri", user1, user2);
        Friend friend2 = new Friend("fri", user1, user3);
        friendDao.save(friend1);
        friendDao.save(friend2);
        Friend result = friendDao.findOneFriendByUsersNames2("Name", "secondName");
        System.out.println(result);
        assertEquals(result.getUserSender().getName(), "Name");
    }
}