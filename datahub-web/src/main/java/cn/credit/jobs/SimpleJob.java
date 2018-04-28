package cn.credit.jobs;

import cn.credit.entity.JobEntity;
import cn.credit.service.SimpleService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;


@Slf4j
@Service
public class SimpleJob implements Job{

    @Autowired
    private  Scheduler scheduler;
    @Autowired
    private SimpleService service;

    public void createJob(JobEntity entity) {
        JobDetail jobDetail = entity.buildJobDetail(SimpleJob.class);
        Set<Trigger> triggersForJob = entity.buildTriggers();
        log.info("About to save job with key - {}", jobDetail.getKey());
        try {
            scheduler.scheduleJob(jobDetail, triggersForJob, false);
            log.info("Job with key - {} saved sucessfully", jobDetail.getKey());
        } catch (SchedulerException e) {
            log.error("Could not save job with key - {} due to error - {}", jobDetail.getKey(), e.getLocalizedMessage());
            throw new IllegalArgumentException(e.getLocalizedMessage());
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap dataMap =   jobExecutionContext.getMergedJobDataMap();
        String name = dataMap.getString("name");
        service.processData(name);
    }
}