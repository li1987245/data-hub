package cn.credit.utils;

import javax.servlet.*;

/**
 * @Author: jinwei.li@100credit.com
 * @Date: 2018/5/8 10:07
 */
public interface Validate extends Comparable<Validate> {

    ValidateResult doValidate(Object object);

    void setPriority(int priority);

    int getPriority();

    void setGroup(String group);

    String getGroup();

    void setParam(Class<?> param);

    Class<?> getParam();
}
