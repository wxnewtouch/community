package com.wallly.startboot.controller;

import com.wallly.startboot.Model.User;
import com.wallly.startboot.UserMapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies){
            if (cookie.getName().equals("token")){
                User user = userMapper.findByToken(cookie.getValue());
                if (user != null){
                    request.getSession().setAttribute("githubUser",user);
                }
                break;
            }
        }

        return "index";
    }
}
