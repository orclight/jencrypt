package org.orclight.encryption.enums;

/**
 * 数据转成string时的转换方式
 *
 * @author maoyingli
 * @date 2018/1/12 12:13
 */
public enum CodecEnum {
    HEX("hex"),
    Base64("base64");

    private String name;

    CodecEnum(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.name;
    }
}
