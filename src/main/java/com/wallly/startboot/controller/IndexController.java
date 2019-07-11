package com.wallly.startboot.controller;

import com.wallly.startboot.dto.PaginationDTO;
import com.wallly.startboot.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {
        /**
         * 在返回index之前需要将question的数据查询出来
         */
        PaginationDTO pagination = questionService.findByAll(page, size);
        model.addAttribute("pagination", pagination);
        return "index";
    }
}
