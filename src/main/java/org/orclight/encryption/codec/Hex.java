package org.orclight.encryption.codec;

public class Hex {  
	  
    /** 
     * 用于建立十六进制字符的输出的小写字符数组 
     */  
    private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5',  
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };  
  
    /** 
     * 用于建立十六进制字符的输出的大写字符数组 
     */  
    private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5',  
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    /**
     * 将字节数组转换为十六进制字符
     *
     * @param data
     *            byte[]
     * @return 十六进制String
     */
    public static String encode2Str(byte[] data) {
        return encode2Str(data, true);
    }

    /**
     * 将字节数组转换为十六进制字符�?
     *
     * @param data
     *            byte[]
     * @param toLowerCase
     *            <code>true</code> 传换成小写 <code>false</code> 传换成大写
     * @return 十六进制String
     */
    public static String encode2Str(byte[] data, boolean toLowerCase) {
        return new String(encode(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER));
    }

    /** 
     * 将字节数组转换为十六进制字符数组 
     *  
     * @param data 
     *            byte[] 
     * @param toDigits 
     *            用于控制输出的char[] 
     * @return 十六进制char[] 
     */  
    private static char[] encode(byte[] data, char[] toDigits) {
        int length = data.length;
        char[] out = new char[length << 1];
        // two characters form the hex value.  
        for (int i = 0, j = 0; i < length; i++) {
            out[j++] = toDigits[(data[i]>>4) & 0x0F];
            out[j++] = toDigits[data[i] & 0x0F];
        }  
        return out;  
    }


    /**
     * 将十六进制字符数组转换为字节数组
     *
     * @param src
     *            十六进制char[]
     * @return byte[]
     * @throws RuntimeException
     *             如果源十六进制字符数组是个奇怪的长度，将抛出运行时异�?
     */
    public static byte[] decode(String src) {

        char[] data = src.toCharArray();
        int len = data.length;

        if ((len & 0x01) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }

        byte[] out = new byte[len >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f = f | toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }

        return out;
    }
  
    /** 
     * 将十六进制字符转换成byte
     *  
     * @param ch 
     *            十六进制char 
     * @param index 
     *            十六进制字符在字符数组中的位置
     * @return
     * @throws RuntimeException 
     *             当ch不是合法的十六进制字符时，抛出运行时异常
     */  
    private static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);  
        if (digit == -1) {  
            throw new RuntimeException("Illegal hexadecimal character " + ch  
                    + " at index " + index);  
        }  
        return digit;  
    }  
  
    public static void main(String[] args) {  
        String srcStr = "asdasd中文测试123123数字";
        String encodeStr = encode2Str(srcStr.getBytes());
        String decodeStr = new String(decode(encodeStr));
        System.out.println("转换前：" + srcStr);  
        System.out.println("转换后：" + encodeStr);  
        System.out.println("还原后：" + decodeStr);  
    }  
  
}  