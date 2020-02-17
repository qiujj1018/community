package com.jiajia.community.service;

import com.jiajia.community.dto.PaginationDTO;
import com.jiajia.community.dto.QuestionDTO;
import com.jiajia.community.exception.CustomizeErrorCode;
import com.jiajia.community.exception.CustomizeException;
import com.jiajia.community.mapper.QuestionMapper;
import com.jiajia.community.mapper.UserMapper;
import com.jiajia.community.model.Question;
import com.jiajia.community.model.User;
import com.jiajia.community.dto.PaginationDTO;
import com.jiajia.community.dto.QuestionDTO;
import com.jiajia.community.exception.CustomizeErrorCode;
import com.jiajia.community.exception.CustomizeException;
import com.jiajia.community.mapper.QuestionMapper;
import com.jiajia.community.mapper.UserMapper;
import com.jiajia.community.model.Question;
import com.jiajia.community.model.User;
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

    public PaginationDTO list(String search,Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalCount = questionMapper.count();
        if(totalCount % size == 0){
            totalPage=totalCount/size;
        }else{
            totalPage=totalCount/size + 1;
        }

        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        paginationDTO.setPagiation(totalPage,page);

        Integer offent = size * (page-1);
        List<Question> questions = questionMapper.list(offent,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO list(Long UserId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.countByUserId(UserId);

        Integer totalPage;
        if(totalCount % size == 0){
            totalPage=totalCount/size;
        }else{
            totalPage=totalCount/size + 1;
        }

        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }

        paginationDTO.setPagiation(totalPage,page);

        Integer offent = size * (page-1);
        List<Question> questions = questionMapper.listByUserId(UserId,offent,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.getById(id);
        if (question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        BeanUtils.copyProperties(question,questionDTO);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtModified());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
//            System.out.println(question.getId());
            questionMapper.create(question);
        }else{
            //更新
            question.setGmtModified(question.getGmtModified());
            int updated = questionMapper.update(question);
            if(updated !=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void inView(Long id) {
//        Question question = questionMapper.getById(id);
        Question updateQuestion = new Question();
//        updateQuestion.setViewCount(question.getViewCount()+1);
//        questionMapper.updateViewCount(updateQuestion);
        updateQuestion.setId(id);
        updateQuestion.setViewCount(1);
        questionMapper.inView(updateQuestion);
    }
}