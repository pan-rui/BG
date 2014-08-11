package com.qpp.test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by qpp on 8/11/2014.
 */
public class MDTest {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update("Hello World,My name is Pan Ray".getBytes("UTF-8"));
        md.update("Security...".getBytes("UTF-8"));   //更新了  "Security".getBytes().length 长度的字节....
        byte[] res1 = md.digest();
        System.out.println(res1.length);
        StringBuffer sb = new StringBuffer();
        for (byte b : res1) {
            int b16=b&0xff;
            if(b16<16)
                sb.append(0);
//            sb.append(Integer.toHexString(b16));
            sb.append(Integer.toString(b16, 16));  //等同于上面....
        }
        System.out.println(sb.toString());


    }
}
