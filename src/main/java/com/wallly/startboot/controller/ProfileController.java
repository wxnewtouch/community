package com.wallly.startboot.controller;

import com.wallly.startboot.Model.User;
import com.wallly.startboot.dto.PaginationDTO;
import com.wallly.startboot.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller()
public class ProfileController {

    @Autowired
    private QuestionService questionService;
    /**
     * 动态的切换路劲
     * @return
     */
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size){
        User user = (User)request.getSession().getAttribute("githubUser");
        if (user == null){
            return "redirect: /";
        }
        if (action.equals("question")){
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的问题");
        }else if (action.equals("repies")){
            model.addAttribute("section","repies");
            model.addAttribute("sectionName","我的回复");
        }
        PaginationDTO pagination =questionService.list(user.getId(),page,size);
        model.addAttribute("pagination", pagination);
        return "profile";
    }
}
