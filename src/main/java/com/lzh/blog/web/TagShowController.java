package com.lzh.blog.web;

import com.lzh.blog.entity.Tag;
import com.lzh.blog.service.BlogListService;
import com.lzh.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogListService blogListService;

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                       @PathVariable Long id, Model model){
        List<Tag> tags = tagService.listTopTag(999);
        if (id == -1){
            id = tags.get(0).getId();
        }
        model.addAttribute("tags",tags);
        model.addAttribute("page", blogListService.listBlog(id, pageable));
        model.addAttribute("activeId", id);
        model.addAttribute("newBlogs",blogListService.listTopBlog(3));
        return "tags";
    }
}
