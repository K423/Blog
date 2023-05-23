package com.lzh.blog.web;

import com.lzh.blog.service.BlogListService;
import com.lzh.blog.service.CommentService;
import com.lzh.blog.service.TagService;
import com.lzh.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private BlogListService blogListService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC)Pageable pageable,
                        Model model){
        model.addAttribute("page",blogListService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(4));
        model.addAttribute("tags",tagService.listTopTag(8));
        model.addAttribute("recommendedBlogs",blogListService.listTopBlog(8));
        model.addAttribute("newBlogs",blogListService.listTopBlog(3));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC)Pageable pageable,
                         @RequestParam String query, Model model){
        model.addAttribute("page",blogListService.listBlog("%" + query + "%", pageable));
        model.addAttribute("query", query);
        model.addAttribute("newBlogs",blogListService.listTopBlog(3));
        return "searchInfo";
    }

    @GetMapping("/blogContent/{id}")
    public String blog(@PathVariable Long id, Model model){
        model.addAttribute("blog", blogListService.getConvert(id));
        model.addAttribute("comments", commentService.listComment(id));
        model.addAttribute("newBlogs",blogListService.listTopBlog(3));
        return "blogContent";
    }

//    @GetMapping("/types")
//    public String type(){
//        return "classify";
//    }

//    @GetMapping("/tags")
//    public String tag(){
//        return "tags";
//    }

//    @GetMapping("/archives")
//    public String archive(){
//        return "archives";
//    }

    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("newBlogs",blogListService.listTopBlog(3));
        return "about";
    }


}
