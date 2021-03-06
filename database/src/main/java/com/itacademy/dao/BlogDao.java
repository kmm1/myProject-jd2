package com.itacademy.dao;

import com.itacademy.dao.common.BaseDao;
import com.itacademy.entity.Blog;

import java.util.List;


public interface BlogDao extends BaseDao<Blog> {

    Long createBlog(Long categoryId, Long userId, String title, String content);

    void addExistingBlogToExistingCategory(Long categoryId, Long blogId);

    List<Blog> findAllUsersBlogs(Long userId, Integer limit, Integer offset);

    List<Blog> findAllBlogsByCategory(Long categoryId);

    void deliteExistingBlogFromExistingCategory(Long categoryId, Long blogId);

    Integer countUserBlogs(Long userId);
}

