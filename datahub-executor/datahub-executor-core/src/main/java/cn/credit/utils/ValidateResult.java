package cn.credit.utils;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jinwei.li@100credit.com
 * @Date: 2018/5/8 10:46
 */
@Data
public class ValidateResult {
    private String identify;
    private String code;
    private String message;
    private List<ValidateResult> validateResults;

    public ValidateResult() {
        validateResults=new ArrayList<>();
    }

    public ValidateResult(ResponseCode responseCode) {
        this();
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    public ValidateResult(String identify, ResponseCode responseCode) {
        this();
        this.identify = identify;
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    public void addResult(ValidateResult validateResult) {
        validateResults.add(validateResult);
    }

    public String result() {
        return JSON.toJSONString(this);
    }
}
