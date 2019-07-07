package com.wallly.startboot.controller;

import com.wallly.startboot.Mapper.QuestionMapper;
import com.wallly.startboot.Mapper.UserMapper;
import com.wallly.startboot.Model.Question;
import com.wallly.startboot.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            HttpServletRequest request,
            Model model) {
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if (title == null || title.equals("")){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description == null || description.equals("")){
            model.addAttribute("error","描述不能为空");
            return "publish";
        }if (tag == null || tag.equals("")){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        User user = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies){
            if (cookie.getName().equals("token")){
                user = userMapper.findByToken(cookie.getValue());
                if (user != null){
                    request.getSession().setAttribute("githubUser",user);
                }
                break;
            }
        }
        if (user == null  && user.getAccountId() != null){
            model.addAttribute("error","用户信息不能为空");
            return "publish";
        }
        Question question = new Question();
        question.setTag(tag);
        question.setDescription(description);
        question.setTitle(title);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setCreator(user.getAccountId());
        questionMapper.create(question);
        return "index";
    }

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }
}
