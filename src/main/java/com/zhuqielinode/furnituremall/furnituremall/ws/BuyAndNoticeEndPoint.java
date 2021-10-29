package com.zhuqielinode.furnituremall.furnituremall.ws;

import com.zhuqielinode.furnituremall.furnituremall.config.ApplicationContextRegister;
import com.zhuqielinode.furnituremall.furnituremall.exception.PayNotEnoughException;
import com.zhuqielinode.furnituremall.furnituremall.exception.RepeatKillException;
import com.zhuqielinode.furnituremall.furnituremall.exception.SeckillCloseException;
import com.zhuqielinode.furnituremall.furnituremall.exception.SeckillException;
import com.zhuqielinode.furnituremall.furnituremall.po.Goods;
import com.zhuqielinode.furnituremall.furnituremall.service.GoodFunc;
import com.zhuqielinode.furnituremall.furnituremall.vo.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
//秒杀功能
@ServerEndpoint(value="/{seckillId}/{openid}/{md5}/execution" )
@Component
public class BuyAndNoticeEndPoint {
    private static Map<String, BuyAndNoticeEndPoint> onlineUsers=new ConcurrentHashMap<String, BuyAndNoticeEndPoint>();

    private Session session;
    @OnOpen
    public void OnOpen(Session session, @PathParam("openid")String openid, @PathParam("seckillId")String seckillId) throws IOException {
        this.session=session;

        onlineUsers.put(openid,this);
        session.getBasicRemote().sendText(openid+"连接建立");
        GoodFunc goodFunc =  (GoodFunc) ApplicationContextRegister.getApplicationContext().getBean(GoodFunc.class);

        Integer goodsid = Integer.valueOf(seckillId);
        RespBean rs = goodFunc.selectGood(goodsid);
        Goods goods = (Goods)rs.getObj();
        session.getBasicRemote().sendText(goods.toString());
    }
    @OnMessage
    public void onMessage(String pay, Session session, @PathParam("md5")String md5,@PathParam("openid")String openid, @PathParam("seckillId")String seckillId) throws IOException {
        GoodFunc goodFunc = (GoodFunc) ApplicationContextRegister.getApplicationContext().getBean(GoodFunc.class);
        Integer SeckillId = Integer.valueOf(seckillId);
        Double payMoney = Double.valueOf(pay);
        try {
            SeckillExecution execution = goodFunc.executeSeckill(SeckillId, payMoney, openid, md5);
            if(execution.getState()==1){
                //秒杀成功，通知所有人秒杀结束
                session.getBasicRemote().sendText(execution.toString());
                broadcastAllusers();
            }else{
                //通知失败原因
                session.getBasicRemote().sendText(execution.toString());
            }
        } catch (SeckillException e) {
            if (e.getStatus() == 1) {
            SeckillExecution seckillExecution = new SeckillExecution(SeckillId, SeckillStatEnum.REPEAT_KILL);
            session.getBasicRemote().sendText(new SeckillResult<SeckillExecution>(true, seckillExecution).toString());
         }if (e.getStatus() == 2) {
            SeckillExecution seckillExecution = new SeckillExecution(SeckillId, SeckillStatEnum.END);
            session.getBasicRemote().sendText(new SeckillResult<SeckillExecution>(true, seckillExecution).toString());
         }if (e.getStatus() == 3) {
            SeckillExecution seckillExecution = new SeckillExecution(SeckillId, SeckillStatEnum.PAYNOTENOUGH);
            session.getBasicRemote().sendText(new SeckillResult<SeckillExecution>(true, seckillExecution).toString());
         }if (e.getStatus() == 4) {
            SeckillExecution seckillExecution = new SeckillExecution(SeckillId, SeckillStatEnum.INNER_ERROR);
            session.getBasicRemote().sendText(new SeckillResult<SeckillExecution>(true, seckillExecution).toString());
         }
        }

    }
    public void broadcastAllusers(){
        try{
            Set<String> names = onlineUsers.keySet();
            for(String name :names){
                BuyAndNoticeEndPoint buyAndNoticeEndPoint = onlineUsers.get(name);
                buyAndNoticeEndPoint.session.getBasicRemote().sendText("秒杀结束");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @OnClose
    public void onClose(Session session, @PathParam("openid") String openid) {
        onlineUsers.remove(openid);

    }
}
