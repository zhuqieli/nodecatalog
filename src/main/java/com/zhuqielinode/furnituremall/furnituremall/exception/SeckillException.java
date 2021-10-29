package com.zhuqielinode.furnituremall.furnituremall.exception;

public class SeckillException extends RuntimeException{
    int status;
    public SeckillException(int status) {
        this.status=status;
    }

    public int getStatus() {
        return status;
    }
}
