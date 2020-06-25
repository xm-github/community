package com.xm.community.community.controller;

import com.xm.community.community.dto.QuestionDTO;
import com.xm.community.community.mapper.QuestionMapper;
import com.xm.community.community.service.QuestionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author XM
 * @version 1.0
 * @data 2020-06-22
 * @description QuestuinController
 */
@Controller
public class QuestuinController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Integer id,
                           Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
