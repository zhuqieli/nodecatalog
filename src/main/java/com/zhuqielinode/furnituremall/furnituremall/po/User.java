package com.zhuqielinode.furnituremall.furnituremall.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @NotNull(message = "openId不能为空")
    private String openId;
    @NotNull(message = "电话不能为空")
    private String userTel;
    @NotNull(message = "地址不能为空")
    private String address;

    private String nickName;



}