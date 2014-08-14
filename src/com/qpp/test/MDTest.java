package com.qpp.test;

import com.qpp.model.TOrder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashSet;
import java.util.StringTokenizer;

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
        System.out.println("encode\t" + URLEncoder.encode("a=aaa&b=bbb", "UTF-8"));

        StringTokenizer st = new StringTokenizer("a=aaa&b=bbb", "&");int i=0;
        while(st.hasMoreTokens()){
            System.out.println(++i);
            System.out.println(st.nextToken());
        }
        System.out.println("88888\t"+st.countTokens());
        System.out.println("Type \t"+new HashSet<TOrder>().getClass().getName());
        System.out.println("clild Type \t"+new HashSet<TOrder>().getClass().getTypeParameters()[0].getName());
        System.out.println(" TypeVar \t"+new HashSet<TOrder>().getClass().getTypeParameters()[0].getGenericDeclaration());

        System.out.println(" ArgmType \t" + (new HashSet<TOrder>().getClass().getTypeParameters()[0].getBounds()[0]));
        System.out.println("Date\t"+new Date());
    }
}
