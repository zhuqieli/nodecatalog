package com.zhuqielinode.furnituremall.furnituremall.controller;

import com.zhuqielinode.furnituremall.furnituremall.exception.GlobalException;
import com.zhuqielinode.furnituremall.furnituremall.exception.RepeatKillException;
import com.zhuqielinode.furnituremall.furnituremall.exception.SeckillCloseException;
import com.zhuqielinode.furnituremall.furnituremall.exception.SeckillException;
import com.zhuqielinode.furnituremall.furnituremall.po.Goods;
import com.zhuqielinode.furnituremall.furnituremall.po.Order;
import com.zhuqielinode.furnituremall.furnituremall.service.GoodFunc;
import com.zhuqielinode.furnituremall.furnituremall.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Controller
@Slf4j
public class GoodsController {
    @Autowired
    GoodFunc goodFunc;
//商家添加商品
    @PostMapping("/furnituremall/seller/goods/addGood")
    public @ResponseBody  RespBean addGood(@Valid Goods good){
        RespBean rs = goodFunc.goodAdd(good);
        return rs;
    }
    //商家删除商品
    @PostMapping("/furnituremall/seller/goods/deleteGood")
    public @ResponseBody  RespBean deleteGood( String goodsid){
        if(goodsid==null){
            throw new GlobalException(RespBeanEnum.MESSAGE_ERROR);
        }
        RespBean rs = goodFunc.goodDelete(Integer.valueOf(goodsid));
        return rs;
    }
    //商家检查所有秒杀商品并将之放入redis
    @GetMapping("/furnituremall/seller/goods/allsecondkillGoods")
    public @ResponseBody
    RespBean selectsecondkillgoods(){

        RespBean rs = goodFunc.findAll();
        return rs;
    }
    //商品详情
    @GetMapping("/furnituremall/user/goods/selectone")
    public @ResponseBody  RespBean selectgoodsdetial( String goodsid ){
        if(goodsid==null){
            throw new GlobalException(RespBeanEnum.MESSAGE_ERROR);
        }
        RespBean rs = goodFunc.selectGood(Integer.valueOf(goodsid));
        return rs;
    }
    //关键词搜索商品
    @GetMapping("/furnituremall/user/goods/fuzzyselect")
    public @ResponseBody
    RespBean fuzzyselect( String keywords ){
        if(keywords==null){
            throw new GlobalException(RespBeanEnum.MESSAGE_ERROR);
        }
        RespBean rs = goodFunc.fuzzySelectGoods(keywords);
        return rs;
    }
    //类型搜索商品
    @GetMapping("/furnituremall/user/goods/selectType")
    public @ResponseBody
    RespBean selectType(String Type){
        if(Type==null){
            throw new GlobalException(RespBeanEnum.MESSAGE_ERROR);
        }
        RespBean rs = goodFunc.typeSelect(Type);
        return rs;
    }
    //搜索所有商品
    @GetMapping("/furnituremall/user/goods/allGoods")
    public @ResponseBody
    RespBean selectAllgoods(){

        RespBean rs = goodFunc.AllGoods();
        return rs;
    }

    //搜索用户订单
    @GetMapping("/furnituremall/user/goods/selectOrder")
    public @ResponseBody
    RespBean selectOrder(   LoginVo loginVo){
        String openid = loginVo.getOpenId();
        RespBean rs = goodFunc.userOrder(openid);
        return rs;
    }
    //查找所有订单
    @GetMapping("/furnituremall/seller/goods/selectAllOrder")
    public @ResponseBody
    RespBean RespBeanselectAllOrder() {
        RespBean rs = goodFunc.AllOrder();
        return rs;
    }
//    @RequestMapping("/furnituremall/user/goods/buy")
//    public @ResponseBody boolean buygood( String gid,double pay, Model model , LoginVo loginVo){
//        String openid = loginVo.getOpenid();
//        if(gid==null){
//            throw new GlobalException(RespBeanEnum.MESSAGE_ERROR);
//        }
//        model.addAttribute("openid",openid);
//        Order order=new Order(Integer.valueOf(gid),openid,0,null,null);
//        boolean b = goodFunc.buyGood(Integer.valueOf(gid),openid, pay);
//        return b;
//    }
//    @RequestMapping(value = "/{seckillId}/{md5}/execution",
//            method = RequestMethod.POST,
//            produces = {"application/json;charset=UTF-8"})
//    @ResponseBody
//    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") int seckillId,
//                                                   @PathVariable("md5") String md5,
//                                                   @RequestParam("money") double money,
//                                                   LoginVo loginVo) {
//
//        try {
//            SeckillExecution execution = goodFunc.executeSeckill(seckillId,money,loginVo.getOpenid(),md5);
//            return new SeckillResult<SeckillExecution>(true, execution);
//        } catch (RepeatKillException e) {
//            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
//            return new SeckillResult<SeckillExecution>(true, seckillExecution);
//        } catch (SeckillCloseException e) {
//            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.END);
//            return new SeckillResult<SeckillExecution>(true, seckillExecution);
//        } catch (SeckillException e) {
//            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
//            return new SeckillResult<SeckillExecution>(true, seckillExecution);
//        }
//    }
}
