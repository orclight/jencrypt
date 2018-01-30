package org.orclight.encryption.codec;

import java.nio.charset.Charset;
import java.util.Base64;


/**
 * Nothing seek, Nothing find.
 * author: shuzhilong
 * Date: 2018/1/3 下午8:20
 * desc: (The role of this class is to ...)
 * To change this template use preferences | editor | File and code Templates
 */
public class Base64Algorithm {

    /******************************encode**************************************/
    public static String encode(String src) {
        return encode(src,"UTF-8");
    }

    public static String encode(String src,String charsetStr) {
        Charset charset = Charset.forName(charsetStr);
        return new String(Base64.getEncoder().encode(src.getBytes(charset)),charset);
    }

    public static String encode(byte[] data) {
        return encode(data,"UTF-8");
    }

    public static String encode(byte[] data,String charsetStr) {
        Charset charset = Charset.forName(charsetStr);
        return new String(Base64.getEncoder().encode(data),charset);
    }
    /******************************decode**************************************/

    public static byte[] decode2Byte(String src) throws Exception {
        return decode2Byte(src,"UTF-8");
    }

    public static byte[] decode2Byte(String src,String charsetStr) throws Exception {
        return Base64.getDecoder().decode(src.getBytes(charsetStr));
    }

    public static String decode2String(String src) {
        return decode2String(src,"UTF-8");
    }

    public static String decode2String(String src,String charsetStr) {
        Charset charset = Charset.forName(charsetStr);
        return new String(Base64.getDecoder().decode(src),charset);
    }

    public static String decode2String(byte[] data) {
        return decode2String(data,"UTF-8");
    }

    public static String decode2String(byte[] data,String charsetStr) {
        Charset charset = Charset.forName(charsetStr);
        return new String(Base64.getDecoder().decode(data),charset);
    }
}
