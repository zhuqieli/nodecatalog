package com.zhuqielinode.furnituremall.furnituremall.service;

import com.zhuqielinode.furnituremall.furnituremall.po.User;
import com.zhuqielinode.furnituremall.furnituremall.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserDetailFunc {
    RespBean insert(User user);
    RespBean updateUserMessage(String openid,String address,String nickName);
    RespBean selectUser(String openid , HttpServletRequest request, HttpServletResponse response);
    String getUserByCookie(String userTicket);
    RespBean selectAndShowUser(String openid);
}
