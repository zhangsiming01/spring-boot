package com.example.springboot.common.enumutis;

/**
 * @program: spring-boot
 * @description: ${description}
 * @author: zsm
 * @create: 2019-08-19 17:18
 **/
public enum ScheduleStatus {
    STOP("0", "停用"),
    START("1", "启用");

    /**
     * 编码
     */
    private String code;
    /**
     * 描述
     */
    private String description;

    public String getCode() {
        return code;
    }

    public String getCodeToString() {
        return code + "";
    }

    public String getDescription() {
        return description;
    }


    ScheduleStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static ScheduleStatus byCode(String code) {
        if (code == null) {
            return null;
        }
        for (ScheduleStatus value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

    public static ScheduleStatus byDescription(String description) {
        if (description == null) {
            return null;
        }
        for (ScheduleStatus value : values()) {
            if (value.getDescription().equals(description)) {
                return value;
            }
        }
        return null;
    }
}

