package com.szh.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String paymentInfo_OK(Integer id){
        return "线程池：  "+Thread.currentThread().getName()+"  paymentInfo_OK,id:  "+id;
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="5000")
    }) //设置程序出现问题由哪个方法来替代,设置方法超时时间，超时后降级
    public String paymentInfo_TimeOut(Integer id){
        int timeNumber=3;
        //延时，演示延时错误
        try {TimeUnit.SECONDS.sleep(timeNumber);} catch (InterruptedException e) { e.printStackTrace(); }
        //测试其他错误
        //int i=1/0;
        return "线程池：  "+Thread.currentThread().getName()+"  paymentInfo_timeout,id:  "+id+"\t"+"  耗时:  "+timeNumber;
    }
    //服务降级替代方法
    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池：  "+Thread.currentThread().getName()+"   8001系统异常或者服务异常请稍后再试  ,id:  "+id;
    }
    //----------------服务熔断   方法默认参数HystrixCommandProperty
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"), // 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")// 失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if (id<0){
            throw new RuntimeException("*********id不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();//利用糊涂工具包生成uuid
        return Thread.currentThread().getName()+"\t"+"调用成功，流水号："+serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id不能为负数，请稍后再试，/(ㄒoㄒ)/~~   id:"+id;
    }
}
