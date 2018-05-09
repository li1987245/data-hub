package cn.credit.aspect;

import cn.credit.utils.Validate;
import cn.credit.utils.ValidateChain;
import cn.credit.annotation.ValidateMethod;
import cn.credit.utils.ValidateResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @Author: jinwei.li@100credit.com
 * @Date: 2018/5/8 16:21
 */
@Aspect
@Component
@Slf4j
public class ValidateAspect {
    @Resource
    private ValidateChain validateChain;

    @Pointcut("@annotation(validateMethod)")
    private void validateMethodPointcut(ValidateMethod validateMethod) {
    }

    @Before("validateMethodPointcut(validateMethod)")
    public void doBefore(ValidateMethod validateMethod) {

    }

    @Around(value = "validateMethodPointcut(validateMethod)")
    public Object interceptor(ProceedingJoinPoint pjp, ValidateMethod validateMethod) throws Throwable {
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        //获取方法注解
//        Method targetMethod = methodSignature.getMethod();
//        Class clazz = targetMethod.getClass();
//        targetMethod.isAnnotationPresent(ValidateMethod.class);
//        Annotation[] annotations = targetMethod.getDeclaredAnnotations();
        String[] groups = validateMethod.group();
        Object[] objects = pjp.getArgs();
        for (Object object : objects) {
            ValidateResult validateResult = validateChain.validate(object, groups);
            log.info(validateResult.result());
        }

        // start stopwatch
        Object retVal = pjp.proceed();
        // stop stopwatch
        return retVal;
    }
}
