package com.credit;

import cn.credit.Application;
import cn.credit.entity.ReqVo;
import cn.credit.service.JobConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @Author: jinwei.li@100credit.com
 * @Date: 2018/5/7 14:38
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class JobTest {
    @Resource
    private JobConsumer jobConsumer;
    @Test
    public void put() {
        ReqVo reqVo = new ReqVo();
        reqVo.setName("admin");
        String str = jobConsumer.put(reqVo);
        System.out.println(str);
    }
}
