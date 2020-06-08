package com.szh.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping("/testA")
    public String testA(){
        //try{ TimeUnit.MILLISECONDS.sleep(800); }catch (InterruptedException e){e.printStackTrace();}
        return "--------testA";
    }

    @GetMapping("/testB")
    public String testB(){
        log.info(Thread.currentThread().getName()+"\t"+"testB");
        return "--------testB";
    }

    @GetMapping("/testC")
    public String testC(){
//        try{ TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){e.printStackTrace();}
//        log.info("testC 测试RT");
        log.info("testC 测试异常比例");
        int i=10/0;
        return "--------testC";
    }

    @GetMapping("/testD")
    public String testD(){
        log.info("testC 测试异常数");
        int i=10/0;
        return "--------testD";
    }

    @GetMapping("/testHotKey")
    @SentinelResource(value ="testHotKey",blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value = "p1",required = false) String p1,
                             @RequestParam(value = "p1",required = false) String p2){
        return "------testHotKey";
    }
    public String deal_testHotKey(String p1, String p2, BlockException exception){
        return "------deal_testHotKey,╥﹏╥...";
    }
}
