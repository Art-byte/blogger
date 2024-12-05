package com.artbyte.blog.repository;

import com.artbyte.blog.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    //Traer todos los comentarios de un blog en especifico
    List<Comment> findAllCommentsByBlogId(String id);
}
