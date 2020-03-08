package cn.xiongyu.bookstore.common.aop;

import java.lang.annotation.*;

/**
 * ClassName: ServiceLimit
 * Package: cn.xiongyu.bookstore.common
 * Description:
 * Date: 2020/3/7 下午1:56
 * Author: xiongyu
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceLimit {
    String description() default "用户限流";
    LimitType limitType() default LimitType.IP;
    enum LimitType {
        IP,
        CUSTOMER;
    }
}
