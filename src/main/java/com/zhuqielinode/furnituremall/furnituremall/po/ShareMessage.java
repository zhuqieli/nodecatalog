package com.zhuqielinode.furnituremall.furnituremall.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShareMessage {

    @NotNull
    private Integer messageId;

    private String shareImage;
    @NotNull
    private String shareMessage;

    private Integer hitCount;
    @NotNull
    private String openId;
    private String goodsId;

}