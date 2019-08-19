package com.example.springboot.aop;

import com.example.springboot.common.enumUtis.ErrorCodeConstants;
import com.example.springboot.common.redis.CacheLock;
import com.example.springboot.common.utis.StringUtils;
import com.example.springboot.common.utis.UnifiedException;
import com.example.springboot.service.CacheKeyGenerator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.lang.reflect.Method;

/**
 * @program: spring-boot
 * @description: redis 方案
 * @author: zsm
 * @create: 2019-08-19 11:17
 **/
@Aspect
@Configuration
public class LockMethodInterceptor {
    private final StringRedisTemplate lockRedisTemplate;
    private final CacheKeyGenerator cacheKeyGenerator;

    @Autowired
    public LockMethodInterceptor(StringRedisTemplate lockRedisTemplate, CacheKeyGenerator cacheKeyGenerator) {
        this.lockRedisTemplate = lockRedisTemplate;
        this.cacheKeyGenerator = cacheKeyGenerator;
    }

    @Around("execution(public * *(..)) && @annotation(com.example.springboot.common.redis.CacheLock)")
    public Object interceptor(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        CacheLock lock = method.getAnnotation(CacheLock.class);
        if (StringUtils.isEmpty(lock.prefix())) {
            throw new UnifiedException(ErrorCodeConstants.REDIS_LOCK);
        }
        final String lockKey = cacheKeyGenerator.getLockKey(point);
        try {
            //key不存在才能设置成功
            final Boolean success = lockRedisTemplate.opsForValue().setIfAbsent(lockKey, "");
            if (success) {
                lockRedisTemplate.expire(lockKey, lock.expire(), lock.timeUnit());
            } else {
                throw new UnifiedException(ErrorCodeConstants.REDIS_LOCK_01);
            }
            try {
                return point.proceed();
            } catch (Throwable throwable) {
                throw new UnifiedException(ErrorCodeConstants.REDIS_LOCK_02);
            }
        } finally {
            //如果演示的话需要注释该代码;实际应该放开
//             lockRedisTemplate.delete(lockKey);
        }
    }
}
