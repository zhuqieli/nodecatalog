package com.zhuqielinode.furnituremall.furnituremall.controller;

import com.zhuqielinode.furnituremall.furnituremall.exception.GlobalException;
import com.zhuqielinode.furnituremall.furnituremall.po.ShareMessage;
import com.zhuqielinode.furnituremall.furnituremall.service.ShareFunc;
import com.zhuqielinode.furnituremall.furnituremall.vo.LoginVo;
import com.zhuqielinode.furnituremall.furnituremall.vo.RespBean;
import com.zhuqielinode.furnituremall.furnituremall.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class ShareController {

    @Autowired
    ShareFunc shareFunc;


//用户添加朋友圈
@PostMapping ("/furnituremall/user/share/addmessgae")
    public @ResponseBody RespBean shareMessage(LoginVo loginVo, String image, String message,String goodsid){
    String openid = loginVo.getOpenId();
    ShareMessage msg=new ShareMessage();
    msg.setOpenId(openid);
    msg.setShareImage(image);
    msg.setShareMessage(message);
    msg.setGoodsId(goodsid);
    RespBean rs = shareFunc.addShareMessage(msg);
    return rs;
}
//点赞前检查登录，未做重复点赞，同账号点赞限制
@RequestMapping("/furnituremall/user/share/hit")
    public @ResponseBody RespBean hitMessage( String msgId ,  LoginVo loginVo){
    String openid = loginVo.getOpenId();
    if(msgId==null){
        throw new GlobalException((RespBeanEnum.MESSAGE_ERROR));
    }
    RespBean rs = shareFunc.hitShareMessage(Integer.valueOf(msgId));
    return rs;
}
//删除，删除前检查登录，检查朋友圈是否属于已登录账号
@PostMapping("/furnituremall/user/share/delete")
    public @ResponseBody RespBean deleteMessage(String msgId, LoginVo loginVo){
    String openid = loginVo.getOpenId();
    if(msgId==null){
        throw new GlobalException((RespBeanEnum.MESSAGE_ERROR));
    }
    RespBean rs = shareFunc.selectMessage(Integer.valueOf(msgId));
    ShareMessage ms =(ShareMessage) rs.getObj();
    if(!openid.equals(ms.getOpenId()))
        throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
    RespBean rs1 = shareFunc.deleteShareMessage(Integer.valueOf(msgId));
    return rs1;
}
//详情，查看单条朋友圈
@GetMapping("/furnituremall/user/share/getone")
    public @ResponseBody RespBean selectMessage( String msgId){
    if(msgId==null){
        throw new GlobalException((RespBeanEnum.MESSAGE_ERROR));
    }
    RespBean rs = shareFunc.selectMessage(Integer.valueOf(msgId));
    return rs;
}
//找到自己所有的朋友圈，在此处加入删除接口
@GetMapping("/furnituremall/user/share/getUserAll")
    public @ResponseBody
    RespBean selectUserMessage( LoginVo loginVo){
    String openid = loginVo.getOpenId();
    RespBean rs = shareFunc.selectUserMessage(openid);
    return rs;
}
//查看热点，朋友圈按点赞降序排列
@GetMapping("/furnituremall/user/share/getHot")
    public @ResponseBody RespBean selectHotMessage(){
    RespBean rs = shareFunc.selectHotMessage();
    return rs;
}
//关键词查找朋友圈
@GetMapping("/furnituremall/user/share/fuzzyGet")
    public @ResponseBody RespBean fuzzyselectMessage(String keywords){
    RespBean rs = shareFunc.fuzzySelectMessage(keywords);
    return rs;
}

}
