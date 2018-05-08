package cn.credit.annotation;

import java.lang.annotation.*;

/**
 * 验证注解，default分组为基础验证类别，全部执行，自定义验证需自定义注解分组
 *
 * @Author: jinwei.li@100credit.com
 * @Date: 2018/5/8 14:43
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateStrategy {
    String group() default "default";

    String value() default "";

    //指定规则针对哪个参数，如未指定，针对所有参数
    Class<?> param();

    //数值越大优先级越高
    int priority() default 0;
}
