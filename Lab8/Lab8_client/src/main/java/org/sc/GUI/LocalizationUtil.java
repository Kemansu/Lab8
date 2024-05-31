package org.sc.GUI;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationUtil {
    private static Locale currentLocale = Locale.getDefault();
    private static ResourceBundle bundle = ResourceBundle.getBundle("messages_ru", currentLocale);

    public static void setLocale(Locale locale) {
        currentLocale = locale;
        bundle = ResourceBundle.getBundle("messages", currentLocale);
    }

    public static String getString(String key) {
        return bundle.getString(key);
    }

    public static Locale getCurrentLocale() {
        return currentLocale;
    }
}
