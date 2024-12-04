package online.fadai.opengptproject.aspect;

import com.github.benmanes.caffeine.cache.Cache;
import jakarta.annotation.Resource;
import online.fadai.opengptproject.enums.RoleType;
import online.fadai.opengptproject.repository.es.pojo.MsgIndex;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Aspect
@Component
public class CacheProxy {
    @Resource
    private Cache<String, List<MsgIndex>> cache;

    /**
     * 删除缓存
     */
    //@Before("execution(* online.fadai.opengptproject.service.impl.MsgServiceImpl.save(..))")
    public void removeCache(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String uuid = (String) args[0];
        cache.invalidate(uuid);
    }

    @Around("execution(* online.fadai.opengptproject.service.impl.MsgServiceImpl.findByUuid(..))")
    public List<MsgIndex> getCache(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String uuid = (String) args[0];

        // 先从缓存中获取数据
        List<MsgIndex> cachedData = cache.getIfPresent(uuid);
        if (cachedData != null) {
            // 如果缓存中有数据，则直接返回
            return cachedData;
        }

        // 执行目标方法并获取结果
        Object result = joinPoint.proceed();

        // 检查类型是否正确
        if (result instanceof List<?>) {
            @SuppressWarnings("unchecked")
            List<MsgIndex> resultList = (List<MsgIndex>) result;

            if (!resultList.isEmpty()) {
                // 将方法结果放入缓存
                cache.put(uuid, resultList);
            }

            return resultList;
        } else {
            throw new ClassCastException("Expected a List<MsgIndex>, but got " + result.getClass());
        }
    }

    /**
     * 更新缓存而不是删除的策略
     */
    @After("execution(* online.fadai.opengptproject.service.impl.MsgServiceImpl.save(..))")
    public void updateCache(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String uuid = (String) args[0];
        RoleType roleType = (RoleType) args[1];
        String content = (String) args[2];
        List<MsgIndex> msgs = cache.getIfPresent(uuid);
        if (msgs != null) {
            msgs.add(new MsgIndex(UUID.randomUUID().toString(), uuid, roleType.getRoleType(), content, System.currentTimeMillis()));
            cache.put(uuid, msgs);
        } else {
            msgs = new ArrayList<>();
            msgs.add(new MsgIndex(UUID.randomUUID().toString(), uuid, roleType.getRoleType(), content, System.currentTimeMillis()));
            cache.put(uuid, msgs);
        }
    }
}
