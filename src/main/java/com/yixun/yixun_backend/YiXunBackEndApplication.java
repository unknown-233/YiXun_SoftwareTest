package com.yixun.yixun_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yixun.yixun_backend.mapper")
public class YiXunBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(YiXunBackEndApplication.class, args);
    }

}
