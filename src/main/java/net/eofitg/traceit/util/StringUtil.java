package net.eofitg.traceit.util;

import java.util.regex.Pattern;

public class StringUtil {

    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + '§' + "[0-9A-FK-OR]");

    public static String stripColor(String input) {
        if (input == null) {
            return null;
        }
        return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }

}
