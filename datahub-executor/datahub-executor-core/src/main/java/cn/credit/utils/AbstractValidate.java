package cn.credit.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: jinwei.li@100credit.com
 * @Date: 2018/5/8 10:37
 */
@Data
@Slf4j
public abstract class AbstractValidate implements Validate, Comparable<Validate> {
    private int priority;
    private String group;
    private Class<?> param;

    @Override
    public ValidateResult doValidate(Object object) {

        return null;
    }


    @Override
    public int compareTo(Validate o) {
        return this.getPriority() > o.getPriority() ? 1 : -1;
    }
}
