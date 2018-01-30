package org.orclight.encryption.codec;

/**
 * Nothing seek, Nothing find.
 * author: shuzhilong
 * Date: 2018/1/8 下午8:17
 * desc: (The role of this class is to ...)
 * To change this template use preferences | editor | File and code Templates
 */
public class RotConverter {
    private static final int aCHAR = (int) 'a';
    private static final int zCHAR = (int) 'z';
    private static final int ACHAR = (int) 'A';
    private static final int ZCHAR = (int) 'Z';
    private static final int CHAR0 = (int) '0';
    private static final int CHAR9 = (int) '9';

    public static String encode(String message, int offset, boolean numbers){
        char[] chars = new char[message.length()];
        for(int x = 0; x < message.length();x++){
            int temp = (int) message.charAt(x);


            if(numbers){
                if (temp >= CHAR0 && temp <= CHAR9){
                    temp -= CHAR0;
                    temp = (temp + offset)%10;
                    temp += CHAR0;
                }
            }

            //A == 65, Z == 90
            if(temp >= ACHAR && temp <= ZCHAR){
                temp -= ACHAR;
                temp = (temp + offset)%26;
                temp += ACHAR;
            }
            //a == 97, z == 122.
            else if(temp >= aCHAR && temp <= zCHAR){
                temp -= aCHAR;
                temp = (temp + offset)%26;
                temp += aCHAR;
            }
            chars[x] = (char) temp;
        }
        String returnString = new String(chars);
        return returnString;
    }

    public static String decode(String message, int offset, boolean numbers){
        char[] chars = new char[message.length()];
        for(int x = 0; x < message.length(); x++){
            int temp = (int) message.charAt(x);

            //if converting numbers is enabled
            if(numbers){
                if (temp >= CHAR0 && temp <= CHAR9){
                    temp -= CHAR0;
                    temp = (temp - offset);
                    while(temp<0) {
                        temp += 10;
                    }
                    temp += CHAR0;
                }
            }

            //A == 65, Z == 90
            if (temp >= ACHAR && temp <= ZCHAR){
                temp -= ACHAR;

                temp = (temp - offset);
                while(temp < 0) {
                    temp = 26 + temp;
                }
                temp += ACHAR;
            }
            else if (temp >= aCHAR && temp <= zCHAR){
                temp -= aCHAR;

                temp = (temp - offset);
                if(temp < 0)
                    temp = 26 + temp;

                temp += aCHAR;
            }
            chars[x] = (char) temp;
        }

        String returnString = new String(chars);
        return returnString;
    }
}
