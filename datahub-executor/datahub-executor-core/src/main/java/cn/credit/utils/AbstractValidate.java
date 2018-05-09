package cn.credit.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: jinwei.li@100credit.com
 * @Date: 2018/5/8 10:37
 */
@Data
@Slf4j
public abstract class AbstractValidate<T> implements Validate<T> {
    private int priority;
    private String group;
    private Class<?> param;

    @Override
    public ValidateResult doValidate(Object obj) {
        if (obj == null)
            return new ValidateResult(ResponseCode.ERR_SYSTEM);
        T t = (T) obj;
        return validate(t);
    }

    protected abstract ValidateResult validate(T t);


    @Override
    public int compareTo(Validate o) {
        return this.getPriority() > o.getPriority() ? 1 : -1;
    }
}
