package com.itacademy.service;


import com.itacademy.entity.Blog;
import com.itacademy.service.common.BaseService;

import java.util.List;

public interface BlogService extends BaseService<Blog> {

    Blog findById(Long id);

    Long save(Blog blog);

    List<Blog> findAllUsersBlogs(Long userId, Integer limit, Integer offset);

    void delete(Blog blog);

    void update(Blog blog);

    void addExistingBlogToExistingCategory(Long categoryId, Long blogId);

    void deliteExistingBlogFromExistingCategory(Long categoryId, Long blogId);

    List<Blog> findAllBlogsByCategory(Long categoryId);

    Integer countUserBlogs(Long userId);


}
