package com.wallly.startboot.controller;

import com.wallly.startboot.Mapper.CommentMapper;
import com.wallly.startboot.Model.Comment;
import com.wallly.startboot.Model.User;
import com.wallly.startboot.dto.CommentDto;
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
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDto commentDto, HttpServletRequest request){
        if ((User)request.getSession().getAttribute("githubUser") == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDto.getParentId());
        comment.setContent(commentDto.getContent());
        comment.setType(commentDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(1L);
        commentService.insert(comment);
        Map<Object, Object> objectHashMap = new HashMap<>();
        objectHashMap.put("message","成功");
        return ResultDTO.okOf();
    }
}
