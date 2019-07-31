package com.java.kafkainfluxdb;


public enum ResultEnum {
    SUCCESS("00000000", "成功"),
    BIZ_EXCEPTION("00000006", "业务异常"),
    ;

    private String code;

    private String desc;

    ResultEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
