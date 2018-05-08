package cn.credit.api;

import cn.credit.entity.TaskVo;
import org.quartz.Job;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface JobApi {
    @RequestMapping(method = RequestMethod.GET, value = "/jobs")
    List<Job> getStores();

    @RequestMapping(method = RequestMethod.POST, value = "/put/{swiftNumber}")
    String put(@RequestParam("swiftNumber") String swiftNumber, @RequestBody TaskVo taskVo);
}