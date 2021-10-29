package com.zhuqielinode.furnituremall.furnituremall.service;

import com.zhuqielinode.furnituremall.furnituremall.po.Goods;
import com.zhuqielinode.furnituremall.furnituremall.vo.Exposer;
import com.zhuqielinode.furnituremall.furnituremall.vo.RespBean;
import com.zhuqielinode.furnituremall.furnituremall.vo.SeckillExecution;

import java.util.List;

public interface GoodFunc {
    RespBean goodAdd(Goods good);
    RespBean goodDelete(int gid);
    RespBean selectGood(int gid);
    RespBean fuzzySelectGoods(String keyword);
    RespBean typeSelect(String type);
    RespBean userOrder(String openid );
    RespBean AllOrder();
     RespBean  AllGoods();
    //boolean buyGood(int gid ,String openid, double pay);
    RespBean findAll();
    public Exposer exportSeckillUrl(int gid);
    public SeckillExecution executeSeckill(int seckillId, double pay, String openid, String md5);
}
