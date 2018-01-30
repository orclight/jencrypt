package org.orclight.test.encryption.codec;


import org.orclight.encryption.codec.Base64Algorithm;

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
