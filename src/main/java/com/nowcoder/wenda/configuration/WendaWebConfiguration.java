package com.nowcoder.wenda.configuration;

import com.nowcoder.wenda.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2018/3/14.
 */
@Component
public class WendaWebConfiguration extends WebMvcConfigurerAdapter{

    /**
     * 重载注册自己的拦截器
     * @param registry
     */
    @Autowired
    PassportInterceptor passportInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor); //注册自己的拦截器
        super.addInterceptors(registry);
    }
}
