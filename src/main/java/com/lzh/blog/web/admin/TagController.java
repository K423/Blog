package com.lzh.blog.web.admin;

import com.lzh.blog.entity.Tag;
import com.lzh.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String list(Pageable pageable, Model model){
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/adminTags";
    }

    @GetMapping("/tags/add")
    public String addTags(){
        return "admin/addTags";
    }

    @PostMapping("/tags")
    public String add(Tag tag, RedirectAttributes attributes){
        Tag t0 = tagService.getByName(tag.getName());
        if (t0 != null){
            attributes.addFlashAttribute("message","标签已存在");
            return "redirect:/admin/tags";
        }
        Tag t = tagService.saveTag(tag);
        if (t == null){
            attributes.addFlashAttribute("message","添加失败");
        }else {
            attributes.addFlashAttribute("message","添加成功");

        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/edit")
    public String editTag(@PathVariable Long id, Model model){
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/editTags";
    }

    @PostMapping("/tags/{id}")
    public String add(Tag tag,
                      @PathVariable Long id,
                      RedirectAttributes attributes){
        Tag t0 = tagService.getByName(tag.getName());
        if (t0 != null){
            attributes.addFlashAttribute("message","标签已存在");
            return "redirect:/admin/tags";
        }
        Tag t = tagService.updateTag(id, tag);
        if (t == null){
            attributes.addFlashAttribute("message","添加失败");
        }else {
            attributes.addFlashAttribute("message","添加成功");

        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String del(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }
}
