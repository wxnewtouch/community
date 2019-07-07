package com.wallly.startboot.controller;

import com.wallly.startboot.Mapper.UserMapper;
import com.wallly.startboot.Model.User;
import com.wallly.startboot.dto.QuestionDTO;
import com.wallly.startboot.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public String index(HttpServletRequest request,Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    User user = userMapper.findByToken(cookie.getValue());
                    if (user != null) {
                        request.getSession().setAttribute("githubUser", user);
                    }
                    break;
                }
            }
        }
        /**
         * 在返回index之前需要将question的数据查询出来
         */
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        questionDTOS = questionService.findByAll();
        model.addAttribute("questions",questionDTOS);
        return "index";
    }
}
