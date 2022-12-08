package com.example.demo.utils.common;

import java.text.Normalizer;
import java.util.regex.Pattern;

import com.example.demo.utils.DtsStringUtil;

public class CommonStringUtil extends DtsStringUtil {

    public static boolean isNotBlank(final CharSequence cs) {
        return DtsStringUtil.isNotBlank(cs);
    }

    public static String repeat(final String str, final int repeat) {
        return DtsStringUtil.repeat(str, repeat);
    }

    public static String substring(final String str, int start) {
        return DtsStringUtil.substring(str, start);
    }

    public static String removeAccent(String str) {
        str = str.replaceAll("Đ", "D");
        str = str.replaceAll("đ", "d");
        String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    public static String removeSpecial(String str) {
        return str.replaceAll("[^A-Za-z0-9]", "");
    }

    public static String cleanXSS(String value) {
        if (value != null) {
            value = value.replaceAll("\\s{2,}", " ").trim();
            value = value.replaceAll("%3C", "<");
            value = value.replaceAll("%2F", "/");
            value = value.replaceAll("%3E", ">");
            value = value.replaceAll("%3D", "=");
            // You'll need to remove the spaces from the html entities below
            if (value.matches("<.*?on.*?>.*?")) {
                value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
            }
            /* value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;"); */
            if (value.matches(".*?=.*?")) {
                value = value.replaceAll("'", "&#39;");
            }
            value = value.replaceAll("eval\\((.*)\\)", "");
            value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
            value = value.replaceAll("<.*?script.*?>", "");
        }

        return value;
    }
}
