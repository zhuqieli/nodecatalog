package com.zhuqielinode.furnituremall.furnituremall.dao;

import com.zhuqielinode.furnituremall.furnituremall.po.User;

public interface UserMapper {
    int deleteByPrimaryKey(String openId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String openId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}