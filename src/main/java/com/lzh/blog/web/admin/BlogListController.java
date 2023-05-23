package com.lzh.blog.web.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzh.blog.entity.Blog;
import com.lzh.blog.entity.BlogQuery;
import com.lzh.blog.service.BlogListService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class BlogListController {

    @Autowired
    private BlogListService blogListService;
    @Autowired
    private TypeService typeService;

    @GetMapping("/bloglist")
    public String list(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                       Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogListService.listBlog(pageable));
        System.out.println("/*****START************测试Code**************************************************************************************************/");
//        ObjectMapper mapper = new ObjectMapper();
//        String json1 = null;
//        try {
//            json1 = mapper.writeValueAsString(blogListService.listBlog(pageable));
//        } catch (JsonProcessingException e) {
//            System.out.println("有异常。。。。。。。。。。。。。");
//            e.printStackTrace();
//        }
//        System.out.println(json1);
////        ObjectMapper mapper1 = new ObjectMapper();
////        String json2 = mapper1.writeValueAsString(typeService.listType());
////        System.out.println(json2);
        System.out.println("/******END***********************************************************************************/");
        return "admin/adminIndex";
    }

    @PostMapping("/bloglist/search")
    public String search(@PageableDefault(size = 3, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model){
        model.addAttribute("page", blogListService.searchBlog(pageable, blog));
//        System.out.println(blogListService.searchBlog(pageable, blog).getContent());
        return "admin/adminIndex :: blogList";
    }

    @GetMapping("/bloglist/{id}/delete")
    public String del(@PathVariable Long id, RedirectAttributes attributes){
        blogListService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/bloglist";
    }
}
