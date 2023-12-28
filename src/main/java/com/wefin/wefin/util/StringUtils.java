package com.wefin.wefin.util;

public class StringUtils {

    public static String removeAllExceptNumber(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }

        return str.replaceAll("[^0-9]", "");
    }
}
