package cn.credit.controller;

import cn.credit.entity.JobEntity;
import cn.credit.entity.TriggerEntity;
import cn.credit.jobs.SimpleJob;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jinwei.li@100credit.com
 * @Date: 2018/4/28 14:35
 */
@RestController
public class JobController {
    @Resource
    private SimpleJob simpleJob;

    @RequestMapping("addJob")
    public  String addJob(String name){
        Map<String,Object> data = new HashMap<String, Object>(){
            {
                put("name",name);
            }
        };
        JobEntity entity = new JobEntity().setGroup("default").setName(name).setData(data);
        TriggerEntity trigger = new TriggerEntity().setGroup("default").setName(name).setCron("0 * * * * ?");
        entity.setTrigger(trigger);
        simpleJob.createJob(entity);
        return  name;
    }
}
