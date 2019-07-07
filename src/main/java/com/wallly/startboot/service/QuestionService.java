package com.wallly.startboot.service;

import com.wallly.startboot.Mapper.QuestionMapper;
import com.wallly.startboot.Mapper.UserMapper;
import com.wallly.startboot.Model.Question;
import com.wallly.startboot.Model.User;
import com.wallly.startboot.dto.QuestionDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> findByAll() {
        List<Question> questionList = questionMapper.list();
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }
}
