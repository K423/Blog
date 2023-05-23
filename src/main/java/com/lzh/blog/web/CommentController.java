package com.lzh.blog.web;

import com.lzh.blog.entity.Comment;
import com.lzh.blog.entity.User;
import com.lzh.blog.service.BlogListService;
import com.lzh.blog.service.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogListService blogListService;

    @GetMapping("/comments/{blogId}")  //获取评论
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments", commentService.listComment(blogId));
        return "blogContent :: commentList";
    }

    @PostMapping("/comments")  //保存评论
    public String post(Comment comment, HttpSession httpSession){
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogListService.getBlog(blogId));
        User user = (User) httpSession.getAttribute("user");
        if (user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }
        commentService.save(comment);
        return "redirect:/comments/" + comment.getBlog().getId();
    }
}























