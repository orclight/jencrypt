package org.orclight.test.encryption.asymmetric;


import org.orclight.encryption.asymmetric.RSAAlgorithm;
import org.orclight.encryption.enums.CodecEnum;
import org.orclight.encryption.enums.RSAKeySizeEnum;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Nothing seek, Nothing find.
 * author: shuzhilong
 * Date: 2018/1/4 上午10:16
 * desc: (The role of this class is to ...)
 * To change this template use preferences | editor | File and code Templates
 */
public class TestRSAAlgorithm {

    public static void main(String[] args) {
//        int version = 1515752729;
        String publick_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDBpr7/7gpS/jukXZfDmcO+iYnUlsN2KgQhgiXX+/aqPJ/3QyLnpc/J2XrUP75xRnZYAd5TUBrF2H8bbB+7HF3gozTCtKEtAI7eqH+zmY2aEMgdzHo8UG4cbUQLhre7KD5agW6cF/oXxLeRl41yxbp1WoEuo0PuJHyJSy7JlskzXwIDAQAB";
        String private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMGmvv/uClL+O6Rdl8OZw76JidSWw3YqBCGCJdf79qo8n/dDIuelz8nZetQ/vnFGdlgB3lNQGsXYfxtsH7scXeCjNMK0oS0Ajt6of7OZjZoQyB3MejxQbhxtRAuGt7soPlqBbpwX+hfEt5GXjXLFunVagS6jQ+4kfIlLLsmWyTNfAgMBAAECgYEAtJ+bN4wIBTEs4DqL+93IPkh7TtbwqZ63C6ohMjvJ7y9s5Wyxee+myFcZwi9SqsVWTPAxNvRYhIFSzXkkf5oN1IHp2wiBJn0EQsMMSDsnAYQ1Qr2yUfEanLQDvBAv3jBWBm5YrcZ+WNEUVTjowC1Pae3q6DlwraKCZ+0p+QMqzUECQQDkRnJV7k7fDk7XeZeP6FvhXyRR/LDsT7BcfiFyQZ0p0kmlBbq3QhbBgpcTQ/fsCUF3yceBZyHNrm0FhFlwclP1AkEA2SvFp9pONSg/YKhAqMpHN7lajyQO6U8+8S1wlHYw45F1/VjqXDZICtL88MedqyhYHTF2uggOF9GEgNMvmb4pgwJBAI+DQoqHogpVwndxODn/CRStbfYleU135BaiLYDZg2cxj58x9OsVn5zU7wnrp0g7oW8f6k+OeRAIYv4e8OPi3KkCQC71axDngH+4CzRATXLyAkJHXxCSkHLcgtStqalAgUPD4Up93nc/+YX1p2H9F9e5qovtdSm2si1uTYN5ptJMrzcCQEYKNKVbJMqSu2L093QBDolr05nCfjuGphxnXQjU4cKSpspsMwEOQ9Qw6RuowlYjA/KCKiH0bo6/3/IUCaDiiq4=";
        String toDecrypt = "1";

        RSAAlgorithm rsaAlgorithm = RSAAlgorithm.create().codec(CodecEnum.Base64).rsakeySize(RSAKeySizeEnum.size1024).build();

        System.out.println(rsaAlgorithm.getPublicKeyE(publick_key));
        System.out.println(rsaAlgorithm.getPublicKeyN(publick_key));
        String ret1 = rsaAlgorithm.encryptByPublic(toDecrypt,publick_key);
        System.out.println(ret1);

//        ret1= "WCLhwJdm0SZeIKxJdNlelkusfGbelJBJYOtyNAKm1XsDdlX/DeH38pQhxwoMu8TODXsESoHrBD+iFBvO3YDXQmI7GPce2BzA4d+QKkn7yBeXKjJcCO2xdsVW7DUBAeF1r4hfZZ2g/R7o3ox9xteyYK6wVIQTiYiDpyHCEJAClVE=";

        ret1= "grcrBdT2+r6Jasw1kk0mTluwjqJcRUC9Pss/5B35I3mFmY8T4JGFWDf18pLRPkyihlAYjfewsQ5lH4DMnxx4u6pltMzOAhfz2ifWGa4NaImRU8GE3MEK6dJnV8JYghySD5ITjSN9wkI4V6MbUYVYKWlbRAuVwBsgfqh8/E7aiFQ=";

        ret1 = "fcCjHqn8FNDRySUEp3L6byxpyo7BIdKdvwNCBHar4uAu2Q3zs0INnYv1jo4A7H46lmWg7qH+G9PFzO90AvsZajv8cWaIU9GZYXmh5jzqCw8KZL2SxTTj5BlAbBQYsnLm8vgdIiJsPBWwmm59zKWJ1mv6118pOELAjTHsWGTM3ng=";

        String ret2 = rsaAlgorithm.decryptByPrivate(ret1,private_key);
        System.out.println(ret2);

        System.out.println("----------------------");
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
