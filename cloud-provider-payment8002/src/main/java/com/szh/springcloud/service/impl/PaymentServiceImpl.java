package com.szh.springcloud.service.impl;


import com.szh.springcloud.dao.PaymentDao;
import com.szh.springcloud.entities.Payment;
import com.szh.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {

    //@Autowired
    @Resource//java自带的依赖注入注解
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
