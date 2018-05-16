package cn.credit;

import cn.credit.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: jinwei.li@100credit.com
 * @Date: 2018/5/11 12:41
 */
@Slf4j
public class RedisTest {
    @Test
    public void testLock() {
        // 1. Create config object
        Config config = new Config();
        config.useClusterServers().addNodeAddress("192.168.23.157:7360,192.168.23.157:7361,192.168.23.158:7360,192.168.23.159:7360".split(","));
        // 2. Create Redisson instance
        RedissonClient redisson = Redisson.create(config);
        RMap map = redisson.getMap("my-map");
        // 3. Get object you need
        RLock lock = redisson.getLock("myLock");
        try {
            lock.lock();

        } finally {
            lock.unlock();
        }
    }

    @Test
    public void testLock1() throws InterruptedException {
        RedisUtil service = new RedisUtil();
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        long total = 8000;
        service.set("shirt", "800", "1");
        int size = 2000;
        CountDownLatch latch = new CountDownLatch(size);
        for (int i = 0; i < size; i++) {
            executorService.execute(new MultiRunnable(service, latch));
        }
        latch.await();
        long now = Long.parseLong(service.get("shirt"));
        System.out.println("----------end" + now + "---------");
    }

    class MultiRunnable implements Runnable {
        private RedisUtil service;
        private CountDownLatch latch;

        public MultiRunnable(RedisUtil service, CountDownLatch latch) {
            this.service = service;
            this.latch = latch;
        }

        @Override
        public void run() {
            String requestId = String.valueOf(System.currentTimeMillis());
            try {
                service._lock("incrBy", requestId);
                long total = Long.parseLong(service.get("shirt", "1"));
                long minus = -10;
                if (total + minus < 0) {
                    System.out.println("错误，当前数量" + total);
                    latch.countDown();
                    return;
                }
                service.incrBy("shirt", "1", minus);
            } catch (Exception e) {
                log.error("获取锁异常", e);
                latch.countDown();
                return;
            } finally {
                service.unlock("incrBy", requestId);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long plus = 2;
            service.incrBy("shirt", "1", plus);
            latch.countDown();
        }
    }

    @Test
    public void testURI() {
        String clusterNodeStr = "192.168.23.157:7360,192.168.23.157:7361,192.168.23.158:7360,192.168.23.159:7360";
        for (String address : clusterNodeStr.split(",")) {
            URI uri = URI.create("//" + address);
            log.info("schema:{},ip:{},port:{}", uri.getScheme(), uri.getHost(), uri.getPort());
        }
    }
}
