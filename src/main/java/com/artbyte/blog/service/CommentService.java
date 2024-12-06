package com.artbyte.blog.service;

import com.artbyte.blog.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getAllCommentsFromBlog(String blogId);
    Comment getCommentById(String id);
    Comment createComment(Comment comment);
    void deleteComment(String id);
}
