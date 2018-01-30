package org.orclight.encryption.enums;

/**
 * @author maoyingli
 * @date 2018/1/11 16:55
 */
public enum AesCipherAlgorithmEnum {
    ECB_PKCS5PADDING("AES/ECB/PKCS5Padding"),
    CBC_PKCS5PADDING("AES/CBC/PKCS5Padding"),
    //PKCS7Padding java默认不支持改模式，但经测试 PKCS7Padding 加密的可以用 PKCS5Padding 解密 反之亦然
    CBC_PKCS7PADDING("AES/CBC/PKCS7Padding"),
    ;
    private String cipherAlgorithm;

    public String getCipherAlgorithm() {
        return cipherAlgorithm;
    }

    AesCipherAlgorithmEnum(String cipherAlgorithm) {
        this.cipherAlgorithm = cipherAlgorithm;
    }

}
