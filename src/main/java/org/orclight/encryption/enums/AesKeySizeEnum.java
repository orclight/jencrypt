package org.orclight.encryption.enums;

/**
 * @author lmy
 */
public enum AesKeySizeEnum {
    //128位 java默认仅支持 16字节的aes加密，使用24,32字节的需要修改jdk 文件
    size128(128),
    //192位
    size192(192),
    //256位
    size256(256);
    //字符串长度
    private int value;

    public int value() {
        return value;
    }

    AesKeySizeEnum(int value) {
        this.value = value;
    }
}
