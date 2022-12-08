package com.example.demo.utils;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DtsBase64Util extends Base64 {

    public static final String BASE64_FILE_HEADER_DEFAULT = "data:@file/octet-stream;base64,";

    public static String removeBase64Header(String base64) {
        if (base64 == null)
            return null;
        return base64.substring(base64.indexOf(",") + 1);
    }

    public static String encodeByte(byte[] bytes) {
        return new String(encodeBase64(bytes), StandardCharsets.UTF_8);
    }

    public static String encodeFile(File file) {
        String result = DtsStringUtil.EMPTY;
        try {
            byte[] bytes = FileUtils.readFileToByteArray(file);
            result = encodeByte(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static byte[] decodeToByte(String base64String) {
        byte[] result = null;
        if (DtsStringUtil.isNotBlank(base64String)) {
            result = Base64.decodeBase64(removeBase64Header(base64String));
        }
        return result;
    }

    public static long calcSizeInKBytes(String base64String) {
        try {
            Double result = -1.0;
            if (DtsStringUtil.isNotBlank(base64String)) {
                Integer padding = 0;
                if (base64String.endsWith("==")) {
                    padding = 2;
                } else {
                    if (base64String.endsWith("="))
                        padding = 1;
                }
                result = (Math.ceil(base64String.length() / 4) * 3) - padding;
            }
            return Math.round(result / 1024);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return 0;
        }
    }

    public static void saveToFile(File file, String base64String) {
        try {
            byte[] filebyte = decodeToByte(base64String);
            FileUtils.writeByteArrayToFile(file, filebyte);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
