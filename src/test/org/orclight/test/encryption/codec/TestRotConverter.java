package org.orclight.test.encryption.codec;

import org.orclight.encryption.codec.RotConverter;

/**
 * Nothing seek, Nothing find.
 * author: shuzhilong
 * Date: 2018/1/8 下午8:17
 * desc: (The role of this class is to ...)
 * To change this template use preferences | editor | File and code Templates
 */
public class TestRotConverter {
    public static void main(String[] args) {
        String str = "1f2e9df6131b480b9fdddc633cf24996";

        System.out.println(str);
        System.out.println(RotConverter.encode(str,13,false));
        System.out.println(RotConverter.encode(str,13,true));
    }
}
