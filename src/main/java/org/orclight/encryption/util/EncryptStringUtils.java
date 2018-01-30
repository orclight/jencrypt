package org.orclight.encryption.util;

/**
 * Nothing seek, Nothing find.
 * author: shuzhilong
 * Date: 2018/1/2 上午10:36
 * desc: (The role of this class is to ...)
 * To change this template use preferences | editor | File and code Templates
 */
public class EncryptStringUtils {

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

}
