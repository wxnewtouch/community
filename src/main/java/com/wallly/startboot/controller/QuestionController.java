package com.wallly.startboot.controller;

import com.wallly.startboot.dto.CommentDTO;
import com.wallly.startboot.dto.QuestionDTO;
import com.wallly.startboot.enums.CommentTypeEnum;
import com.wallly.startboot.service.CommentService;
import com.wallly.startboot.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(value = "id")Integer id,
                           Model model){
        QuestionDTO questionDTO = questionService.findById(id);
        Integer type = CommentTypeEnum.Question.getType();
        List<CommentDTO> list = commentService.listByTargetId(id, type);
        questionService.incView(id);
        List<QuestionDTO> questionDTOS = questionService.queryByTag(questionDTO);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comment",list);
        model.addAttribute("tags",questionDTOS);

        return "question";
    }
}
