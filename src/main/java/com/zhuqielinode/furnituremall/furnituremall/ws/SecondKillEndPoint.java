package com.zhuqielinode.furnituremall.furnituremall.ws;

import com.zhuqielinode.furnituremall.furnituremall.config.ApplicationContextRegister;
import com.zhuqielinode.furnituremall.furnituremall.exception.GlobalException;
import com.zhuqielinode.furnituremall.furnituremall.po.Goods;
import com.zhuqielinode.furnituremall.furnituremall.service.GoodFunc;
import com.zhuqielinode.furnituremall.furnituremall.service.UserDetailFunc;
import com.zhuqielinode.furnituremall.furnituremall.utils.CookieUtil;
import com.zhuqielinode.furnituremall.furnituremall.vo.Exposer;
import com.zhuqielinode.furnituremall.furnituremall.vo.RespBean;
import com.zhuqielinode.furnituremall.furnituremall.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
//秒杀连接，建立连接，定时暴露秒杀url
@ServerEndpoint(value="/furnituremall/user/goods/selectone/{openid}/{gid}",configurator=GetHttpSessionConfigure.class )
@Component
public class SecondKillEndPoint {
    private static Map<String, SecondKillEndPoint> onlineUsers = new ConcurrentHashMap<>();

    private Session session;
    private HttpSession httpSession;
    @Autowired
    private UserDetailFunc userDetailFunc;
    @Autowired
    private GoodFunc goodFunc;

    @Autowired
    public void setUserInfo(UserDetailFunc userDetailFunc, GoodFunc goodFunc
    ) {
        this.goodFunc = goodFunc;
        this.userDetailFunc = userDetailFunc;
    }


    @OnOpen
    public void OnOpen(Session session, @PathParam("openid") String openid, @PathParam("gid") String gid) throws IOException {
        this.session = session;
//        HttpSession httpSession  = (HttpSession) endpointConfig.getUserProperties().get(HttpSession.class.getName());
//        this.httpSession=httpSession;
//        String openid = (String) httpSession.getAttribute("openid");

        onlineUsers.put(openid, this);
        session.getBasicRemote().sendText(openid + "连接建立");
        GoodFunc goodFunc = (GoodFunc) ApplicationContextRegister.getApplicationContext().getBean(GoodFunc.class);

        Integer goodsid = Integer.valueOf(gid);
        RespBean rs = goodFunc.selectGood(goodsid);
        Goods goods = (Goods) rs.getObj();
        session.getBasicRemote().sendText(goods.toString());
    }

    @OnMessage
    public void onMessage(String gid, Session session) throws IOException {
        GoodFunc goodFunc = (GoodFunc) ApplicationContextRegister.getApplicationContext().getBean(GoodFunc.class);
        int goodsid = Integer.valueOf(gid);
        Exposer exposer = goodFunc.exportSeckillUrl(goodsid);
        //定时发送message:gid，查看秒杀是否进行中
        if (exposer.isExposed()) {
            //秒杀开启中，发放链接
            session.getBasicRemote().sendText(exposer.getMd5());
        } else {
            //通知秒杀不在时间范围
            session.getBasicRemote().sendText("不在秒杀时间内");
        }
        // 进行中则获得秒杀链接，通过秒杀链接秒杀,当商品被秒杀后通过http请求websocket通知秒杀结束。


    }

    @OnClose
    public void onClose(Session session, @PathParam("openid") String openid) {
        onlineUsers.remove(openid);

    }
}
