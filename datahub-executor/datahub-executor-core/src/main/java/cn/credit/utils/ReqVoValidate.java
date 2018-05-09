package cn.credit.utils;

import cn.credit.annotation.ValidateStrategy;
import cn.credit.entity.ReqVo;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Author: jinwei.li@100credit.com
 * @Date: 2018/5/9 14:54
 */
@ValidateStrategy(group = "task", param = ReqVo.class)
@Component
public class ReqVoValidate extends AbstractValidate<ReqVo> {
    @Override
    protected ValidateResult validate(ReqVo reqVo) {
        if (StringUtils.isEmpty(reqVo.getSwiftNumber())) {
            return new ValidateResult(ResponseCode.ERR_SYSTEM);
        }
        return null;
    }
}
