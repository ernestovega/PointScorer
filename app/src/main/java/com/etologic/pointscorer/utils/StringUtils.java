package com.etologic.pointscorer.utils;

public class StringUtils {

    public static boolean isEmpty(String text) {
        if(text == null) return true;
        return text.trim().isEmpty();
    }
}
