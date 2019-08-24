package com.wallly.startboot.controller;

import com.wallly.startboot.Model.Comment;
import com.wallly.startboot.Model.User;
import com.wallly.startboot.dto.CommentCreateDTO;
import com.wallly.startboot.dto.ResultDTO;
import com.wallly.startboot.exception.CustomizeErrorCode;
import com.wallly.startboot.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDto, HttpServletRequest request){
        if ((User)request.getSession().getAttribute("githubUser") == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDto.getParentId());
        comment.setContent(commentCreateDto.getContent());
        comment.setType(commentCreateDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(1);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
