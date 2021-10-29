package com.zhuqielinode.furnituremall.furnituremall.dao;

import com.zhuqielinode.furnituremall.furnituremall.po.Goods;
import com.zhuqielinode.furnituremall.furnituremall.po.Order;
import com.zhuqielinode.furnituremall.furnituremall.vo.Exposer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMapper {
    int deleteByPrimaryKey(Integer goodsid);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer goodsid);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);
    List<Goods> fuzzySelectGoods(String keyword);
    List<Goods> typeSelect(String Type);
    List<Goods> userOrder(String openid);
    List<Goods> AllOrder();
    List<Goods>AllGoods();
    int insertSaleHistory(@Param("gid") int gid,@Param("openId") String openid,@Param("pay") double pay);
    int updatesaleByPrimaryKeySelective(@Param("goodsId") int gid, @Param("pay") double pay);
    int insertOrderList(Order order);
    int setStatus(int goodsId);
    Exposer exportSeckillUrl( int seckillId);
}