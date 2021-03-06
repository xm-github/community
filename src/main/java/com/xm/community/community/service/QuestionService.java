package com.xm.community.community.service;

import com.xm.community.community.dto.PaginationDTO;
import com.xm.community.community.dto.QuestionDTO;
import com.xm.community.community.exception.CustomizeErrorCode;
import com.xm.community.community.exception.CustomizeException;
import com.xm.community.community.mapper.QuestionMapper;
import com.xm.community.community.mapper.UserMapper;
import com.xm.community.community.model.Question;
import com.xm.community.community.model.QuestionExample;
import com.xm.community.community.model.User;
import org.apache.ibatis.session.RowBounds;
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
        Integer totalCount = questionMapper.countByExample(new QuestionExample());
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
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user  = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        QuestionExample questionExample= new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = questionMapper.countByExample(questionExample);
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
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user  = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question= questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user  = userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setUser(user);
        BeanUtils.copyProperties(question,questionDTO);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
            if(question.getId()==null){
                //创建
                question.setGmtCreate(System.currentTimeMillis());
                question.setGmtModified(question.getGmtCreate());
                questionMapper.insert(question);
            }else{
                //更新

                Question updateQuestion = new Question();
                updateQuestion.setGmtModified(System.currentTimeMillis());
                updateQuestion.setTitle(question.getTitle());
                updateQuestion.setDescription(question.getDescription());
                updateQuestion.setTag(question.getTag());
                QuestionExample example = new QuestionExample();
                example.createCriteria()
                        .andIdEqualTo(question.getId());
                int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
                if(updated!=1){
                    throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
                }
            } }
}
