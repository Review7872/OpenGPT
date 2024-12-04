package online.fadai.opengptproject.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "cache")
@Configuration
@Data
public class CacheConfiguration {
    /**
     * 缓存初始容量
     */
    private int initialCapacity = 10;

    /**
     * 缓存最大容量
     */
    private long maximumSize = 100;

    /**
     * 写后过期时间（单位：秒）
     */
    private long expireAfterWrite = 30;

    /**
     * 访问后过期时间（单位：秒）
     */
    private long expireAfterAccess = 30;

    /**
     * 是否开启统计功能
     */
    private boolean recordStats = false;

}
