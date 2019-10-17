package com.wallly.startboot.service;

import com.wallly.startboot.Mapper.QuestionMapper;
import com.wallly.startboot.Mapper.UserMapper;
import com.wallly.startboot.Model.Question;
import com.wallly.startboot.Model.User;
import com.wallly.startboot.dto.PaginationDTO;
import com.wallly.startboot.dto.QuestionDTO;
import com.wallly.startboot.exception.CustomizeErrorCode;
import com.wallly.startboot.exception.CustomizeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(Question question) {
        if (question.getId() == null){
            //插入
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else{
            //更新
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);

        }
    }

    public PaginationDTO findByAll(String search, Integer page, Integer size) {
        if (StringUtils.isNotBlank(search)){
            String[] split = StringUtils.split(search, " ");
            String searchFor = Arrays.stream(split).collect(Collectors.joining("|"));
        }
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setTotalCount(questionMapper.count(search));
        /**
         * 现在totalPage已经算出来了
         */
        if (paginationDTO.getTotalCount() % size == 0){
            paginationDTO.setTotalPage(paginationDTO.getTotalCount() / size);
        }else {
            paginationDTO.setTotalPage(paginationDTO.getTotalCount() / size + 1);
        }
        /**
         * 这一步确定一下page的取值
         */
        if (page > paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }
        if (page <= 1){
            page = 1;
        }
        paginationDTO.setpagination(size,page);

        Integer offset = size * (page - 1);
        List<Question> questionList = questionMapper.list(search,offset, size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setData(questionDTOS);
        return paginationDTO;
    }

    public QuestionDTO findById(Integer id) {
        Question question = questionMapper.findById(id);
        if (question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void incView(Integer id) {
        Question oldQuestion = questionMapper.findById(id);
        questionMapper.updateByExampleSelective(oldQuestion);
    }

    public PaginationDTO list(Integer id, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setTotalCount(questionMapper.countByAccountId(id));
        /**
         * 现在totalPage已经算出来了
         */
        if (paginationDTO.getTotalCount() % size == 0){
            paginationDTO.setTotalPage(paginationDTO.getTotalCount() / size);
        }else {
            paginationDTO.setTotalPage(paginationDTO.getTotalCount() / size + 1);
        }
        /**
         * 这一步确定一下page的取值
         * 验证同一个数据的时候，要从范围较大的开始验证，
         * 然后在验证较小的数据，这样完成之后是有保证的。
         */
        if (page > paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }
        if (page <= 1){
            page = 1;
        }
        paginationDTO.setpagination(size,page);
        Integer offset = size * (page - 1);
        List<Question> questionList = questionMapper.listProfile(id,offset, size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setData(questionDTOS);
        return paginationDTO;
    }

    public List<QuestionDTO> queryByTag(QuestionDTO queryTag) {
        if (StringUtils.isBlank(queryTag.getTag())){
            return new ArrayList<>();
        }
        String[] split = StringUtils.split(queryTag.getTag(), ",");
        String tag = Arrays.stream(split).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryTag.getId());
        question.setTag(tag);
        List<Question> questionList = questionMapper.queryByTag(question);
        List<QuestionDTO> questionDTOS = questionList.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
