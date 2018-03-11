package com.nowcoder.wenda.aspect;


import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
/**
 * Created by Administrator on 2018/3/8.
 */
@Aspect
@Component
//aop切面
public class LogAspect {
   /*
   private static final Logger logger = org.slf4j.LoggerFactory.getLogger(LogAspect.class);
    @Before("execution(* com.nowcoder.wenda.controller.SettingController.*(..)")
    public  void beforeMethod(){
     logger.info("before method");
    }

    @After("execution(* com.nowcoder.wenda.controller.SettingController.*(..)")
    public void afterMethod() {
    logger.info("after method");
    }
   */
}
