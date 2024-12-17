package com.artbyte.blog.controller;

import com.artbyte.blog.mappers.Mappers;
import com.artbyte.blog.mappers.vo.BlogMapper;
import com.artbyte.blog.model.Blog;
import com.artbyte.blog.model.Likes;
import com.artbyte.blog.model.User;
import com.artbyte.blog.service.BlogService;
import com.artbyte.blog.service.LikeService;
import com.artbyte.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;
    private final LikeService likeService;
    private final UserService userService;
    private final Mappers mapperService;

    private static final Logger logger = LoggerFactory.getLogger(BlogController.class);

    @GetMapping("/all")
    public ResponseEntity<List<BlogMapper>> getAllBlogs(){
        try{
            List<Blog> blogList = blogService.getAllBlogs();
            if(blogList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<BlogMapper> finalList = mapperService.convertBlogList(blogList);
            return new ResponseEntity<>(finalList, HttpStatus.OK);
        }catch (DataAccessException e){
            logger.error("Error => {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/blogSelect/{blogId}")
    public ResponseEntity<BlogMapper> getBlogById(@PathVariable String blogId){
        try{
            Blog blog = blogService.getBlogById(blogId);
            BlogMapper blogMapper = mapperService.convertBlogBody(blog);
            return new ResponseEntity<>(blogMapper, HttpStatus.OK);
        }catch (DataAccessException e){
            logger.error("Error => {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/myBlogs/{authorId}")
    public ResponseEntity<List<BlogMapper>> getAllMyBLogs(@PathVariable String authorId){
        try{
            List<Blog> blogList = blogService.getAllBlogsFromUser(authorId);
            if(blogList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<BlogMapper> finalList = mapperService.convertBlogList(blogList);
            return new ResponseEntity<>(finalList, HttpStatus.OK);
        }catch (DataAccessException e){
            logger.error("Error => {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Esta es la miniatura con la que veremos solo el nombre de los usuarios que han dado like
    @GetMapping("/users_likes/{blogId}")
    public ResponseEntity<List<String>> getAllUsersLikedBlog(@PathVariable String blogId){
        try{
            if(blogId == null || blogId.isEmpty()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            //Obtenemos los likes asociados al blog
            List<Likes> likes = likeService.getAllUsersFromBlog(blogId);
            if(likes == null || likes.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            //Transformamos la lista de usernames
            List<String> usernameList = likes.stream()
                    .map(like -> userService.findUserById(like.getUserId()))
                    .filter(Objects::nonNull)
                    .map(User::getUsername)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(usernameList, HttpStatus.OK);
        }catch (DataAccessException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addOrRemoveLike")
    public ResponseEntity<Void> addOrRemoveLikes(@RequestParam String blogId, @RequestParam int key){
        blogService.addLike(blogId, key);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog){
        try{
            Blog blogObj = blogService.createBlog(blog);
            return new ResponseEntity<>(blogObj, HttpStatus.CREATED);
        }catch (DataAccessException e){
            logger.error("Database error: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
