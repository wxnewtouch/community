package com.wallly.startboot.service;

import com.wallly.startboot.Mapper.CommentMapper;
import com.wallly.startboot.Mapper.NotificationMapper;
import com.wallly.startboot.Mapper.QuestionMapper;
import com.wallly.startboot.Mapper.UserMapper;
import com.wallly.startboot.Model.Comment;
import com.wallly.startboot.Model.Notification;
import com.wallly.startboot.Model.Question;
import com.wallly.startboot.Model.User;
import com.wallly.startboot.dto.CommentDTO;
import com.wallly.startboot.enums.CommentTypeEnum;
import com.wallly.startboot.enums.NotificationStatusEnum;
import com.wallly.startboot.enums.NotificationTypeEnum;
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
    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment, User commenter) {
        //判断回复内容有没有
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        //判断回复类型
        if (comment.getType() == null || !CommentTypeEnum.isExits(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (StringUtils.isBlank(comment.getContent())) {
            throw new CustomizeException(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }
        if (comment.getType() == CommentTypeEnum.Comment.getType()) {
            //回复评论
            Comment dbComment = commentMapper.selectById(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
            Question question = questionMapper.findById(dbComment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //增加问题的回复数功能
            Comment contentCount = new Comment();
            contentCount.setId(comment.getParentId());
            contentCount.setContentCount(1);
            commentMapper.incContentCount(contentCount);
            //创建通知
            createNotify(comment, dbComment.getCommentator(), commenter.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT, question.getId());
        } else {
            //回复问题
            Question question = questionMapper.findById(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionMapper.updateCommentCount(question);
            //创建通知
            createNotify(comment, question.getCreator(), commenter.getName(), question.getTitle(), NotificationTypeEnum.REPLY_QUESTION, question.getId());
        }
    }

    private void createNotify(Comment comment, Integer receiver, String notifierName, String outerTitle, NotificationTypeEnum replyComment, Integer outerId) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        //获取当前评论人的id
        notification.setNotifier(comment.getCommentator());
        //获取当前评论的问题的id
        notification.setOuterId(outerId);
        //获取创建了该问题的人的id
        notification.setReceiver(receiver);
        //回复的类型
        notification.setType(replyComment.getType());
        //状态
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setOuterTitle(outerTitle);
        notification.setNotifierName(notifierName);
        notificationMapper.insert(notification);
    }

    public List<CommentDTO> listByTargetId(Integer id, Integer type) {
        List<Comment> comments = commentMapper.listByQuestionId(id, type);
        if (comments.size() == 0) {
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
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOs;
    }
}
