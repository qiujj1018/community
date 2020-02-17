package com.jiajia.community.controller;

import com.jiajia.community.dto.CommentDTO;
import com.jiajia.community.dto.ResultDTO;
import com.jiajia.community.exception.CustomizeErrorCode;
import com.jiajia.community.mapper.CommentMapper;
import com.jiajia.community.model.Comment;
import com.jiajia.community.model.User;
import com.jiajia.community.service.CommentService;
import com.jiajia.community.dto.CommentDTO;
import com.jiajia.community.dto.ResultDTO;
import com.jiajia.community.exception.CustomizeErrorCode;
import com.jiajia.community.mapper.CommentMapper;
import com.jiajia.community.model.Comment;
import com.jiajia.community.model.User;
import com.jiajia.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value="/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO, HttpServletRequest request){

        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(1L);
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}