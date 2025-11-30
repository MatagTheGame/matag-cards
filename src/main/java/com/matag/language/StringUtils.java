package com.matag.language;

public class StringUtils {
    public static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)" + regex + "(?!.*?" + regex + ")", replacement);
    }
}
