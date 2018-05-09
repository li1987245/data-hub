package cn.credit.service;

import cn.credit.annotation.ValidateMethod;
import cn.credit.entity.ReqVo;
import cn.credit.entity.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: jinwei.li@100credit.com
 * @Date: 2018/5/8 16:07
 */
@Service
@Slf4j
public class ExecutorService {


    @ValidateMethod(group = {"task"})
    public void execute(ReqVo reqVo, Task task) {
        log.info("任务流水：{}，任务详情：{}", reqVo, task);
    }
}
