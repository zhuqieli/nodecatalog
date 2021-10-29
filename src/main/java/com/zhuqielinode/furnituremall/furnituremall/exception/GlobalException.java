package com.zhuqielinode.furnituremall.furnituremall.exception;

import com.zhuqielinode.furnituremall.furnituremall.vo.RespBeanEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class GlobalException extends RuntimeException {
    private RespBeanEnum respBeanEnum;
}
