package com.ldx.demo.Controller;

import com.ldx.demo.Service.IDemoService;
import com.ldx.mvcframework.annotations.MyAutowired;
import com.ldx.mvcframework.annotations.MyController;
import com.ldx.mvcframework.annotations.MyRequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MyController
@MyRequestMapping("/demo")
public class DemoController {

    @MyAutowired
    private IDemoService demoService;

    @MyRequestMapping("/query")
    public String query(HttpServletRequest request, HttpServletResponse response, String name) {
        return demoService.get(name);
    }

}
