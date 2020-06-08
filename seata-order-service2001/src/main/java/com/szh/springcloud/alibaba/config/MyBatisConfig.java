package com.szh.springcloud.alibaba.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.szh.springcloud.alibaba.dao"})
public class MyBatisConfig {
}
