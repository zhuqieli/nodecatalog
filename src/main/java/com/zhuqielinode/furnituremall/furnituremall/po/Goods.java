package com.zhuqielinode.furnituremall.furnituremall.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goods {
    @NotNull(message = "商品编号不能为空")
    private Integer goodsid;

    private String goodsname;

    private Double goodsprice;

    private String goodsimage;

    private String goodstype;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date starttime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endtime;
    private boolean status;

}