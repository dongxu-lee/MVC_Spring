package com.ldx.demo.Service.impl;

import com.ldx.demo.Service.IDemoService;
import com.ldx.mvcframework.annotations.MyService;

@MyService
public class DemoServiceImpl implements IDemoService {
    @Override
    public String get(String name) {
        System.out.println("service实现类中的name参数" + name);
        return name;
    }
}
