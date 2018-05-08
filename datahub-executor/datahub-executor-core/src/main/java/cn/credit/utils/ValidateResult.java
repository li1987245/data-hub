package cn.credit.utils;

import lombok.Data;

import java.util.List;

/**
 * @Author: jinwei.li@100credit.com
 * @Date: 2018/5/8 10:46
 */
@Data
public class ValidateResult {
    private String code;
    private String message;
    private List<ValidateResult> validateResults;

    public void addResult(ValidateResult validateResult) {
        validateResults.add(validateResult);
    }
}
