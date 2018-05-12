package cn.credit.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author memoryaxis@gmail.com
 */
public class JedisClusterUtil {

    private static final Logger log = LoggerFactory.getLogger(JedisClusterUtil.class);

    private static JedisClusterUtil instance = null;
    private static Properties redisProperties;

    static {
        redisProperties = new Properties();
        try {
            InputStream in = JedisClusterUtil.class.getClassLoader().getResourceAsStream("redis-config.properties");
            redisProperties.load(in);
            in.close();
        } catch (IOException e) {
            log.error("Read Redis Config File Fail!", e);
        }
    }

    private JedisCluster jedisCluster = null;

    private JedisClusterUtil() {
        initJedisCluster();
    }

    public static JedisClusterUtil getInstance() {
        if (instance == null) {
            synchronized (JedisClusterUtil.class) {
                if (instance == null) {
                    instance = new JedisClusterUtil();
                }
            }
        }
        return instance;
    }

    private void initJedisCluster() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(50);
        config.setMaxIdle(50);
        config.setMinIdle(50);
        config.setMaxWaitMillis(6 * 1000);
        config.setTestOnBorrow(true);

        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        String clusterNodeStr = (String) redisProperties.get("redis.address");
        for (String node : clusterNodeStr.split(",")) {
            String ip = node.split(":")[0];
            int port = Integer.parseInt(node.split(":")[1]);
            log.warn("Init Redis Address:{}:{}", ip, port);
            jedisClusterNodes.add(new HostAndPort(ip, port));
        }
        jedisCluster = new JedisCluster(jedisClusterNodes, 2000, 1000, config);
    }


    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }

}
