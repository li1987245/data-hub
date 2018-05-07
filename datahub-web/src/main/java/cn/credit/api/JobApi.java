package cn.credit.api;

import cn.credit.entity.ReqVo;
import org.quartz.Job;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface JobApi {
    @RequestMapping(method = RequestMethod.GET, value = "/jobs")
    List<Job> getStores();

    @RequestMapping(method = RequestMethod.POST, value = "/put")
    String put(@RequestBody ReqVo reqVo);
}