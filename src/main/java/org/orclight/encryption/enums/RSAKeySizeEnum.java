package org.orclight.encryption.enums;

/**
 * @author lmy
 */
public enum RSAKeySizeEnum {
    size512(512),
    size1024(1024),
    size2048(2048);
    private int value;

    public int value() {
        return value;
    }

    RSAKeySizeEnum(int value) {
        this.value = value;
    }
}
