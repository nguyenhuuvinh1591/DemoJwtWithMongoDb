package com.example.demo.utils.common;

import java.nio.charset.StandardCharsets;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.example.demo.utils.DtsBase64Util;

public class CommonBase64Util extends DtsBase64Util {

    public static String encodeString(String message) {
        return new String(encodeBase64(CommonStringUtil.getBytes(message, StandardCharsets.UTF_8)),
                StandardCharsets.UTF_8);
    }

    public static String decodeString(String message) {
        return new String(decodeBase64(message), StandardCharsets.UTF_8);
    }

    public static String getImageType(String base64) {
        String[] header = base64.split("[;]");
        if (header == null || header.length == 0) {
            return null;
        }
        return header[0].split("[/]")[1];
    }

    public static String encodeWithSecretKey(String message, String secretKey) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            hash = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
    }

}
