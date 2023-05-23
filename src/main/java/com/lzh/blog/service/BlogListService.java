package com.lzh.blog.service;

import com.lzh.blog.entity.Blog;
import com.lzh.blog.entity.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogListService {

    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable);
    Page<Blog> searchBlog(Pageable pageable, BlogQuery blog);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);

    List<Blog> listTopBlog(Integer size);

    Page<Blog> listBlog(String query, Pageable pageable);

    Blog getConvert(Long id);

    Page<Blog> listBlog(Long tagId, Pageable pageable);

    Map<String, List<Blog>> archives();

    Long count();
}
