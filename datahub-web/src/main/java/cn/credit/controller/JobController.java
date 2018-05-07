package cn.credit.controller;

import cn.credit.api.JobApi;
import cn.credit.entity.JobEntity;
import cn.credit.entity.ReqVo;
import cn.credit.entity.TriggerEntity;
import cn.credit.jobs.SimpleJob;
import org.quartz.Job;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: jinwei.li@100credit.com
 * @Date: 2018/4/28 14:35
 */
@RestController
public class JobController implements JobApi {
    @Resource
    private SimpleJob simpleJob;

    @Override
    public List<Job> getStores() {
        return null;
    }

    public String put(@RequestBody  ReqVo reqVo) {
        String group = reqVo.getGroup() == null ? "default" : reqVo.getGroup();
        String name = reqVo.getName();
        if (StringUtils.isEmpty(name))
            return reqVo.toString();
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
