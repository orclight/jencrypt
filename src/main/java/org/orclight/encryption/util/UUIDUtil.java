package org.orclight.encryption.util;

import java.util.UUID;

/**
 * Nothing seek, Nothing find.
 * author: shuzhilong
 * Date: 2018/1/8 下午5:45
 * desc: (The role of this class is to ...)
 * To change this template use preferences | editor | File and code Templates
 */
public class UUIDUtil {

    public static String getUUIDOrigin() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String getUUIDWithOutBlank() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        // 去掉"-"符号
        return  new StringBuffer()
                .append(str.substring(0, 8))
                .append(str.substring(9, 13))
                .append(str.substring(14, 18))
                .append(str.substring(19, 23))
                .append(str.substring(24))
                .toString();
    }

    public static void main(String[] args) {
        for(int i=0;i<=10;i++) {
            System.out.println(getUUIDOrigin());
        }
        System.out.println("------------------------");
        for(int i=0;i<=10;i++) {
            String ret = getUUIDWithOutBlank();
            System.out.println(ret);
            byte[] resultBytes = ret.getBytes();
            System.out.println(resultBytes.length);
            for(byte b:resultBytes) {
                System.out.print(b);
                System.out.print(' ');
                System.out.print(String.valueOf(b));
            }
            System.out.println();

            System.out.println((byte)'0');
            System.out.println((byte)'f');
        }
    }
}
