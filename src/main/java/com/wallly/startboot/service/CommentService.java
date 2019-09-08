package com.wallly.startboot.service;

import com.wallly.startboot.Mapper.CommentMapper;
import com.wallly.startboot.Mapper.QuestionMapper;
import com.wallly.startboot.Mapper.UserMapper;
import com.wallly.startboot.Model.Comment;
import com.wallly.startboot.Model.Question;
import com.wallly.startboot.Model.User;
import com.wallly.startboot.dto.CommentDTO;
import com.wallly.startboot.enums.CommentTypeEnum;
import com.wallly.startboot.exception.CustomizeErrorCode;
import com.wallly.startboot.exception.CustomizeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void insert(Comment comment) {
        //判断回复内容有没有
        if (comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        //判断回复类型
        if (comment.getType() == null || !CommentTypeEnum.isExits(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (StringUtils.isBlank(comment.getContent())){
            throw new CustomizeException(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }
        if (comment.getType() == CommentTypeEnum.Comment.getType()){
            //评论的回复
            Comment dbComment = commentMapper.selectById(comment.getParentId());
            if (dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
            //增加问题的回复数功能
            Comment contentCount = new Comment();
            contentCount.setId(comment.getParentId());
            contentCount.setContentCount(1);
            commentMapper.incContentCount(contentCount);
        }else {
            //问题的回复
            Question question = questionMapper.findById(comment.getParentId());
            if (question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionMapper.updateCommentCount(question);
        }
    }

    public List<CommentDTO> listByTargetId(Integer id, Integer type) {
        List<Comment> comments = commentMapper.listByQuestionId(id,type);
        if (comments.size() == 0){
            return new ArrayList<>();
        }

        //将所有的评论者id获取到
        Set<Integer> collect = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> list = new ArrayList<>();
        list.addAll(collect);
        List<User> users = userMapper.findByIds(list);
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        List<CommentDTO> commentDTOs = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOs;
    }
}
