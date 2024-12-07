package com.artbyte.blog.repository;

import com.artbyte.blog.model.Blog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends MongoRepository<Blog, String> {
    List<Blog> findAllByAuthorId(String authorId);
}
