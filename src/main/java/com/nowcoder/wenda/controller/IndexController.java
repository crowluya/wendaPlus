package com.nowcoder.wenda.controller;

import com.nowcoder.wenda.model.User;
import com.nowcoder.wenda.service.WendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;



/**
 * Created by Administrator on 2017/10/12.
 */
@Controller //入口层
public class IndexController {
     @Autowired
     WendaService wendaService;
   //   WendaService wenda Service = new WendaService();
   // @RequestMapping(path={"/index","/"}, method = {RequestMethod.GET})
   // @ResponseBody
   // public  String  index(HttpSession httpSession) {
  //      return " Hello NewCoder" + httpSession.getAttribute("msg") ;
  //  }

    @RequestMapping(path={"/profile/{groupId}/{userId}"})
    @ResponseBody
    public  String  profile(@PathVariable("userId") int userId,
                             @PathVariable("groupId") String groupId,
                             @RequestParam(value = "type",defaultValue ="23",required =true ) int type,
                             @RequestParam( value = "key",defaultValue = "zzz",required = false) String key
                           )  {
        return  String.format("Profile Page of %s / %d t:%d k: %s ",  groupId , userId,type , key);
    }


    @RequestMapping(path={"/vm"}, method = {RequestMethod.GET})

    public  String template(Model model)  {
        model.addAttribute("value1","aaaaa1");
        model.addAttribute("msg","这个字符串啊");
      Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 4; ++i)
        {
            map.put(String.valueOf(i), String.valueOf(i*i));
        }
       model.addAttribute("map", map);
       model.addAttribute("user", new User("Ling"));
        return  ("home");
    }

    @RequestMapping(path={"/request"}, method = {RequestMethod.GET})
    @ResponseBody
    public String request(Model model, HttpServletResponse response,
                          HttpServletRequest request,
                          HttpSession httpSession,
                          @CookieValue("JSESSIONID") String sessionId){
     StringBuilder sb = new StringBuilder();
     sb.append("COOKIEVALUE:" + sessionId);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            sb.append(name + ":" + request.getHeader(name) + "<br>");
        }
        if (request.getCookies() != null) {
            for (Cookie cookie: request.getCookies())
            sb.append("Cookie: " + cookie.getName() + "Value:" + cookie.getValue());
        }
 //   response.addHeader("");
     sb.append(request.getMethod()+ "<br>");
     sb.append(request.getQueryString()+" <br>");
     sb.append(request.getPathInfo() + "<br>");
     sb.append(request.getRequestURI() + "<br>");
        return sb.toString();
    }
    @RequestMapping(path={"/redirect/{code}"}, method = {RequestMethod.GET})
    @ResponseBody
    public String redirect(@PathVariable ("code") int code) {

     return "redirect:/index";
    }


    @RequestMapping(path={"/admin"}, method = {RequestMethod.GET})
    @ResponseBody
    public String admin(@RequestParam("key") String key){
       if("admin".equals(key)) {
        return    "hello admin";
       }
       throw new  IllegalArgumentException("参数不对");
    }



    @ExceptionHandler
    @ResponseBody
    public String error(Exception e){
        return "error:" + e.getMessage();
    }

}