package com.lzh.blog.web.admin;

import com.lzh.blog.entity.Type;
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
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String list(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                       Model model){
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/adminType";
    }

    @GetMapping("/types/add")
    public String addType(){
        return "admin/addType";
    }

    @PostMapping("/types")
    public String add(Type type, RedirectAttributes attributes){

        Type t0 = typeService.getByName(type.getName());
        if(t0 != null){
            attributes.addFlashAttribute("message","分类已存在");
            return "redirect:/admin/types";
        }

        Type t = typeService.saveType(type);
        if(t == null){
            attributes.addFlashAttribute("message","添加失败");
        }else {
            attributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/edit")
    public String editType(@PathVariable Long id, Model model){

        model.addAttribute("type", typeService.getType(id));
        return "admin/editType";
    }

    @PostMapping("/types/{id}")
    public String edit(Type type,
                       @PathVariable Long id,
                       RedirectAttributes attributes){

        Type t0 = typeService.getByName(type.getName());
        if(t0 != null){
            attributes.addFlashAttribute("message","分类已存在");
            return "redirect:/admin/types";
        }

        Type t = typeService.updateType(id, type);
        if(t == null){
            attributes.addFlashAttribute("message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String del(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
