package com.zhuqielinode.furnituremall.furnituremall.service;

import com.zhuqielinode.furnituremall.furnituremall.dao.UserMapper;
import com.zhuqielinode.furnituremall.furnituremall.exception.GlobalException;
import com.zhuqielinode.furnituremall.furnituremall.po.User;
import com.zhuqielinode.furnituremall.furnituremall.utils.CookieUtil;
import com.zhuqielinode.furnituremall.furnituremall.utils.UUIDUtil;
import com.zhuqielinode.furnituremall.furnituremall.utils.ValidatorUtil;
import com.zhuqielinode.furnituremall.furnituremall.vo.RespBean;
import com.zhuqielinode.furnituremall.furnituremall.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
@Component
public class UserDetailFuncImpl implements UserDetailFunc {
    @Autowired
    private UserMapper mapper;
    @Autowired
    RedisTemplate redisTemplate;

    public void setUserDetailFuncImplDao(UserMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public RespBean insert(User user) {
        if(user==null||user.getUserTel()==""||user.getAddress()==""){
            throw new GlobalException(RespBeanEnum.MESSAGE_ERROR);
        }
        User user1 = mapper.selectByPrimaryKey(user.getOpenId());
        if(user1!=null)
            throw new GlobalException(RespBeanEnum.SAVE_ERROR);
        String userTel=user.getUserTel();
        if(!ValidatorUtil.isMobile(userTel)){
           throw new GlobalException(RespBeanEnum.MOBILE_ERROR);
        }
        int num=mapper.insert(user);
        return RespBean.success();
    }

    @Override
    public RespBean updateUserMessage( String openid,String address,String nickName) {


        User user1 = mapper.selectByPrimaryKey(openid);
        if(user1==null)
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
         if((address==null||address=="")&&nickName==null||nickName=="")//同时为空或者空白
             throw new GlobalException(RespBeanEnum.MESSAGE_ERROR);
         User user=new User();
         user.setOpenId(openid);
         user.setNickName(nickName);
         user.setAddress(address);
        int num=mapper.updateByPrimaryKeySelective(user);
        return RespBean.success();
    }

    @Override
    public RespBean selectUser(String openId , HttpServletRequest request, HttpServletResponse response) {
        if(openId==null){
            throw new GlobalException(RespBeanEnum.MESSAGE_ERROR);
        }
        User user=mapper.selectByPrimaryKey(openId);
        if (user==null)
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        String ticket= UUIDUtil.getUUID();//生成cookie

       request.getSession().setAttribute(ticket,openId);
        redisTemplate.opsForValue().set("user"+ticket,openId);
        CookieUtil.setCookie(request,response,"openId",ticket);
        return RespBean.success();
    }

    @Override
    public String getUserByCookie(String userTicket) {
        if(StringUtils.isEmpty(userTicket)){
            return null;
        }
        String openid = (String)redisTemplate.opsForValue().get("user" + userTicket);

        return openid;
    }

    @Override
    public RespBean selectAndShowUser(String openid) {
        User user = mapper.selectByPrimaryKey(openid);
        RespBean rs = RespBean.success(user);
        return rs;
    }

}
