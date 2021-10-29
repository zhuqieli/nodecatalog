package com.zhuqielinode.furnituremall.furnituremall.vo;


import com.zhuqielinode.furnituremall.furnituremall.po.Order;

/**
 * 封装执行秒杀后的结果
 *
 * @auther TyCoding
 * @date 2018/10/8
 */
public class SeckillExecution {

    private int seckillId;

    //秒杀执行结果状态
    private int state;

    //状态表示
    private String stateInfo;

    //秒杀成功的订单对象
    private Order order;

    public SeckillExecution(int seckillId, SeckillStatEnum seckillStatEnum, Order order) {
        this.seckillId = seckillId;
        this.state = seckillStatEnum.getState();
        this.stateInfo = seckillStatEnum.getStateInfo();
        this.order = order;
    }

    public SeckillExecution(int seckillId, SeckillStatEnum seckillStatEnum) {
        this.seckillId = seckillId;
        this.state = seckillStatEnum.getState();
        this.stateInfo = seckillStatEnum.getStateInfo();
    }

    public int getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(int seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public Order getSeckillOrder() {
        return order;
    }

    public void setSeckillOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", seckillOrder=" + order +
                '}';
    }
}
