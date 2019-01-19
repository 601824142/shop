package com.wan.admin;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication(scanBasePackages = "com.wan")//Spring扫描路径
@MapperScan("com.wan.dao")//Mybatis接口扫描
@DubboComponentScan("com.wan.serviceImpl")//服务管理中心注册扫描
@EnableTransactionManagement//事务管理控制
public class ServiceGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceGoodsApplication.class, args);
    }

}

