package com.nowcoder.wenda.service;

import com.nowcoder.wenda.Util.WendaUtil;
import com.nowcoder.wenda.dao.LoginTicketDao;
import com.nowcoder.wenda.dao.UserDao;
import com.nowcoder.wenda.model.LoginTicket;
import com.nowcoder.wenda.model.Question;
import com.nowcoder.wenda.model.User;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.*;

/**
 * Created by Administrator on 2018/3/12.
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
   private   UserDao userDao;

    @Autowired
    private LoginTicketDao loginTicketDao;

   public User getUser(int id){
       return userDao.selectedById(id);
   }

    public User selectByName(String name) {
        return userDao.selectByName(name);
    }

    /**
     * 注册功能
     * @param username
     * @param password
     * @return
     */


   public Map<String, String> register(String username, String password){
     Map<String, String> map = new HashMap<String, String>();
     if(StringUtils.isEmpty("username")){
       map.put("msg", " 用户名不能为空！");
       return map;
     }
     if(StringUtils.isEmpty("password")){
         map.put("msg", "密码不能为空！");
       return map;
     }

     User user = userDao.selectByName("username");
     if (user != null) {
         map.put("msg", "用户名已存在");
         return map;
     }

     user = new User();
     user.setName(username);

     user.setSalt(UUID.randomUUID().toString().substring(0,5));
     user.setPassword(WendaUtil.MD5(password+user.getSalt()));   //密码安全性
     user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
     //注册完自动登录
     String ticket = addLoginTicket(user.getId());  //将ticket与用户id关联
     map.put("ticket", ticket);


     return map;
   }

    /**
     * 登录与登出
     *增加一个ticket
     * @param
     * @return
     */
   public  Map<String , Object> login(String name, String password){
       Map<String, Object> map = new HashMap<String,Object>();
       if (StringUtils.isEmpty("username")){
           map.put("msg", "用户名不能为空");
           return map;
       }

       if (StringUtils.isEmpty("password")){
           map.put("msg","密码不能为空");
           return map;
       }

       User user = userDao.selectByName("username");
       if (user ==null) {
           map.put("msg","用户名不存在");
           return map;
       }

       if (!WendaUtil.MD5(password+user.getSalt()).equals(user.getPassword())){
           map.put("msg","密码不正确");
           return map;
       }

       String ticket = addLoginTicket(user.getId());
       map.put("ticket", ticket);

       return map;
   }





   public String addLoginTicket(int userId){
       LoginTicket loginTicket = new LoginTicket();
       loginTicket.setUserid(userId);
       Date now = new Date();
       now.setTime(3600*24*100 + now.getTime());
       loginTicket.setExpired(now);
       loginTicket.setStatus(0);  //0表示登录有效
       loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
       loginTicketDao.addTicket(loginTicket);
       return loginTicket.getTicket();
   }

   public void logout(String ticket){
       loginTicketDao.updateStatus(ticket,1);
   }

}
