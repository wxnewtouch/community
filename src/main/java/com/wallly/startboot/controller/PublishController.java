package com.wallly.startboot.controller;

import com.wallly.startboot.Mapper.QuestionMapper;
import com.wallly.startboot.Model.Question;
import com.wallly.startboot.Model.User;
import com.wallly.startboot.cache.TagCache;
import com.wallly.startboot.dto.QuestionDTO;
import com.wallly.startboot.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionService questionService;
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "id",required = false) Integer id,
            HttpServletRequest request,
            Model model) {
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tags", TagCache.get());
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
        if (StringUtils.isNotBlank(TagCache.checkedInvalid(tag))){
            model.addAttribute("error","输入非法标签 :"+TagCache.checkedInvalid(tag));
            return "publish";
        }
        User user = (User)request.getSession().getAttribute("githubUser");
        if (user == null  && user.getId()!= null){
            model.addAttribute("error","用户信息不能为空");
            return "publish";
        }
        Question question = new Question();
        question.setTag(tag);
        question.setDescription(description);
        question.setTitle(title);
        question.setCreator(user.getId().toString());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }

    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(value = "id") Integer id,
                       Model model){
        QuestionDTO question = questionService.findById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }
}
