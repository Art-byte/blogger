package com.artbyte.blog.mappers;

import com.artbyte.blog.mappers.vo.BlogMapper;
import com.artbyte.blog.mappers.vo.CommentsMapper;
import com.artbyte.blog.model.Blog;
import com.artbyte.blog.model.Comment;
import com.artbyte.blog.model.User;
import com.artbyte.blog.service.BlogService;
import com.artbyte.blog.service.CommentService;
import com.artbyte.blog.service.UserService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Mappers {

    private final BlogService blogService;
    private final CommentService commentService;
    private final UserService userService;

    public Mappers(BlogService blogService, CommentService commentService, UserService userService) {
        this.blogService = blogService;
        this.commentService = commentService;
        this.userService = userService;
    }

    public List<BlogMapper> convertBlogList(List<Blog> blogList){
        List<BlogMapper> finalList = new ArrayList<>();
        for(Blog blog : blogList){
            BlogMapper blogM = convertBlogBody(blog);
            finalList.add(blogM);
        }
        return finalList;
    }


    public BlogMapper convertBlogBody(Blog blog) {
        User user = userService.findUserById(blog.getAuthorId());

        BlogMapper blogMapper = new BlogMapper();
        mapBlogToBlogMapper(blog, user, blogMapper);
        blogMapper.setCommentList(mapCommentsFromBlog(blog.getId()));
        return blogMapper;
    }

    // Método auxiliar para mapear los atributos del Blog a BlogMapper
    private void mapBlogToBlogMapper(Blog blogFind, User user, BlogMapper blogMapper) {
        blogMapper.setId(blogFind.getId());
        blogMapper.setTitle(blogFind.getTitle());
        blogMapper.setContent(blogFind.getContent());
        blogMapper.setComments(blogFind.getComments());
        blogMapper.setLikes(blogFind.getLikes());
        blogMapper.setShares(blogFind.getShares());
        blogMapper.setFiles(blogFind.getFiles());
        blogMapper.setCreateAt(blogFind.getCreateAt());
        blogMapper.setAuthor(user.getUsername());
    }


    public List<CommentsMapper> mapCommentsFromBlog(String blogId) {
        List<Comment> list = commentService.getAllCommentsFromBlog(blogId);
        Set<String> authorIds = list.stream()
                .map(Comment::getAuthorId)
                .collect(Collectors.toSet());

        // Mapa para almacenar los usuarios obtenidos, para evitar consultas duplicadas
        Map<String, User> usersMap = new HashMap<>();

        for (String authorId : authorIds) {
            if (!usersMap.containsKey(authorId)) {
                User user = userService.findUserById(authorId);
                usersMap.put(authorId, user);
            }
        }
        return list.stream()
                .map(com -> {
                    User username = usersMap.get(com.getAuthorId());
                    CommentsMapper comment = new CommentsMapper();
                    comment.setId(com.getId());
                    comment.setComment(com.getComment());
                    comment.setCreateAt(com.getCreateAt());
                    comment.setAuthor(username != null ? username.getUsername() : "Unknown");
                    return comment;
                })
                .sorted(Comparator.comparing(CommentsMapper::getCreateAt))
                .collect(Collectors.toList());
    }

}
