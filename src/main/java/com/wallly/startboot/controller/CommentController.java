package com.wallly.startboot.controller;

import com.wallly.startboot.Model.Comment;
import com.wallly.startboot.Model.User;
import com.wallly.startboot.dto.CommentCreateDTO;
import com.wallly.startboot.dto.CommentDTO;
import com.wallly.startboot.dto.ResultDTO;
import com.wallly.startboot.enums.CommentTypeEnum;
import com.wallly.startboot.exception.CustomizeErrorCode;
import com.wallly.startboot.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDto, HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("githubUser");
        if (user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDto.getParentId());
        comment.setContent(commentCreateDto.getContent());
        comment.setType(commentCreateDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        //这里有问题，这里的值获取到的永远是1，所以永远不对。
        comment.setCommentator(user.getId());
        commentService.insert(comment,user);
        return ResultDTO.okOf();
    }
    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Integer id){
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.Comment.getType());
        return ResultDTO.okOf(commentDTOS);
    }
}
