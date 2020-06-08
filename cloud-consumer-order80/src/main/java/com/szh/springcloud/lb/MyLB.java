package com.szh.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//重写Robbin的轮询方法
@Component
public class MyLB implements LoadBalancer {

    private AtomicInteger atomicInteger=new AtomicInteger(0);

    public final int getAndIncrement(){
        int current;
        int next;
        //自循锁,利用compareAndSet方法，如果成功修改就结束，不成功就继续循环尝试
        do {
            current=this.atomicInteger.get();
            next=current>=2147483647?0:current+1;//整型可以展示的最大数字2147483647
        }while (!this.atomicInteger.compareAndSet(current,next));
        System.out.println("*******第几次访问次数：next:"+next);
        return next;
    }
    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        int index = getAndIncrement()% serviceInstances.size();
        return serviceInstances.get(index);
    }
}
