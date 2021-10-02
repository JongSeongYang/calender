package com.himsoomzzin.calender.util;

import java.security.MessageDigest;

public class EncodingUtils {

    public static String toHash(String s) {
        return encrypt(s, "SHA-256");
    }

    public static String encrypt(String s, String messageDigest) {
        try {
            MessageDigest md = MessageDigest.getInstance(messageDigest);
            byte[] passBytes = s.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digested.length; i++) sb.append(Integer.toString((digested[i]&0xff) + 0x100, 16).substring(1));
            return sb.toString();
        } catch (Exception e) {
            return s;
        }
    }

    public static String toPasswordHash(String password) {
        return toHash(password);
    }
}
