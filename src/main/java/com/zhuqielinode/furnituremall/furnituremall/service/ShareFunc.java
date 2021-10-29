package com.zhuqielinode.furnituremall.furnituremall.service;

import com.zhuqielinode.furnituremall.furnituremall.po.ShareMessage;
import com.zhuqielinode.furnituremall.furnituremall.vo.RespBean;
import sun.plugin2.message.Message;

import java.util.List;

public interface ShareFunc {
    RespBean addShareMessage(ShareMessage msg);
    RespBean hitShareMessage(int msgid);
    RespBean deleteShareMessage(int msgid);
    RespBean selectUserMessage(String openid);
    RespBean selectHotMessage();
    RespBean fuzzySelectMessage(String keywords);
    RespBean selectMessage(int msgid);

}
