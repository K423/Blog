package com.lzh.blog.web;

import com.lzh.blog.service.BlogListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArchiveShowController {

    @Autowired
    private BlogListService blogListService;

    @GetMapping("/archives")
    public String archive(Model model){
        model.addAttribute("archiveMap",blogListService.archives());
        model.addAttribute("archiveCount",blogListService.count());
        model.addAttribute("newBlogs",blogListService.listTopBlog(3));
        return "archives";
    }
}
