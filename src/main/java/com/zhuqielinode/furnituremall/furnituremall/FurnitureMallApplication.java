package com.zhuqielinode.furnituremall.furnituremall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@MapperScan(basePackages = "com.zhuqielinode.furnituremall.furnituremall.dao")//开启扫描mapper接口的包以及子目录
@ServletComponentScan(basePackages ="com.zhuqielinode.furnituremall.furnituremall.ws")
@SpringBootApplication
public class FurnitureMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(FurnitureMallApplication.class, args);
    }

}
