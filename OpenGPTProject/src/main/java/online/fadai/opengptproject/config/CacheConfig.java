package online.fadai.opengptproject.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.Resource;
import online.fadai.opengptproject.repository.es.pojo.MsgIndex;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {
    @Resource
    private CacheConfiguration cacheConfiguration;

    @Bean
    public Cache<String, List<MsgIndex>> caffeineCacheBuilder() {
        Caffeine<String, List<MsgIndex>> builder = Caffeine.newBuilder()
                //初始数量
                .initialCapacity(cacheConfiguration.getInitialCapacity())
                //最大条数
                .maximumSize(cacheConfiguration.getMaximumSize())
                //expireAfterWrite和expireAfterAccess同时存在时，以expireAfterWrite为准
                //最后一次写操作后经过指定时间过期
                .expireAfterWrite(cacheConfiguration.getExpireAfterWrite(), TimeUnit.SECONDS)
                //最后一次读或写操作后经过指定时间过期
                .expireAfterAccess(cacheConfiguration.getExpireAfterAccess(), TimeUnit.SECONDS)
                //监听缓存被移除
                .removalListener((key, val, removalCause) -> {

                });
        if (cacheConfiguration.isRecordStats()) {
            //记录命中
            builder.recordStats();
        }
        return builder.build();
    }
}
