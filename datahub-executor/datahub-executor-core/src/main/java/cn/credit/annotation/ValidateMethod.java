package cn.credit.annotation;

import java.lang.annotation.*;

/**
 * @Author: jinwei.li@100credit.com
 * @Date: 2018/5/8 17:56
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ValidateMethod {
    String[] group() default {"default"};
}
