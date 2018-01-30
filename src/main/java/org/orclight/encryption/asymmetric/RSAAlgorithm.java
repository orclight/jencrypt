package org.orclight.encryption.asymmetric;

import org.orclight.encryption.codec.Base64Algorithm;
import org.orclight.encryption.codec.Hex;
import org.orclight.encryption.enums.CodecEnum;
import org.orclight.encryption.enums.RSAKeySizeEnum;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Nothing seek, Nothing find.
 * author: shuzhilong
 * Date: 2018/1/2 下午8:55
 * desc: (The role of this class is to ...)
 * To change this template use preferences | editor | File and code Templates
 */
public class RSAAlgorithm {

    public static final String KEY_RSA = "RSA";

    //定义签名算法
    private final static String KEY_RSA_SIGNATURE = "MD5withRSA";

    //定义公钥关键词
    public static final String KEY_RSA_PUBLICKEY = "RSAPublicKey";
    //定义私钥关键词
    public static final String KEY_RSA_PRIVATEKEY = "RSAPrivateKey";

    public static final String KEY_RSA_PUB_N = "RSA_PUB_N";

    public static final String KEY_RSA_E = "RSA_E";

    public static final String KEY_RSA_PRI_D = "RSA_D";

    private static final String DEFAULT_ENCODING = "UTF-8";


    static KeyFactory factory;

    static {
        try {
            factory = KeyFactory.getInstance(KEY_RSA);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private RSAKeySizeEnum rsaKeySize;
    private CodecEnum codec;
    private String encodingCharset;

    private RSAAlgorithm(RSAKeySizeEnum rsaKeySize, CodecEnum codec, String encodingCharset) {
        this.rsaKeySize = rsaKeySize;
        this.codec = codec;
        this.encodingCharset = encodingCharset;
    }

    /**
     * 生成公私密钥对
     */
    public Map<String, Object> init() throws NoSuchAlgorithmException {
        Map<String, Object> map = null;
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

            KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_RSA);
            //设置密钥对的bit数，越大越安全，但速度减慢，一般使用512或1024
            generator.initialize(rsaKeySize.value(),random);
            KeyPair keyPair = generator.generateKeyPair();
            // 获取公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

            // 获取私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();


            BigInteger publicExponent = publicKey.getPublicExponent();
            BigInteger exponent = publicKey.getModulus();

            BigInteger privateExponent = privateKey.getPrivateExponent();
            BigInteger exponent1 = privateKey.getModulus();

            System.out.println(publicExponent==privateExponent);
            System.out.println(exponent==exponent1);

            System.out.println(exponent);

            // 将密钥对封装为Map
            map = new HashMap<>();
            map.put(KEY_RSA_PUBLICKEY, publicKey);
            map.put(KEY_RSA_PRIVATEKEY, privateKey);
        } catch (NoSuchAlgorithmException e) {
            throw e;
        }

        return map;
    }

    public String getPublicKeyStr(Map<String, Object> map) {
        Key key = (Key) map.get(KEY_RSA_PUBLICKEY);
        if(CodecEnum.Base64 == codec) {
            return Base64Algorithm.encode(key.getEncoded());
        } else if(CodecEnum.HEX == codec) {
            return Hex.encode2Str(key.getEncoded());
        }
        //默认用base64
        return Base64Algorithm.encode(key.getEncoded());
    }

    public String getPrivateKeyStr(Map<String, Object> map) {
        Key key = (Key) map.get(KEY_RSA_PRIVATEKEY);
        if(CodecEnum.Base64 == codec) {
            return Base64Algorithm.encode(key.getEncoded());
        } else if(CodecEnum.HEX == codec) {
            return Hex.encode2Str(key.getEncoded());
        }
        //默认用base64
        return Base64Algorithm.encode(key.getEncoded());
    }

    public RSAPublicKey getPublicKey(Map<String, Object> map) {
        return  (RSAPublicKey) map.get(KEY_RSA_PUBLICKEY);
    }

    public RSAPrivateKey getPrivateKey(Map<String, Object> map) {
        return  (RSAPrivateKey) map.get(KEY_RSA_PRIVATEKEY);
    }

    public RSAPublicKey getPublicKey(String publicKeyStr) {
        try {
            byte[] publicKeyBytes =  decode2Byte(publicKeyStr);
            // 获得公钥
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey = factory.generatePublic(keySpec);
            return (RSAPublicKey)publicKey;
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RSAPrivateKey getPrivateKey(String privateKeyStr) {
        try {
            // 对私钥解密
            byte[] privateKeyBytes = decode2Byte(privateKeyStr);
            // 获得私钥
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey = factory.generatePrivate(keySpec);
            return (RSAPrivateKey)privateKey;
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BigInteger getPublicKeyE(String publicKeyStr) {
        RSAPublicKey rsaPublicKey = getPublicKey(publicKeyStr);
        if(rsaPublicKey!=null) {
            return rsaPublicKey.getModulus();
        }
        return null;
    }

    public BigInteger getPublicKeyN(String publicKeyStr) {
        RSAPublicKey rsaPublicKey = getPublicKey(publicKeyStr);
        if(rsaPublicKey!=null) {
            return rsaPublicKey.getPublicExponent();
        }
        return null;
    }

    /**
     * 公钥加密
     * @param src
     * @param publicKeyStr
     * @return
     */
    public String encryptByPublic(String src, String publicKeyStr){
        try {
            PublicKey publicKey = getPublicKey(publicKeyStr);

            // 取得待加密数据
            byte[] data = src.getBytes(encodingCharset);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] result = cipher.doFinal(data);
            return encode2String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 私钥解密
     * @param toDecrypt
     * @param privateKeyStr
     * @return
     */
    public String decryptByPrivate(String toDecrypt, String privateKeyStr){
        try {

            PrivateKey privateKey = getPrivateKey(privateKeyStr);

            // 对数据解密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // 返回UTF-8编码的解密信息
            // 获得待解密数据
            byte[] data = decode2Byte(toDecrypt);
            byte[] result = cipher.doFinal(data);
            return new String(result,encodingCharset);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 私钥加密
     * @param src
     * @param privateKeyStr
     * @return
     */
    public String encryptByPrivate(String src, String privateKeyStr){
        try {
            PrivateKey privateKey = getPrivateKey(privateKeyStr);

            // 对数据加密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            // 返回编码转换后的信息
            // 取得待加密数据
            byte[] data = decode2Byte(src);
            return encode2String(cipher.doFinal(data));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 公钥解密
     * @param toDecrypt
     * @param publicKeyStr
     * @return
     */
    public String decryptByPublic(String toDecrypt, String publicKeyStr){
        try {
            PublicKey publicKey = getPublicKey(publicKeyStr);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            // 返回UTF-8编码的解密信息
            // 取得待加密数据
            byte[] data = decode2Byte(toDecrypt);
            return encode2String(cipher.doFinal(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] decode2Byte(String data) {
        byte[] publicKeyBytes = null;

        // 将公钥由字符串转为UTF-8格式的字节数组
        try {
            if(codec==CodecEnum.Base64) {
                publicKeyBytes = Base64Algorithm.decode2Byte(data);
            } else if(codec==CodecEnum.HEX) {
                publicKeyBytes = Hex.decode(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicKeyBytes;
    }

    private String encode2String(byte[] data) {
        if(codec==CodecEnum.Base64) {
            // 返回加密后由Base64编码的加密信息
            return Base64Algorithm.encode(data);
        } else if(codec==CodecEnum.HEX) {
            // 返回加密后由Hex编码的加密信息
            return Hex.encode2Str(data);
        }
        return null;
    }

//    /**
//     * 用私钥对加密数据进行签名
//     * @param encryptedStr
//     * @param privateKeyStr
//     * @return
//     */
//    public String sign(String encryptedStr, String privateKeyStr) {
//        String str = "";
//        try {
//            //将私钥加密数据字符串转换为字节数组
//            byte[] data = encryptedStr.getBytes();
//            // 解密由base64编码的私钥
//            byte[] bytes = Base64Algorithm.decode(privateKeyStr).getBytes();
//            // 构造PKCS8EncodedKeySpec对象
//            PKCS8EncodedKeySpec pkcs = new PKCS8EncodedKeySpec(bytes);
//            // 指定的加密算法
//            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
//            // 取私钥对象
//            PrivateKey key = factory.generatePrivate(pkcs);
//            // 用私钥对信息生成数字签名
//            Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);
//            signature.initSign(key);
//            signature.update(data);
//            str = Base64Algorithm.encode(signature.sign());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return str;
//    }
//
//    /**
//     * 校验数字签名
//     * @param encryptedStr
//     * @param publicKeyStr
//     * @param sign
//     * @return 校验成功返回true，失败返回false
//     */
//    public boolean verify(String encryptedStr, String publicKeyStr, String sign) {
//        boolean flag = false;
//        try {
//            //将私钥加密数据字符串转换为字节数组
//            byte[] data = encryptedStr.getBytes();
//            // 解密由base64编码的公钥
//            byte[] bytes = Base64Algorithm.decode(publicKeyStr).getBytes();
//            // 构造X509EncodedKeySpec对象
//            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
//            // 指定的加密算法
//            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
//            // 取公钥对象
//            PublicKey key = factory.generatePublic(keySpec);
//            // 用公钥验证数字签名
//            Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);
//            signature.initVerify(key);
//            signature.update(data);
//            flag = signature.verify(Base64Algorithm.decode(sign).getBytes());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return flag;
//    }
//

    public static Builder create() {
        return new Builder();
    }

    public static final class Builder {

        private RSAKeySizeEnum rsaKeySizeEnum = RSAKeySizeEnum.size1024;
        private CodecEnum codec = CodecEnum.Base64;
        private String encodingCharset = RSAAlgorithm.DEFAULT_ENCODING;

        public Builder rsakeySize(RSAKeySizeEnum rsaKeySizeEnum) {
            this.rsaKeySizeEnum = rsaKeySizeEnum;
            return this;
        }

        public Builder codec(CodecEnum codec) {
            this.codec = codec;
            return this;
        }

        public RSAAlgorithm build() {
            if(rsaKeySizeEnum==null) {
                throw new RuntimeException("[RSAAlgorithm.Builder] [build] rsaKeySizeEnum is null ");
            }
            if(codec==null) {
                throw new RuntimeException("[RSAAlgorithm.Builder] [build] codec is null ");
            }
            return new RSAAlgorithm(rsaKeySizeEnum,codec,encodingCharset);
        }

    }
}