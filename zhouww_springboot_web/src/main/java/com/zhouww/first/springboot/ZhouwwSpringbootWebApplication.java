package com.zhouww.first.springboot;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.ws.Service;

@SpringBootApplication(scanBasePackages = {"com.zhouww.first.springboot"})
@MapperScan("com.zhouww.first.modules.dao")
public class ZhouwwSpringbootWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhouwwSpringbootWebApplication.class, args);
    }

}
