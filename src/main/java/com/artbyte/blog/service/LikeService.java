package com.artbyte.blog.service;

import com.artbyte.blog.model.Likes;

import java.util.List;

public interface LikeService {
    Long getTotalLikes(String blogId);
    List<String> getAllUsersFromBlog(String blogId);
}
