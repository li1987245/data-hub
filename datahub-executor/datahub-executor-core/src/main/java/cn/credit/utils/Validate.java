package cn.credit.utils;

/**
 * @Author: jinwei.li@100credit.com
 * @Date: 2018/5/8 10:07
 */
public interface Validate<T> extends Comparable<Validate> {

    ValidateResult doValidate(Object obj);

    void setPriority(int priority);

    int getPriority();

    void setGroup(String group);

    String getGroup();

    void setParam(Class<?> param);

    Class<?> getParam();
}
