package cn.credit.controller;

import cn.credit.api.JobApi;
import cn.credit.entity.JobEntity;
import cn.credit.entity.TaskVo;
import cn.credit.entity.TriggerEntity;
import cn.credit.jobs.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: jinwei.li@100credit.com
 * @Date: 2018/4/28 14:35
 */
@RestController
@Slf4j
public class JobController implements JobApi {
    @Resource
    private SimpleJob simpleJob;

    @Override
    public List<Job> getStores() {
        return null;
    }

    public String put(@RequestParam("swiftNumber") String swiftNumber, @RequestBody TaskVo taskVo) {
        log.info("swift number : {},request param : {}", swiftNumber, taskVo);
        String group = taskVo.getGroup() == null ? "default" : taskVo.getGroup();
        String name = taskVo.getName();
        if (StringUtils.isEmpty(name))
            return taskVo.toString();
        Map<String, Object> data = new HashMap<String, Object>() {
            {
                put("name", name);
            }
        };
        JobEntity entity = new JobEntity().setGroup(group).setName(name).setData(data);
        TriggerEntity trigger = new TriggerEntity().setGroup(group).setName(name).setCron("0 * * * * ?");
        entity.setTrigger(trigger);
        simpleJob.createJob(entity);
        return name;
    }
}
