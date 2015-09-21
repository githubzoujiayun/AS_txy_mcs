package com.txy.utils;


import com.txy.udp.InitData.ByteMerge;

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

    public static int getStringToInt(String str ) {
        byte[] bytes = str.getBytes();
        byte j = 0;
        if (bytes[0] >= 48 && bytes[0] <= 57) {
            j = (byte) ( bytes[0] - '0');
        } else if (bytes[0] >= 97 && bytes[0] <= 122) {
            j = (byte) (bytes[0] - 'a' + 10);
        }
        return j;
    }

}
