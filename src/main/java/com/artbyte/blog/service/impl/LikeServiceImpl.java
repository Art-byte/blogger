package com.artbyte.blog.service.impl;

import com.artbyte.blog.model.Likes;
import com.artbyte.blog.repository.LikesRepository;
import com.artbyte.blog.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikesRepository likesRepository;

    @Override
    public List<Likes> getAllUsersFromBlog(String blogId) {
        return likesRepository.findUserIdByBlogId(blogId);
    }
}
