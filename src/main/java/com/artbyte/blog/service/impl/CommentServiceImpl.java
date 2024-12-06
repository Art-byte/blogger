package com.artbyte.blog.service.impl;

import com.artbyte.blog.exception.CommentException;
import com.artbyte.blog.model.Comment;
import com.artbyte.blog.repository.CommentRepository;
import com.artbyte.blog.service.CommentService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getAllCommentsFromBlog(String blogId) {
        return commentRepository.findAllCommentsByBlogId(blogId);
    }

    @Override
    public Comment getCommentById(String id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentException("Comment not found"));
    }

    @Override
    public Comment createComment(Comment comment) {
        Comment newComment = new Comment();
        newComment.setComment(comment.getComment());
        newComment.setAuthorId(comment.getAuthorId());
        newComment.setBlogId(comment.getBlogId());
        newComment.setCreateAt(Instant.now());
        return  commentRepository.save(newComment);
    }

    @Override
    public void deleteComment(String id) {
        if(commentRepository.existsById(id)){
            commentRepository.deleteById(id);
        }
    }
}
