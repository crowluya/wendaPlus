package com.nowcoder.wenda.model;

import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/3/14.
 */
@Component
/**
 * 多线程处理user
 */
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<User>();

    public  User getUser(){

        return users.get();
    }

    public void setUser(User user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }
}
