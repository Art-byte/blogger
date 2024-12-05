package com.artbyte.blog.service.impl.impl;

import com.artbyte.blog.enums.BlogsEnum;
import com.artbyte.blog.exception.BlogException;
import com.artbyte.blog.model.Blog;
import com.artbyte.blog.repository.BlogRepository;
import com.artbyte.blog.service.impl.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private static final Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);

    public BlogServiceImpl(BlogRepository blogRepository){
        this.blogRepository = blogRepository;
    }

    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
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
    public void addLike(String id, Long number) {
        Blog blog = getBlogById(id);
        Long actualLikes = blog.getLikes();
        if(number == 1){
            actualLikes = actualLikes + 1;
        } else {
            actualLikes = actualLikes - 1;
        }
        blog.setLikes(actualLikes);
        blogRepository.save(blog);
    }
}
