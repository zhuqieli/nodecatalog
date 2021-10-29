package com.zhuqielinode.furnituremall.furnituremall.service;

import com.zhuqielinode.furnituremall.furnituremall.dao.ShareMessageMapper;
import com.zhuqielinode.furnituremall.furnituremall.po.ShareMessage;
import com.zhuqielinode.furnituremall.furnituremall.vo.RespBean;
import com.zhuqielinode.furnituremall.furnituremall.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.plugin2.message.Message;

import javax.annotation.Resource;
import java.util.List;
@Service
public class ShareFuncImpl implements ShareFunc {
    @Autowired
    private ShareMessageMapper mapper;
    public void setShareFuncImplDao(ShareMessageMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public RespBean addShareMessage(ShareMessage msg) {
        int num=mapper.insertSelective(msg);
        return RespBean.success();
    }

    @Override
    public RespBean hitShareMessage(int msgid) {
        int num=mapper.hitMessage(msgid);
        int hitNum=mapper.selectByPrimaryKey(msgid).getHitCount();
        RespBean rs = RespBean.success(hitNum);
        return rs;
    }

    @Override
    public RespBean deleteShareMessage(int msgid) {
        int num=mapper.deleteByPrimaryKey(msgid);
        return RespBean.success();
    }

    @Override
    public RespBean selectUserMessage(String openid) {
        List<ShareMessage> message=mapper.selectUserMessage(openid);
        RespBean rs = RespBean.success(message);
        return rs;
    }

    @Override
    public RespBean selectHotMessage() {
        List<ShareMessage> message=mapper.selectHotMessage();
        RespBean rs = RespBean.success(message);
        return rs;
    }

    @Override
    public RespBean fuzzySelectMessage(String keywords) {
        List<ShareMessage> message=mapper.fuzzySelectMessage(keywords);
        RespBean rs = RespBean.success(message);
        return rs;
    }

    @Override
    public RespBean selectMessage(int msgid) {
        ShareMessage shareMessage = mapper.selectByPrimaryKey(msgid);
        RespBean rs = RespBean.success(shareMessage);
        return rs;
    }
}
