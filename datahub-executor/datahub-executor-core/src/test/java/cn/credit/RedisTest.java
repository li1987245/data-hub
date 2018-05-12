package cn.credit;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.misc.URIBuilder;

import java.net.URI;
import java.net.URLEncoder;

/**
 * @Author: jinwei.li@100credit.com
 * @Date: 2018/5/11 12:41
 */
@Slf4j
public class RedisTest {
    @Test
    public void  testLock(){
        // 1. Create config object
        Config config =new Config();
        config.useClusterServers().addNodeAddress("192.168.23.157:7360,192.168.23.157:7361,192.168.23.158:7360,192.168.23.159:7360".split(","));
        // 2. Create Redisson instance
        RedissonClient redisson = Redisson.create(config);
        RMap map = redisson.getMap("my-map");
        // 3. Get object you need
        RLock lock = redisson.getLock("myLock");
        try {
            lock.lock();

        }
       finally {
            lock.unlock();
        }
    }

    @Test
    public void testURI(){
        String clusterNodeStr = "192.168.23.157:7360,192.168.23.157:7361,192.168.23.158:7360,192.168.23.159:7360";
        for(String address:clusterNodeStr.split(",")) {
            URI uri = URI.create("//"+address);
            log.info("schema:{},ip:{},port:{}",uri.getScheme(),uri.getHost(),uri.getPort());
        }
    }
}
