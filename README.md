# jencrypt
优雅的java加解密工具类-支持RSA，AES，MD5，Base64，Hex，ROT

一.RSA TestCase


public class TestRSAAlgorithm {

    public static void main(String[] args) {
        testFull();
    }

    public static void testFull() {
        //TODO Hex test
        RSAAlgorithm rsaAlgorithm = RSAAlgorithm.create().codec(CodecEnum.Base64).build();
        try {
            Map<String, Object> map = rsaAlgorithm.init();
            System.out.println(map.size());
            String privateKey = rsaAlgorithm.getPrivateKeyStr(map);
            System.out.println("privateKey:"+privateKey);
            System.out.println("privateKey_length:"+privateKey.length());

            String publicKey = rsaAlgorithm.getPublicKeyStr(map);
            System.out.println("publicKey:"+publicKey);
            System.out.println("publicKey_length:"+publicKey.length());

            String str = "orclight2018";

            System.out.println("encrypt by public key & decrypt by private key");
            String encryptByPublic_ret = rsaAlgorithm.encryptByPublic(str,publicKey);
            System.out.println("result:"+encryptByPublic_ret);
            System.out.println("result:"+encryptByPublic_ret.length());

            String decryptByPrivate_ret = rsaAlgorithm.decryptByPrivate(encryptByPublic_ret,privateKey);
            System.out.println("result:"+decryptByPrivate_ret);
            System.out.println("result:"+decryptByPrivate_ret.length());

            System.out.println("-----------------------------------------");

            System.out.println("encrypt by private key & decrypt by public key");
            String encryptByPrivateKey_ret = rsaAlgorithm.encryptByPrivate(str,privateKey);
            System.out.println("result:"+encryptByPrivateKey_ret);
            System.out.println("result:"+encryptByPrivateKey_ret.length());

            String decryptByPrivate = rsaAlgorithm.decryptByPublic(encryptByPrivateKey_ret,publicKey);
            System.out.println("result:"+decryptByPrivate);
            System.out.println("result:"+decryptByPrivate.length());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}

二.AES Testcase



public class TestAESAlgorithm {

    public static void main(String[] args) throws Exception {

        AESAlgorithm aesAlgorithm = AESAlgorithm.create().keySize(AesKeySizeEnum.size128)
                .cipherAlgorithm(AesCipherAlgorithmEnum.CBC_PKCS5PADDING)
                .codec(CodecEnum.HEX).build();

        String key = "1234567890qweasd";
        String siv = "qweasd1234567890";
//        String key = "1234567890qweasd12345678";
        String data = "next action is tomorrow Am 8";

        System.out.println("加密前的字串是：" + data);
        String signature = aesAlgorithm.encrypt(data, key, siv);
        long start = System.currentTimeMillis();

        long end = System.currentTimeMillis();
        System.out.println("加密后的字串是：" + signature);
        System.out.println("加密 cost:" + (end - start));
        System.out.println("4e7f89c7f2a791c59010fb31ee977b77acaba5ad503201c4ff320857a1b04904".equals(signature));

        start = System.currentTimeMillis();
        String DeString = aesAlgorithm.decrypt(signature, key, siv);

        System.out.println("解密后的字串是：" + DeString);
        end = System.currentTimeMillis();
        System.out.println("解密 cost:" + (end - start));
    }

}

三.base64 Testcase

public class Base64AlgorithmTest {
    public static void main(String[] args) throws Exception {
        String content = "next action is tomorrow AM:8";
        String encode = Base64Algorithm.encode(content);
        String decode = Base64Algorithm.decode2String(encode);
        System.out.println("原文："+content);
        System.out.println("加密后："+encode);
        System.out.println("解密后：" + decode);
    }
}


