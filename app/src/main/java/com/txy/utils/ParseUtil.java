package com.txy.utils;


public class ParseUtil {

    public static byte getByteFromEigthBitsString(String str) throws Exception {

        if (str.length() != 8)
            throw new Exception("It's not a 8 length string");
        byte b;
        // check if it's a minus number
        if (str.substring(0, 1).equals("1")) {

            // get lower 7 digits original code
            str = "0" + str.substring(1);
            b = Byte.valueOf(str, 2);
            // then recover the 8th digit as 1 equal to plus 1000000
            b |= 128;
        } else {
            b = Byte.valueOf(str, 2);
        }

        return b;

    }

    // int转8位节节的二进制
    public static String getEigthBitsStringFromByte(int b) {

        b |= 256; // mark the 9th digit as 1 to make sure the string has at
        // least 8 digits

        String str = Integer.toBinaryString(b);

        int len = str.length();

        return str.substring(len - 8, len);

    }
}
