package com.artbyte.blog.mappers.vo;

import com.artbyte.blog.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsMapper {

    private String id;
    private String comment;
    private Instant createAt;
    private String author;

}
