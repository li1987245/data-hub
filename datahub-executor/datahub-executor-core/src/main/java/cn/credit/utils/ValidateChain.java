package cn.credit.utils;

import cn.credit.annotation.ValidateStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 验证逻辑链
 *
 * @Author: jinwei.li@100credit.com
 * @Date: 2018/5/8 10:00
 */
@Slf4j
@Component
public class ValidateChain implements ApplicationContextAware {
    //默认验证策略集
    private List<Validate> defaultValidates;
    //扩展验证策略集
    private Map<String, List<Validate>> additionalValidateMap;

    public ValidateChain() {
        this.defaultValidates = new ArrayList<>();
        this.additionalValidateMap = new HashMap<>();
    }

    /**
     * 对象验证
     *
     * @param obj
     * @return
     */
    public ValidateResult validate(Object obj) {
        return validate(obj, "default");
    }

    /**
     * 对象验证
     *
     * @param obj
     * @param groups
     * @return
     */
    public ValidateResult validate(Object obj, String... groups) {
        ValidateResult result = new ValidateResult();
        List<Validate> validates = new ArrayList<>(defaultValidates);
        for (String group : groups) {
            List<Validate> additionalValidates = additionalValidateMap.get(group);
            if (additionalValidates != null)
                validates.addAll(additionalValidateMap.get(group));
        }
        //按优先级排序
        Collections.sort(validates);
        for (Validate validate : validates) {
            Class<?> cls = validate.getParam();
            //如果param未指定或指定处理该参数
            if (validate.getParam() == null || cls.isInstance(obj)) {
                ValidateResult validateResult = validate.doValidate(obj);
                result.addResult(validateResult);
            }
        }
        return result;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //初始化validate chain
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(ValidateStrategy.class);
        for (Object bean : beans.values()) {
            if (bean instanceof Validate) {
                Validate validate = (Validate) bean;
                ValidateStrategy validateStrategy = AnnotationUtils.findAnnotation(bean.getClass(), ValidateStrategy.class);
                //获取验证策略分组
                String group = validateStrategy.group();
                //参数
                Class<?> param = validateStrategy.param();
                //获取验证策略优先级
                int priority = validateStrategy.priority();
                validate.setGroup(group);
                validate.setParam(param);
                validate.setPriority(priority);
                //设置默认验证策略
                if ("default".equalsIgnoreCase(group)) {
                    defaultValidates.add(validate);
                } else {
                    //增加自定义验证策略
                    List<Validate> additionalValidates;
                    if (additionalValidateMap.containsKey(group)) {
                        additionalValidates = additionalValidateMap.get(group);
                    } else {
                        additionalValidates = new ArrayList<>();
                        additionalValidateMap.put(group, additionalValidates);
                    }
                    additionalValidates.add(validate);
                }
            }
        }
    }

}
