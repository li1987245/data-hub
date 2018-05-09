package cn.credit;

import cn.credit.config.ValidateConfig;
import cn.credit.entity.ReqVo;
import cn.credit.entity.Task;
import cn.credit.service.ExecutorService;
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
@SpringBootTest(classes = ValidateConfig.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ValidateTest {
    @Resource
    private ExecutorService executorService;

    @Test
    public void validate() {
        ReqVo reqVo = new ReqVo();
        Task task = new Task();
        task.setName("admin");
        executorService.execute(reqVo, task);
    }
}
