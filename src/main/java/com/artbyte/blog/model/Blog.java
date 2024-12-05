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
@Document(collection = "blogs")
public class Blog {
    @Id
    private String id;
    private String title;
    private String content;
    private Long comments;
    private Long likes;
    private Long shares;
    private List<String> files;
    private Instant createAt;
    private String status;
    private String authorId;
}


