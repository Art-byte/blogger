package com.artbyte.blog.service;

import com.artbyte.blog.model.Blog;
import com.artbyte.blog.model.Comment;

import java.util.List;

public interface BlogService {

    List<Blog> getAllBlogs();
    Blog getBlogById(String id);
    Blog createBlog(Blog blog);
    Blog updateBlog(String id, Blog blog);
    void deleteBlog(String id);
    void addLike(String id, Long number);
}
