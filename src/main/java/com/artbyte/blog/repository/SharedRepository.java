package com.artbyte.blog.repository;

import com.artbyte.blog.model.Shared;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SharedRepository extends MongoRepository<Shared, String> {
}
