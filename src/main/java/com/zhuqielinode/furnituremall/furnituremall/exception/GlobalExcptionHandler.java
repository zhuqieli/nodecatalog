package com.zhuqielinode.furnituremall.furnituremall.exception;

import com.zhuqielinode.furnituremall.furnituremall.vo.RespBean;
import com.zhuqielinode.furnituremall.furnituremall.vo.RespBeanEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//异常控制，可定义不同异常处理方法1.两个注解2.处理方法  ps处理控制器抛出异常，另一个errorControoller处理未进入处理器异常
@RestControllerAdvice
public class GlobalExcptionHandler {
    @ExceptionHandler(Exception.class)
    public RespBean ExceptionHandler(Exception e){

        if(e instanceof  GlobalException){
            GlobalException ex=(GlobalException)e;
            return RespBean.error(ex.getRespBeanEnum());
        }else if(e instanceof BindException){
            BindException ex=(BindException)e;
            RespBean respBean = RespBean.error(RespBeanEnum.BIND_ERROR);
            respBean.setMessage("参数校验异常"+ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return respBean;
        }else if(e instanceof IllegalStateException){
            IllegalStateException ex=(IllegalStateException)e;
            RespBean respBean = RespBean.error(RespBeanEnum.ILLEGAL_ERROR);
            return respBean;
        }
        return RespBean.error(RespBeanEnum.ERROR);
    }
}
