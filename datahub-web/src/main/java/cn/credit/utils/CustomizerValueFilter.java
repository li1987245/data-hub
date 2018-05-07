package cn.credit.utils;

import com.alibaba.fastjson.serializer.ValueFilter;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * @Author: jinwei.li@100credit.com
 * @Date: 2018/5/2 18:57
 */
public class CustomizerValueFilter implements ValueFilter {
    @Override
    public Object process(Object object, String name, Object value) {
        if(value instanceof Date){
            return new DateTime(value).toString("yyyy-MM-dd");
        }
        else
            return value;
    }
}
