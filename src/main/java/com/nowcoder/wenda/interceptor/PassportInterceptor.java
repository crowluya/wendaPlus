package com.nowcoder.wenda.interceptor;

import com.nowcoder.wenda.dao.LoginTicketDao;
import com.nowcoder.wenda.dao.UserDao;
import com.nowcoder.wenda.model.HostHolder;
import com.nowcoder.wenda.model.LoginTicket;
import com.nowcoder.wenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by Administrator on 2018/3/14.
 */
@Component

/**
 * 用户身份的验证的拦截器
 * @param httpServletRequest
 * @param httpServletResponse
 * @param o
 * @return
 * @throws Exception
 */
public class PassportInterceptor implements HandlerInterceptor{

    @Autowired
    LoginTicketDao loginTicketDao;    //从ticket里面将cookie取出来，是否是有效的

    @Autowired
    UserDao userDao;

    @Autowired
    HostHolder hostHolder;

    @Override
    //请求开始之前 返回false所有的就结束了  拦截器
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
       String ticket = null;
        /**
         * 遍历cookie 找出自己需要的cookie
          */
       if (httpServletRequest.getCookies() != null){
           for (Cookie cookie:httpServletRequest.getCookies()){
               if (cookie.getName().equals("ticket")){
                   ticket = cookie.getValue();
                   break;
               }
           }
       }

       if (ticket != null) {
           LoginTicket loginTicket = loginTicketDao.sekectByTicket(ticket);
           if (loginTicket == null || loginTicket.getExpired().before(new Date()) ||loginTicket.getStatus()!=0){  //判断t票是不是有效的
              return true;
           }

           User user = userDao.selectedById(loginTicket.getUserid());  //将t票关联用户取出来关联上下文
           hostHolder.setUser(user);

       }





        return true;     //不能返回false 返回false整个请求就结束了
    }

    @Override
    //处理完之后在使用这个
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
       if (modelAndView != null) {
         modelAndView.addObject("user",hostHolder.getUser());
       }
    }

    @Override
    //渲染完了之后再调用
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
          hostHolder.clear();
    }
}
