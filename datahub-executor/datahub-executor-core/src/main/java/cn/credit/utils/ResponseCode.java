package cn.credit.utils;

/** 返回值编号与信息枚举
 * @author Wang Weiwei <email>weiwei02@vip.qq.com / weiwei.wang@100credit.com</email>
 * @version 1.0
 * @sine 2017/12/31
 */
public enum ResponseCode {
    SUCC("00", "成功"),
    ERR_NULL("100003", "必选key值缺失或不合法"),
    ERR_PARAM("100006", "请求参数格式错误"),
    MISS_RESULT("100002", "匹配结果为空"),
    MISS_JSON("1000016", "捕获请求json异常，无法解析的错误"),
    ERR_ID_CARD("800001", "身份证号码错误，请重试"),
    ERR_PHONE("800002", "手机号码错误，请重试"),
    ERR_NAME("800003", "姓名错误，请重试"),
    ERR_DATE_FORMAT("800004", "日期格式错误，请重试"),
    ERR_RELATION_FLAG("800005", "关联拨打标记错误，请确认"),
    ERR_TUOMIN_FLAG("800006", "数据脱敏展示标记错误，请重试"),
    ERR_OVER_DUE_FLAG("800007", "逾期标记错误，请重试"),
    ERR_LINK_NAME("800008", "联系人姓名错误，请重试"),
    ERR_LINK_PHONE("800009", "联系人手机号错误，请重试"),
    ERR_PERMISSION("800010", "无接口权限，请联系客服"),
    ERR_PERMISSION_PHONE("800012", "该apicode无查询该流水权限，请联系客服"),
    ERR_LINK_SIZE("800013", "最多只能有3个联系人，请重试"),
    ERR_LINK_PHONE_SIZE("800014", "最多只能有3个联系人电话，请重试"),
    ERR_PERMISSION_NUMBER("800015", "可用条数不足，请确认，若需要请联系客服"),
    TASK_RUN("800016", "任务正在进行中，请稍后再查询"),
    NO_TASK("800017", "该流水不存在，请校验后再查询"),
    ERR_LINK_PHONE_FORMAT("800018", "联系人格式错误，请重试"),
    TASK_TO_MANY("800019", "当前任务过多，请稍后再查询"),
    PARSE_ID_CARD_ERR("800020", "身份证号码解密失败，请重试"),
    PARSE_PHONE_ERR("800021", "手机号码解密失败，请重试"),
    ERR_SYSTEM("999998", "接口异常，请联系客服"),
    ERR_PHONE_BUG("999999", "电话虫拨打异常，请联系客服"),
    //批量处理返回码
    //SUC_BATCH_PUT("800020","批量上传成功"),
    ERR_BATCH_COUNT("800021","批量上传的数量超过2000"),
    ERR_BATCH_ID("800022","批量上传的编号为空或无编号"),
    ERR_BATCH_PUT("800023","批量上传全部失败"),
    ERR_BATCH_PUT_PART("800024","批量上传部分失败"),
    //SUC_BATCH_QUERY("800024","批量查询成功"),
    ERR_BATCH_QUERY("800025","批量查询失败"),
    NO_RECORDS("800027","流水号不存在或无记录"),
    ERROR_DELETE_TIME("800028","数据删除时间小于当前时间"),

    // 策略贷中专属状态码
    ERROR_PASS_DATE_TOO_BIGER("800100", "审批通过日大于当前时间"),
    ERROR_MATURITY_DATE_TOO_BIGER("800101", "审批通过日大于贷款到期日"),
    STRATEGY_STOPED("800102", "贷中策略已被禁用"),
    ERROR_STRATEGR_ID("800103", "贷中策略编号错误"),
    MISS_STRATEGR_ID("800104", "贷中策略编号不存在"),
    MISS_DTB_STRATEGR_ID("800106", "数据策略编号不存在"),
    NO_SWIFT_NUMBER_PERMISSION("800105", "无该流水查询权限"),
    ERROR_PERMISSION_STATUS("800051","策略贷中状态为不可用"),
    ERROR_PERMISSION_ENDDATE("800052","超过策略贷中截止时间"),
    ERROR_START_RULE("800053","开启策略贷中规则集异常"),

    ERROR_PERMISSION_RELATION("800050", "无号码关联核查权限");





    private final String code;
    private final String message;

    private ResponseCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
