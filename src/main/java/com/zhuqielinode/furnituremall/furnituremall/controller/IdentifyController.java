package com.zhuqielinode.furnituremall.furnituremall.controller;

import com.zhuqielinode.furnituremall.furnituremall.po.User;
import com.zhuqielinode.furnituremall.furnituremall.service.UserDetailFunc;
import com.zhuqielinode.furnituremall.furnituremall.vo.LoginVo;
import com.zhuqielinode.furnituremall.furnituremall.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class IdentifyController {
    @Autowired
    UserDetailFunc userDetailFunc;
    
    //用户注册，检验参数
    @PostMapping("/furnituremall/user/detial/save")
    public @ResponseBody RespBean saveUserdetial(@Valid User user){
        RespBean rs = userDetailFunc.insert(user);
        return rs;
    }
    //用户登录
    @GetMapping("/furnituremall/loginuser")
        public @ResponseBody RespBean loginUser( String openId , HttpServletRequest request, HttpServletResponse response){
            RespBean rs = userDetailFunc.selectUser(openId,request,response);
            return rs;
    }
    //用户改地址与昵称
     @PostMapping("/furnituremall/user/detial/update")
    public @ResponseBody RespBean updateUser(String address, String nickName,  LoginVo loginVo){
         String openid = loginVo.getOpenId();
         RespBean rs = userDetailFunc.updateUserMessage(openid, address, nickName);
         return rs;
     }
     //获取用户信息
     @GetMapping ("/furnituremall/user/detial/getuser")
    public @ResponseBody RespBean getUser(LoginVo loginVo){
         RespBean rs = userDetailFunc.selectAndShowUser(loginVo.getOpenId());
         return rs;
     }

}
