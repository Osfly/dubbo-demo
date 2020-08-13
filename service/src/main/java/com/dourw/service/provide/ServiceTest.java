package com.dourw.service.provide;

import com.alibaba.dubbo.config.annotation.Service;
import com.dourw.Iservice.ITest;
import org.springframework.stereotype.Component;

/**
 * @program: dubbo
 * @description: proviide测试
 * @author: rwdou
 * @create: 2020-08-11 14:18
 **/

@Service(interfaceClass = ITest.class,version = "1.0.0")
@Component
public class ServiceTest implements ITest {
    @Override
    public void ins() {
        System.out.println("添加成功");
    }

    @Override
    public void del() {
        System.out.println("删除成功");
    }

    @Override
    public void upd() {
        System.out.println("更新成功");
    }

    @Override
    public void sel() {
        System.out.println("查询成功");
    }
}
