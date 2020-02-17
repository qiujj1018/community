package com.jiajia.community.controller;

import com.jiajia.community.dto.QuestionDTO;
import com.jiajia.community.model.Question;
import com.jiajia.community.model.User;
import com.jiajia.community.service.QuestionService;
import com.jiajia.community.dto.QuestionDTO;
import com.jiajia.community.mapper.QuestionMapper;
import com.jiajia.community.model.Question;
import com.jiajia.community.model.User;
import com.jiajia.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Integer id,Model model){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return  "publish";
    }

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            @RequestParam("id") Long id,
                            HttpServletRequest request, Model model) {

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if (title == null || title=="") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null ||description=="" ) {
            model.addAttribute("error", "描述不能为空");
            return "publish";
        }
        if (tag == null || tag=="") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        User user = (User)request.getSession().getAttribute("user");
            if (user == null) {
                model.addAttribute("error", "用户未登录");
                return "publish";
            }

            Question question = new Question();
            question.setTag(tag);
            question.setDescription(description);
            question.setTitle(title);
            question.setCreator(user.getId());
            question.setId(id);
            questionService.createOrUpdate(question);
            return "redirect:/";
        }
    }

