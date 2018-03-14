package com.nowcoder.wenda.controller;

import com.nowcoder.wenda.service.WendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2018/3/8.
 */
@Controller
public class SettingController {
    @Autowired
    WendaService wendaService;
    @RequestMapping(path = {"/setting"}, method = {RequestMethod.GET})
    @ResponseBody
    public  String  setting(HttpSession httpSession){
        return "Setting OK" + wendaService.getMessage(1) ;
    }

    @RequestMapping(path={"/in"}, method = {RequestMethod.GET})
    @ResponseBody
    public  String  in(HttpSession httpSession) {
       // logger.info("sss");
        return  wendaService.getMessage(7) + " Hello NewCoder 66666" + httpSession.getAttribute("msg");
    }
}
