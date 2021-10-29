package com.zhuqielinode.furnituremall.furnituremall.dao;

import com.zhuqielinode.furnituremall.furnituremall.po.ShareMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
public interface ShareMessageMapper {
    int deleteByPrimaryKey(Integer messageId);

    int insert(ShareMessage record);

    int insertSelective(ShareMessage record);

    ShareMessage selectByPrimaryKey(Integer messageId);

    int updateByPrimaryKeySelective(ShareMessage record);

    int updateByPrimaryKey(ShareMessage record);


    int hitMessage(int messageId);

    List<ShareMessage> selectUserMessage(String openId);

    List<ShareMessage> selectHotMessage();

    List<ShareMessage> fuzzySelectMessage(String keywords);

}