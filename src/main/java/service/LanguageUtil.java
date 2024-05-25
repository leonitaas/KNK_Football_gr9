package service;

public class LanguageUtil {

    private static String language = "English";

    public static void setLanguage(String newLanguage) {
        language = newLanguage;
    }

    public static String getLanguage() {
        return language;
    }
}
