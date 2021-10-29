package com.zhuqielinode.furnituremall.furnituremall.service;

import com.zhuqielinode.furnituremall.furnituremall.dao.GoodsMapper;
import com.zhuqielinode.furnituremall.furnituremall.exception.*;
import com.zhuqielinode.furnituremall.furnituremall.po.Goods;
import com.zhuqielinode.furnituremall.furnituremall.po.Order;
import com.zhuqielinode.furnituremall.furnituremall.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
@Service
@Component
public class GoodFuncImpl implements GoodFunc {
    @Autowired
    private GoodsMapper mapper;
    @Autowired
    RedisTemplate redisTemplate;
    private final String salt = "sjajaspu-i-2jrfm;sd";
    public void setgoodFuncImplDao(GoodsMapper mapper) {
        this.mapper = mapper;
    }
    @Override
    public RespBean goodAdd(Goods good) {
        int num=mapper.insert(good);
        return RespBean.success();
    }

    @Override
    public RespBean goodDelete(int gid) {
        int num = mapper.deleteByPrimaryKey(gid);
        if (num!=1)
            throw new GlobalException(RespBeanEnum.DELETE_ERROR);
        return RespBean.success();
    }

    @Override
    public RespBean selectGood(int gid) {
        Goods good=mapper.selectByPrimaryKey(gid);
        RespBean rs = RespBean.success(good);
        return rs;
    }

    @Override
    public RespBean fuzzySelectGoods(String keyword) {
        List<Goods> goods=mapper.fuzzySelectGoods(keyword);
        RespBean rs = RespBean.success(goods);
        return rs;
    }

    @Override
    public RespBean typeSelect(String type) {
        List<Goods> goods=mapper.typeSelect(type);
        RespBean rs = RespBean.success(goods);
        return rs;
    }

    @Override
    public RespBean userOrder(String openid) {
        List<Goods> goods=mapper.userOrder(openid);
        RespBean rs = RespBean.success(goods);
        return rs;
    }

    @Override
    public RespBean AllOrder() {
        List<Goods> goods=mapper.AllOrder();
        RespBean rs = RespBean.success(goods);
        return rs;
    }
//    @Transactional
//    @Override
//    public boolean buyGood(int gid ,String openid, double pay) {
//
//        int num0=mapper.insertSaleHistory(gid,openid,pay);
//
//        int num1=mapper.updatesaleByPrimaryKeySelective(gid,pay);
//
//        Goods sale=mapper.selectByPrimaryKey(gid);
//        if(sale.getGoodsprice()>0){
//            throw new NullPointerException(sale.getGoodsid()+"支付价格不够");
//        }
//        Order order=new Order(gid,openid,0,null,null);
//        int num2=mapper.insertOrderList(order);
//        int num3=mapper.deleteByPrimaryKey(order.getGoodsid());
//        return true;
//    }

    @Override
    public RespBean AllGoods() {
        List<Goods> goods = mapper.AllGoods();
        RespBean rs = RespBean.success(goods);
        return rs;
    }

    @Override
    public RespBean findAll() {
        List<Goods> seckillList = redisTemplate.boundHashOps("seckill").values();
        if (seckillList == null || seckillList.size() == 0){
            //说明缓存中没有秒杀列表数据
            //查询数据库中秒杀列表数据，并将列表数据循环放入redis缓存中
            seckillList = mapper.AllGoods();
            for (Goods good : seckillList){
                //将秒杀列表数据依次放入redis缓存中，key:秒杀表的ID值；value:秒杀商品数据
                redisTemplate.boundHashOps("seckill").put(String.valueOf(good.getGoodsid()), good);
            }
        }
        return RespBean.success(seckillList);

    }
    //前端定时，秒杀开始才通过此连接获取商品秒杀的url
    public Exposer exportSeckillUrl(int gid) {
        Goods seckill = (Goods) redisTemplate.boundHashOps("seckill").get(String.valueOf(gid));
        if (seckill == null) {
            //说明redis缓存中没有此key对应的value
            //查询数据库，并将数据放入缓存中
            seckill = mapper.selectByPrimaryKey(gid);
            if (seckill == null) {
                //说明没有查询到
                return new Exposer(false, gid);
            } else {
                //查询到了，存入redis缓存中。 key:秒杀表的ID值； value:秒杀表数据
                redisTemplate.boundHashOps("seckill").put(String.valueOf(seckill.getGoodsid()), seckill);
            }
        }
        Date startTime = seckill.getStarttime();
        Date endTime = seckill.getEndtime();
        //获取系统时间
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, gid, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        //转换特定字符串的过程，不可逆的算法
        String md5 = getMD5(gid);
        return new Exposer(true, md5, gid);
    }
    //秒杀
    @Transactional
    @Override
    public SeckillExecution executeSeckill(int seckillId, double pay, String openid, String md5)
            throws  SeckillException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException(4);
        }
        //执行秒杀逻辑：1.减库存；2.储存秒杀订单
        Date nowTime = new Date();
        try {
            //记录秒杀订单信息
            int insertCount=mapper.insertSaleHistory(seckillId,openid,pay);
            //唯一性：seckillId,userPhone，保证一个用户只能秒杀一件商品
            if (insertCount <= 0) {
                //重复秒杀
                throw new SeckillException(1);
            } else {
                //减库存,价格为零则秒杀成功
                int num1=mapper.updatesaleByPrimaryKeySelective(seckillId,pay);
                if (num1 <= 0) {
                    //没有更新记录，秒杀结束
                    throw new SeckillException(2);

                } else {
                    //是否支付成功
                    Goods sale=mapper.selectByPrimaryKey(seckillId);
                    Date payTime = new Date();
                    if(sale.getGoodsprice()>0){

                        throw new SeckillException(3);
                    }
                    //秒杀成功
                    Order order=new Order(Integer.toString(seckillId)+openid,seckillId,openid,0,nowTime,payTime);
                    int num2=mapper.insertOrderList(order);
                    int num3=mapper.setStatus(order.getGoodsId());//因为外键所以不可以删除货物，设置货物状态为0表示已卖出
                    //更新缓存（更新库存数量）
                    BoundHashOperations seckill = redisTemplate.boundHashOps("seckill");
                    seckill.delete(String.valueOf(seckillId));

                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, order);
                }
            }
        }  catch (SeckillException e) {
           throw e;
        }catch (Exception e) {
           throw new SeckillException(4);
        }
    }
    private String getMD5(int seckillId) {
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

}
