package com.artbyte.blog.service.impl;

import com.artbyte.blog.enums.BlogsEnum;
import com.artbyte.blog.exception.BlogException;
import com.artbyte.blog.model.Blog;
import com.artbyte.blog.repository.BlogRepository;
import com.artbyte.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    @Override
    public List<Blog> getAllBlogsFromUser(String authorId) {
        return blogRepository.findAllByAuthorId(authorId);
    }

    @Override
    public Blog getBlogById(String id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new BlogException("Blog not found"));
    }

    @Override
    public Blog createBlog(Blog blog) {
        Blog newBlog = new Blog();
        newBlog.setTitle(blog.getTitle());
        newBlog.setContent(blog.getContent());
        newBlog.setFiles(blog.getFiles());
        newBlog.setLikes(0L);
        newBlog.setShares(0L);
        newBlog.setCreateAt(Instant.now());
        newBlog.setStatus(BlogsEnum.BLOG_ENABLE.name());
        newBlog.setAuthorId(blog.getAuthorId());
        return blogRepository.save(newBlog);
    }

    @Override
    public Blog updateBlog(String id, Blog blog) {
        Blog blogFind = getBlogById(id);
        blogFind.setTitle(blog.getTitle());
        blogFind.setContent(blog.getContent());
        blogFind.setFiles(blog.getFiles());
        return blogRepository.save(blogFind);
    }

    //Deshabilitamos el blog
    @Override
    public void deleteBlog(String id) {
        Blog blog = getBlogById(id);
        blog.setStatus(BlogsEnum.BLOG_DISABLE.name());
        blogRepository.save(blog);
    }

    //Actualizamos el contador de likes en el blog
    @Override
    public void addLike(String id, int number) {
        Blog blog = getBlogById(id);
        Long actualLikes = blog.getLikes();
        if(number == 1){
            actualLikes = actualLikes + 1;
        } else if(number == -1){
            actualLikes = actualLikes - 1;
        }
        blog.setLikes(actualLikes);
        blogRepository.save(blog);
    }
}
