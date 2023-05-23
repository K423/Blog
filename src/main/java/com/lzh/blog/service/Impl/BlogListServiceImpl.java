package com.lzh.blog.service.Impl;

import com.lzh.blog.dao.BlogRepository;
import com.lzh.blog.entity.Blog;
import com.lzh.blog.entity.BlogQuery;
import com.lzh.blog.entity.Type;
import com.lzh.blog.service.BlogListService;
import com.lzh.blog.utils.MarkDownUtils;
import jakarta.persistence.criteria.*;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BlogListServiceImpl implements BlogListService {

    @Autowired
    BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).get();
    }

    @Override /*条件查询*/
    public Page<Blog> listBlog(Pageable pageable) {
//        return blogRepository.findAll(new Specification<Blog>() {
//            @Override
//            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                List<Predicate> predicates = new ArrayList<>();
//                if (!"".equals(blog.getTitle()) && blog.getTitle() != null){
//                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
//                }
//                if (blog.getType().getId() != null){
//                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getType().getId()));
//                }
//                if (blog.isRecommended()){
//                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.isRecommended()));
//                }
//                query.where(predicates.toArray(new Predicate[predicates.size()]));
//                return null;
//            }
//        }, pageable);
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> searchBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null){
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
                }
                if (blog.getTypeId() != null){
//                    System.out.println(/*this is start*/);
//                    System.out.println(blog.getTypeId());
//                    System.out.println(/*this is end*/);
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommended()){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommended"), blog.isRecommended()));
                }
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId() == null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else {
            Long index = blog.getId();
            Blog b = blogRepository.findById(index).get();
            blog.setCreateTime(b.getCreateTime());
            blog.setViews(b.getViews());
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.findById(id).get();
        if (b == null){
            try {
                throw new NotFoundException("不存在该博客");
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
        BeanUtils.copyProperties(blog, b);
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }
    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public List<Blog> listTopBlog(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }

    @Transactional
    @Override
    public Blog getConvert(Long id) {
        Blog blog = blogRepository.findById(id).get();
        if (blog == null){
            try {
                throw new NotFoundException("不存在该博客");
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        b.setContent(MarkDownUtils.markdownExtensions(content));
        //
        blogRepository.updateViews(id);
        return b;
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");
                return criteriaBuilder.equal(join.get("id"), tagId);
            }
        }, pageable);
    }

    @Override
    public Map<String, List<Blog>> archives() {
        List<String> years = blogRepository.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years){
            map.put(year,blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long count() {
        return blogRepository.count();
    }
}
