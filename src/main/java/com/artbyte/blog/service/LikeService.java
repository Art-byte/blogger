package com.artbyte.blog.service;

import com.artbyte.blog.model.Likes;

import java.util.List;

public interface LikeService {
    List<Likes> getAllUsersFromBlog(String blogId);
}
