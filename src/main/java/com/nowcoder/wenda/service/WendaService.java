package com.nowcoder.wenda.service;

import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/3/8.
 */
@Service
public class WendaService {
    public  String getMessage(int userid){
        return "Hello Message" + String.valueOf(userid) ;
    }
}
