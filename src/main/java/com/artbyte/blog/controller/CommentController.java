package com.artbyte.blog.controller;

import com.artbyte.blog.exception.CommentException;
import com.artbyte.blog.model.Comment;
import com.artbyte.blog.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin("http://localhost:4200/")
public class CommentController {

    public final CommentService commentService;
    public static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable String blogId){
        try{
            List<Comment> comments = commentService.getAllCommentsFromBlog(blogId);
            if(comments.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(comments, HttpStatus.OK);
        }catch (DataAccessException e){
            logger.error("Error => {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getComment/{commentId}")
    public ResponseEntity<?> getCommentById(@PathVariable String commentId) {
        try {
            Comment comment = commentService.getCommentById(commentId);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } catch (CommentException e) {
            logger.error("Error: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DataAccessException e) {
            logger.error("Database error: {}", e.getMessage());
            return new ResponseEntity<>("Database error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage());
            return new ResponseEntity<>("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment){
        try{
            Comment comment1 = commentService.createComment(comment);
            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        }catch (DataAccessException e){
            logger.error("Database error: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable String commentId){
        try{
            commentService.deleteComment(commentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CommentException e){
            logger.error("Error => {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (DataAccessException e){
            logger.error("Database error: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
