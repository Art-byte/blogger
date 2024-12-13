package com.artbyte.blog.mappers.vo;

import com.artbyte.blog.service.BlogService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogMapper {

    private String id;
    private String title;
    private String content;
    private Long comments;
    private Long likes;
    private Long shares;
    private List<String> files;
    private Instant createAt;
    private String author;
    private List<CommentsMapper> commentList;
}
