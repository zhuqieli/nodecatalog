package com.zhuqielinode.furnituremall.furnituremall.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
    //通用
    SUCCESS(200,"SUCCESS"),
    ERROR(500,"服务端异常"),
    //用户模块
    LOGIN_ERROR(500211,"openid不匹配"),//登录
    MESSAGE_ERROR(500210,"信息不完整"),//save,update
    MOBILE_ERROR(500212,"手机号格式不正确"),//ismobile
    BIND_ERROR(500213,"参数校验异常"),
    SAVE_ERROR(500214,"用户openid已存在"),
    ILLEGAL_ERROR(500100,"非法请求，检查您的操作"),
    NOT_LOGIN(500100,"请先登录"),
    DELETE_ERROR(500301,"删除失败");
    private final Integer code;
    private final String message;

}
