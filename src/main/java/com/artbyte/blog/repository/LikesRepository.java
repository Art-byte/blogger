package com.artbyte.blog.repository;

import com.artbyte.blog.model.Likes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepository extends MongoRepository<Likes, String> {

    //Todos los id de usuario que dieron like al blog
    List<Likes> findUserIdByBlogId(String blogId);
}
