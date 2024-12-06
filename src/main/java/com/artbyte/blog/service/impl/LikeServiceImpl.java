package com.artbyte.blog.service.impl;

import com.artbyte.blog.repository.LikesRepository;
import com.artbyte.blog.service.LikeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikesRepository likesRepository;

    public LikeServiceImpl(LikesRepository likesRepository){
        this.likesRepository = likesRepository;
    }

    @Override
    public Long getTotalLikes(String blogId) {
        return likesRepository.countByBlogId(blogId);
    }

    @Override
    public List<String> getAllUsersFromBlog(String blogId) {
        return likesRepository.findUserIdByBlogId(blogId);
    }
}
