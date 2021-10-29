package com.zhuqielinode.furnituremall.furnituremall.ws;

import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

@Component
public class RequestListener implements ServletRequestListener {
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
    }

    public RequestListener() {

    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {

    }

}