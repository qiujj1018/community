package com.jiajia.community.controller;

import com.jiajia.community.dto.CommentCreateDTO;
import com.jiajia.community.dto.CommentDTO;
import com.jiajia.community.dto.QuestionDTO;
import com.jiajia.community.enums.CommentTypeEnum;
import com.jiajia.community.service.CommentService;
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

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id,Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        //增加阅读数
        questionService.incView(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments",comments);
        return "question";
    }
}
