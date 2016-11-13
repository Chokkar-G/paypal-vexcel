package tech.paypal.app.ngo.vexcel.util;

/**
 * Created by chokkar
 */
public class StringUtil {

    public static boolean isNullOrEmpty(String input) {
        return input == null || input.trim().length() == 0;
    }
}
