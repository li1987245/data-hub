package cn.credit.aspect;

import cn.credit.utils.Validate;
import cn.credit.utils.ValidateChain;
import cn.credit.annotation.ValidateMethod;
import cn.credit.utils.ValidateResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @Author: jinwei.li@100credit.com
 * @Date: 2018/5/8 16:21
 */
@Aspect
@Component
public class ValidateAspect {
    @Resource
    private ValidateChain validateChain;

    @Pointcut("@annotation(cn.credit.annotation.ValidateMethod)")
    private void validateMethod() {
    }

    @Before("validateMethod()")
    public void doBefore() {

    }

    @Around("validateMethod()")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        Class clazz = targetMethod.getClass();
        if (clazz.isAnnotationPresent(ValidateMethod.class)) {
            ValidateMethod validateMethod = (ValidateMethod) clazz.getAnnotation(ValidateMethod.class);
            String[] groups = validateMethod.group();
            Object[] objects = pjp.getArgs();
            for (Object object : objects) {
                ValidateResult validateResult = validateChain.validate(object, groups);
            }

        }
        // start stopwatch
        Object retVal = pjp.proceed();
        // stop stopwatch
        return retVal;
    }
}
