package com.zhuqielinode.furnituremall.furnituremall.config;

import com.zhuqielinode.furnituremall.furnituremall.exception.GlobalException;
import com.zhuqielinode.furnituremall.furnituremall.service.UserDetailFunc;
import com.zhuqielinode.furnituremall.furnituremall.utils.CookieUtil;
import com.zhuqielinode.furnituremall.furnituremall.vo.LoginVo;
import com.zhuqielinode.furnituremall.furnituremall.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
@Component
public class UserArgumentResolve implements HandlerMethodArgumentResolver {
    @Autowired
    private UserDetailFunc userDetailFunc;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> parameterType = parameter.getParameterType();
        return parameterType== LoginVo.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        String ticket = CookieUtil.getCookieValue(request, "openId");
        if(StringUtils.isEmpty(ticket)){
            throw new GlobalException(RespBeanEnum.NOT_LOGIN);
        }
        String openid = userDetailFunc.getUserByCookie(ticket);
        if(openid==null){
            throw new GlobalException(RespBeanEnum.NOT_LOGIN);
        }
        LoginVo loginVo = new LoginVo();
        loginVo.setOpenId(openid);
        return loginVo;
    }
}
