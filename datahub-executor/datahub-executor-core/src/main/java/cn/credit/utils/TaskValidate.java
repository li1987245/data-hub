package cn.credit.utils;

import cn.credit.annotation.ValidateStrategy;
import cn.credit.entity.Task;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Author: jinwei.li@100credit.com
 * @Date: 2018/5/9 10:02
 */
@ValidateStrategy(group = "task", param = Task.class)
@Component
public class TaskValidate extends AbstractValidate<Task> {
    @Override
    protected ValidateResult validate(Task task) {
        ValidateResult result = new ValidateResult();
        if (StringUtils.isEmpty(task.getName())) {
            result.addResult(new ValidateResult(ResponseCode.ERR_LINK_NAME));
        }
        if (StringUtils.isEmpty(task.getCommand())) {
            result.addResult(new ValidateResult(ResponseCode.ERR_LINK_NAME));
        }
        if (StringUtils.isEmpty(task.getStartTime()) || !task.getStartTime().matches("^\\d{4}-\\d{2}-\\d{2}")) {
            result.addResult(new ValidateResult(ResponseCode.ERR_DATE_FORMAT));
        }
        return result;
    }
}
