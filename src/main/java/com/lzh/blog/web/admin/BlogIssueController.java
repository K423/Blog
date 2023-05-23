package com.lzh.blog.web.admin;

import com.lzh.blog.entity.Blog;
import com.lzh.blog.entity.User;
import com.lzh.blog.service.BlogListService;
import com.lzh.blog.service.TagService;
import com.lzh.blog.service.TypeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class BlogIssueController {

    @Autowired
    private BlogListService blogListService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/issue")
    public String issue(Model model){

        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("blog", new Blog());
        return "admin/adminIssue";
    }

    @PostMapping("/bloglist") //新增与修改
    public String add(Blog blog, RedirectAttributes attributes, HttpSession session){

        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b = blogListService.saveBlog(blog);
//        if(blog.getId() == null){
//            b = blogListService.saveBlog(blog);
//        }else {
//            b = blogListService.updateBlog(blog.getId(), blog);
//        }
        if (b == null){
            attributes.addFlashAttribute("message", "操作失败");
        }else {
            attributes.addFlashAttribute("message", "操作成功");
        }

        return "redirect:/admin/bloglist";
    }

    @GetMapping("/bloglist/{id}/edit") //单纯跳转携带id
    public String eidtBlog(@PathVariable Long id, Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        Blog blog = blogListService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blogListService.getBlog(id));
        return "admin/editBlog";
    }


//    @PostMapping ("/bloglist/{id}")
//    public String edit(Blog blog, Model model){ //更新操作
//
//        model.addAttribute("types", typeService.listType());
//        model.addAttribute("tags", tagService.listTag());
//        model.addAttribute("blog", new Blog());
//        return "redirect:/admin/bloglist";
//    }
}
