package online.fadai.opengptproject.task;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import lombok.extern.slf4j.Slf4j;
import online.fadai.opengptproject.repository.es.pojo.MsgIndex;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(prefix = "cache", name = "record-stats", havingValue = "true")
@Slf4j
public class CacheStatsTask {
    /**
     * 命中打印时间间隔
     */
    public static final Integer STU_TIME = 300000;

    private final Cache<String, List<MsgIndex>> caffeineCache;

    public CacheStatsTask(Cache<String, List<MsgIndex>> caffeineCache) {
        this.caffeineCache = caffeineCache;
        log.info("打印 caffeine 缓存命中功能已开启，间隔时间为 {} 秒", STU_TIME / 60 / 1000);
    }

    @Scheduled(fixedRate = 300000) // 每5分钟执行一次，单位为毫秒
    public void printCacheStats() {
        CacheStats stats = caffeineCache.stats();

        // 构建日志信息
        String logMessage = String.format("""
                                                
                        === 缓存命中情况 ===
                        缓存请求次数: %d
                        缓存命中次数: %d
                        缓存命中率: %.4f
                        缓存加载成功次数: %d
                        缓存加载失败次数: %d
                        =================""",
                stats.requestCount(),
                stats.hitCount(),
                stats.hitRate(),
                stats.loadSuccessCount(),
                stats.loadFailureCount());

        // 一次性打印所有信息
        log.info(logMessage);
    }
}
