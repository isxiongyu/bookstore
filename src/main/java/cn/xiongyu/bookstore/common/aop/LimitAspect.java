package cn.xiongyu.bookstore.common.aop;

import cn.xiongyu.bookstore.common.ip.IPUtils;
import cn.xiongyu.bookstore.exception.LimitException;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import jdk.nashorn.internal.ir.JoinPredecessor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: LimitAspect
 * Package: cn.xiongyu.bookstore.common.aop
 * Description:
 * Date: 2020/3/7 下午2:02
 * Author: xiongyu
 */
@Aspect
@Configuration
public class LimitAspect {
    LoadingCache<String, RateLimiter> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(1, TimeUnit.DAYS)
            .build(new CacheLoader<String, RateLimiter>() {
                @Override
                public RateLimiter load(String key) throws Exception {
                    return RateLimiter.create(2);
                }
            });
    @Pointcut("@annotation(cn.xiongyu.bookstore.common.aop.ServiceLimit)")
    public void ServiceAspect() {

    }
    @Around("ServiceAspect() && @annotation(serviceLimit)")
    public Object around(ProceedingJoinPoint joinPoint, ServiceLimit serviceLimit) throws LimitException {
        ServiceLimit.LimitType limitType = serviceLimit.limitType();
        String key = "";
        Object obj = null;
        if (limitType.equals(ServiceLimit.LimitType.IP)) {
            key = IPUtils.getIpAddr();
            System.out.println(key);
        }
        try {
            RateLimiter rateLimiter = cache.get(key);
            boolean flag = rateLimiter.tryAcquire();
            if (flag) {
                obj = joinPoint.proceed();
            } else {
                throw new LimitException("您访问的太频繁了，请稍后访问");
            }
        } catch (Throwable throwable) {
            throw new LimitException("您访问的太频繁了，请稍后访问");
        }
        return obj;
    }
}
