package com.artbyte.blog.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "likes")
public class Likes {

    @Id
    private String id;
    private String userId;
    private String blogId;
    private Instant createAt;
}
