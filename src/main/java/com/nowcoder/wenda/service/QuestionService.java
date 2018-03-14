package com.nowcoder.wenda.service;

import com.nowcoder.wenda.dao.QuestionDao;
import com.nowcoder.wenda.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/3/12.
 */
@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public List<Question> getLatestQuestion(int userId, int offset,int limit){

        return  questionDao.selectLatestQuestions(userId,offset,limit);
    }
}
