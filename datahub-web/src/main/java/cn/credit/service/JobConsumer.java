package cn.credit.service;

import cn.credit.api.JobApi;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @Author: jinwei.li@100credit.com
 * @Date: 2018/5/7 14:36
 */
@FeignClient("datahub-web")
public interface JobConsumer extends JobApi {
}
