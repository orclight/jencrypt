package org.orclight.encryption.symmetric;

import org.orclight.encryption.codec.Base64Algorithm;
import org.orclight.encryption.codec.Hex;
import org.orclight.encryption.enums.AesCipherAlgorithmEnum;
import org.orclight.encryption.enums.AesKeySizeEnum;
import org.orclight.encryption.enums.CodecEnum;
import org.orclight.encryption.util.EncryptStringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Nothing seek, Nothing find.
 * author: shuzhilong
 * Date: 2017/12/29 下午2:41
 * desc: (The role of this class is to ...)
 * To change this template use preferences | editor | File and code Templates
 */
public class AESAlgorithm {

    //密钥算法
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String DEFAULT_ENCODING = "UTF-8";

    private static HashSet<String> encodingSet = new HashSet<>();
    private ConcurrentHashMap<String, SecretKeySpec> keyMap = new ConcurrentHashMap<>();

    static {
        encodingSet.add("UTF-8");
    }

    //格式：加密算法/工作模式/填充方式
    private String cipherAlgorithm;

    //加密和解密过程中的编码格式
    private String encodingCharset;

    //加密key的长度;分别是16/24/32字节长度
    private int keyLength;

    //byte转成string的方式 支持 hex/base64
//    private int transStringMode;

    private CodecEnum codec;

    private AESAlgorithm(String cipherAlgorithm, String encodingCharset, AesKeySizeEnum keySize, CodecEnum codec) {
        this.cipherAlgorithm = cipherAlgorithm;
        this.encodingCharset = encodingCharset;
        this.codec = codec;
        //加密key的长度;分别是16/24/32字节长度
        keyLength = keySize.value() >> 3;
    }

    /**
     * 加密
     *
     * @param data       待加密数据
     * @param encryptKey 密钥
     * @return 加密数据
     */
    public String encrypt(String data, String encryptKey) throws Exception {
        return encrypt(data, encryptKey, null);
    }

    /**
     * 加密
     *
     * @param data       待加密数据
     * @param encryptKey 密钥
     * @return 加密数据
     */
    public String encrypt(String data, String encryptKey, String ivString) throws Exception {
        String result;
        if (encryptKey.length() < keyLength) {
            encryptKey = zeroPadding(encryptKey);
        }
        if (encryptKey.length() > keyLength) {
            encryptKey = encryptKey.substring(0, keyLength);
        }
        checkParams("encrypt", data, encryptKey);
        try {
            Cipher cipher = buildCipher(encryptKey, ivString, Cipher.ENCRYPT_MODE);
            //把文本转换成byte
            byte[] dataBytes = data.getBytes(encodingCharset);
            //加密
            byte[] encryptData = cipher.doFinal(dataBytes);
            //把加密的byte转成密文
            if (codec == CodecEnum.HEX) {
                result = Hex.encode2Str(encryptData);
            } else {
                result = Base64Algorithm.encode(encryptData,encodingCharset);
            }
        } catch (UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }

    /**
     * 解密
     *
     * @param data       待解密数据
     * @param encryptKey 密钥
     * @return byte[]   解密数据
     * @throws Exception
     */
    public String decrypt(String data, String encryptKey) {
        return decrypt(data, encryptKey, null);
    }

    /**
     * 解密
     *
     * @param data       待解密数据
     * @param encryptKey 密钥
     * @return byte[]   解密数据
     * @throws Exception
     */
    public String decrypt(String data, String encryptKey, String ivString) {
        String result;
        if (encryptKey.length() < keyLength) {
            encryptKey = zeroPadding(encryptKey);
        }
        if (encryptKey.length() > keyLength) {
            encryptKey = encryptKey.substring(0, keyLength);
        }
        //验证data 和 encryptKey
        checkParams("decrypt", data, encryptKey);

        try {
            Cipher cipher = buildCipher(encryptKey, ivString, Cipher.DECRYPT_MODE);
            //把密文转成byte
            byte[] dataBytes;
            if (codec == CodecEnum.HEX) {
                dataBytes = Hex.decode(data);
            } else {
                dataBytes = Base64Algorithm.decode2Byte(data,encodingCharset);
            }
            //解密
            byte[] decodeBytes = cipher.doFinal(dataBytes);
            //把解密得到的byte转成文本
            result = new String(decodeBytes, encodingCharset);
        } catch (Exception e) {
            throw new RuntimeException("[AESUtil] [decrypt] error " + e.getMessage(), e);
        }
        return result;
    }

    private String zeroPadding(String encryptKey) {
        StringBuilder encryptKeyBuilder = new StringBuilder(encryptKey);
        for (int i = 0; i < keyLength - encryptKey.length(); i++) {
            encryptKeyBuilder.append("0");
        }
        return encryptKeyBuilder.toString();
    }

    public void checkParams(String method, String data, String encryptKey) {
        //验证data 和 encryptKey
        if (EncryptStringUtils.isBlank(data)) {
            throw new RuntimeException("[AESAlgorithm]" + method + "[decrypt] data is null");
        }

        if (EncryptStringUtils.isBlank(encryptKey)) {
            throw new RuntimeException("[AESAlgorithm]" + method + "encryptKey is null");
        }

        if (encryptKey.length() != keyLength) {
            throw new RuntimeException("[AESAlgorithm]" + method + "rsakeySize is " + encryptKey.length() + " [need] " + keyLength);
        }
    }

    public Cipher buildCipher(String encryptKey, String ivString, int securityMode) throws Exception {
        Cipher cipher;
        //生成加密key
        //TODO key改成单例后有没有问题
        Key key = getKey(encryptKey);
        //实例化
        cipher = Cipher.getInstance(cipherAlgorithm);

        if (cipherAlgorithm.equals(AesCipherAlgorithmEnum.ECB_PKCS5PADDING.getCipherAlgorithm())) {
            cipher.init(securityMode, key);
        } else if (cipherAlgorithm.equals(AesCipherAlgorithmEnum.CBC_PKCS5PADDING.getCipherAlgorithm())) {
            if (EncryptStringUtils.isBlank(ivString)) {
                ivString = encryptKey;
            } else if (ivString.length() != keyLength) {
                if (ivString.length() < keyLength) {
                    ivString = zeroPadding(ivString);
                } else {
                    ivString = encryptKey.substring(0, keyLength);
                }
            }
            //TODO ivString 的长度是否只支持16个字节，还是说字节数和一样
            if (ivString.length() != keyLength) {
                throw new RuntimeException("[AESAlgorithm] [decrypt] ivString is " + ivString.length() + " [need] " + keyLength);
            }
            IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
            //使用密钥初始化，设置为加密模式
            cipher.init(securityMode, key, iv);
        } else {
            throw new RuntimeException("[AESAlgorithm] cipherAlgorithm=" + cipherAlgorithm + ", not support now!");
        }
        return cipher;
    }

    /**
     * 生成密钥
     *
     * @param key 密钥
     * @return 加密密钥
     * @throws UnsupportedEncodingException
     */
    private Key getKey(String key) throws UnsupportedEncodingException {
        SecretKeySpec secretKeySpec = keyMap.get(key);
        if (secretKeySpec == null) {
            secretKeySpec = new SecretKeySpec(key.getBytes(encodingCharset), "AES");
            keyMap.put(key, secretKeySpec);
        }
        return secretKeySpec;
    }

    public static Builder create() {
        return new Builder();
    }

    public static final class Builder {
        private String cipherAlgorithm = AESAlgorithm.DEFAULT_CIPHER_ALGORITHM;
        private String encoding = AESAlgorithm.DEFAULT_ENCODING;
        private AesKeySizeEnum keySize = AesKeySizeEnum.size128;
        private CodecEnum codec = CodecEnum.HEX;

        public Builder cipherAlgorithm(AesCipherAlgorithmEnum algorithmEnum) {
            this.cipherAlgorithm = algorithmEnum.getCipherAlgorithm();
            return this;
        }

        public Builder encoding(String encoding) {
            this.encoding = encoding;
            return this;
        }

        public Builder codec(CodecEnum codec) {
            this.codec = codec;
            return this;
        }

        public Builder keySize(AesKeySizeEnum keySize) {
            this.keySize = keySize;
            return this;
        }

        public AESAlgorithm build() {
            if (!encodingSet.contains(encoding)) {
                throw new RuntimeException("[AESAlgorithm.Builder] [build] encodingCharset not support " + encoding);
            }
            return new AESAlgorithm(cipherAlgorithm, encoding, keySize, codec);
        }
    }

}
