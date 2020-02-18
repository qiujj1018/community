package com.jiajia.community.controller;

import com.jiajia.community.dto.CommentDTO;
import com.jiajia.community.dto.QuestionDTO;
import com.jiajia.community.enums.CommentTypeEnum;
import com.jiajia.community.exception.CustomizeErrorCode;
import com.jiajia.community.exception.CustomizeException;
import com.jiajia.community.service.QuestionService;
import com.jiajia.community.dto.QuestionDTO;
import com.jiajia.community.mapper.QuestionMapper;
import com.jiajia.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id,Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        //增加阅读数
        questionService.incView(id);
        model.addAttribute("question", questionDTO);
        return "question";
    }
}
