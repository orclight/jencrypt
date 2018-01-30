package org.orclight.encryption.sign;

import org.orclight.encryption.codec.Hex;

import java.security.MessageDigest;

/**
 * Nothing seek, Nothing find.
 * author: shuzhilong
 * Date: 2018/1/3 下午8:44
 * desc: (The role of this class is to ...)
 * To change this template use preferences | editor | File and code Templates
 */
public class Md5Algorithm {

    /**  对字符串进行MD5加密     */
    public static String encode(String originString){
        if (originString != null){
            try{
                //创建具有指定算法名称的信息摘要
                MessageDigest md = MessageDigest.getInstance("MD5");
                //使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
                byte[] results = md.digest(originString.getBytes());
                //将得到的字节数组变成字符串返回
                return Hex.encode2Str(results);
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }

    public static String encode(String originString,int start,int end){
        String md5 = encode(originString);
        return md5.substring(start,end);
    }

    public static String encode16(String originString){
        return encode(originString,8,24);
    }

    public static void main(String[] args) {
        String ret = Md5Algorithm.encode("abc");
        System.out.println(ret.length());
    }
}
