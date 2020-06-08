package com.szh.springcloud.alibaba.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.szh.springcloud.entities.CommonResult;
import com.szh.springcloud.entities.Payment;

public class CustomerBlockHandler {
    public static CommonResult handlerException(BlockException exception){
        return new  CommonResult(444,"用户自定义,global handlerException----1");
    }
    public static CommonResult handlerException2(BlockException exception){
        return new  CommonResult(444,"用户自定义,global handlerException----2");
    }
}
