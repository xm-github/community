package com.xm.community.community.service;

import com.xm.community.community.dto.PaginationDTO;
import com.xm.community.community.dto.QuestionDTO;
import com.xm.community.community.mapper.QuestionMapper;
import com.xm.community.community.mapper.UserMapper;
import com.xm.community.community.model.Question;
import com.xm.community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XM
 * @version 1.0
 * @data 2020-06-22
 * @description QuestionService
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        Integer totalPage;
        if(totalCount%size==0){
            totalPage = totalCount/size;
        }else{
            totalPage = totalCount/size+1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        paginationDTO.setPagination(totalPage,page);
        if(page<1){
            page=1;
        }
        Integer offset = size*(page-1);
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user  = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        System.out.println(paginationDTO);
        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalCount = questionMapper.countByUserId(userId);
        if(totalCount%size==0){
            totalPage = totalCount/size;
        }else{
            totalPage = totalCount/size+1;
        }
        paginationDTO.setPagination(totalPage,page);
        Integer offset = size*(page-1);
        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user  = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        System.out.println(paginationDTO);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question= questionMapper.getById(id);
        User user  = userMapper.findById(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setUser(user);
        BeanUtils.copyProperties(question,questionDTO);
        return questionDTO;
    }
}