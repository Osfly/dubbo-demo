package com.dourw.web.controller;

/**
 * @program: dubbo
 * @description: 测试controller
 * @author: rwdou
 * @create: 2020-08-11 14:27
 **/


import com.alibaba.dubbo.config.annotation.Reference;
import com.dourw.Iservice.ITest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Satsuki
 * @time 2019/8/27 15:58
 * @description:
 * 调用dubbo
 */
@RestController
public class TestController {

    //    @Resource
//    private TestService testServiceImpl;
    @Reference(version = "1.0.0")
    private ITest testServiceImpl;

    @RequestMapping("/ins")
    public String ins(){
        testServiceImpl.ins();
        return "ins";
    }

    @RequestMapping("/del")
    public String del(){
        testServiceImpl.del();
        return "del";
    }

    @RequestMapping("/upd")
    public String upd(){
        testServiceImpl.upd();
        return "upd";
    }

    @RequestMapping("/sel")
    public String sel(){
        testServiceImpl.sel();
        return "sel";
    }
}

