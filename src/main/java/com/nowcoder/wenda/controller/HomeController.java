package com.nowcoder.wenda.controller;

import com.nowcoder.wenda.dao.ViewObject;
import com.nowcoder.wenda.model.Question;
import com.nowcoder.wenda.service.QuestionService;
import com.nowcoder.wenda.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.sound.midi.Patch;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/12.
 */
@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;

    @RequestMapping(path={"/","/index",}, method = {RequestMethod.GET})

    public String index(Model model){

        model.addAttribute("vos",getQuestion(0,0,10));
        return "index";
    }

    @RequestMapping(path={"/user/{userId}"}, method = {RequestMethod.GET})
    public String user(Model model, @PathVariable("userId") int userId){

        model.addAttribute("vos",getQuestion(userId,0,10));
        return  "index";
    }

    private List<ViewObject> getQuestion(int userId, int offset, int limit) {

        List<Question> questionList = questionService.getLatestQuestion(userId, offset, limit);
        List<ViewObject> vos = new ArrayList<ViewObject>();
        for (Question question : questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("user", userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }
}
