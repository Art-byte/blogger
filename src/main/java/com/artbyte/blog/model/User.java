package com.artbyte.blog.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String lastName;
    private String status;
    private Instant createAt;
    private List<String> socialMedia;

    @DBRef
    private List<Blog> blogsCreated;
    @DBRef
    private List<Blog> blogsLiked;
    @DBRef
    private List<Blog> blogsShared;

}
