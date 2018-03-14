package com.nowcoder.wenda.controller;

import com.nowcoder.wenda.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/13.
 */
@Controller
public class LoginController {
    @Autowired
    UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    /**
     * 注册功能
     */
   @RequestMapping(path={"/reg/"}, method = {RequestMethod.POST})
    public String reg(Model model,
                      @RequestParam("username") String username,
                      @RequestParam("password") String password ,
                      HttpServletResponse response){
     try {
         Map<String, String> map = userService.register(username, password);
       /*  if (map.containsKey("msg")) {
             model.addAttribute("msg", map.get("msg"));
             return "login";
         }
        */
         if (map.containsKey("ticket")) {
             Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
             cookie.setPath("/");
             response.addCookie(cookie);
             return "redirect:/";
         }else {
             model.addAttribute("msg", map.get("msg"));
             return "login";
         }
     }catch(Exception e) {
           logger.error("注册异常" + e.getMessage());
           model.addAttribute("msg","服务器异常");
           return "login";
       }

    }


    /**
     * 登录
     * @param model
     * @param username
     * @param password
     * @param next
     * @param remenberme
     * @param response
     * @return
     */
    @RequestMapping(path={"/login/"}, method = {RequestMethod.POST,RequestMethod.GET} )
    public String login(Model model,
                      @RequestParam(value = "username", required = false) String username ,
                      @RequestParam(value = "password", required = false) String password ,
                      @RequestParam(value = "next", required = false) String next,
                      @RequestParam(value = "remenberme",defaultValue = "false") boolean remenberme,
                      HttpServletResponse response){

       try{
          // Map<String, Object> map = userService.register(username,password);
           Map<String, String> map = userService.register(username, password);
           if (map.containsKey("ticket")) {
               Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
               cookie.setPath("/");

               if (remenberme) {
                   cookie.setMaxAge(3600*24*5);
               }
               response.addCookie(cookie);
               if (StringUtils.isEmpty("next")){
                   return "recirect" + next;
               }
               return "redirect:/index";
           }else {
               model.addAttribute("msg", map.get("msg"));
               return "reglogin";
           }


       } catch (Exception e){
           logger.error("登录异常"+ e.getMessage());
           return "login";
       }
    }




    @RequestMapping(path={"/reglogin"}, method = {RequestMethod.GET})
    public String reglogin(){


        return "login";
    }

    @RequestMapping(value = "path = {/logout}", method ={RequestMethod.GET} )
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return "redirect:/";
    }

}
