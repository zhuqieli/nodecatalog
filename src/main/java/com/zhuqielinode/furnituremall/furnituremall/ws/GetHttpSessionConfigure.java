package com.zhuqielinode.furnituremall.furnituremall.ws;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class GetHttpSessionConfigure extends ServerEndpointConfig.Configurator{
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        super.modifyHandshake(sec, request, response);
//        HttpSession httpSession = (HttpSession) request.getHttpSession();
//        sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
//        int i=0;
    }
}
