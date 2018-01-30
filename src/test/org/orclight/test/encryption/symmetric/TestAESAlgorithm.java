package org.orclight.test.encryption.symmetric;

import org.orclight.encryption.enums.AesCipherAlgorithmEnum;
import org.orclight.encryption.enums.AesKeySizeEnum;
import org.orclight.encryption.enums.CodecEnum;
import org.orclight.encryption.symmetric.AESAlgorithm;

/**
 * Nothing seek, Nothing find.
 * author: shuzhilong
 * Date: 2018/1/2 下午8:35
 * desc: (The role of this class is to ...)
 * To change this template use preferences | editor | File and code Templates
 */
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
