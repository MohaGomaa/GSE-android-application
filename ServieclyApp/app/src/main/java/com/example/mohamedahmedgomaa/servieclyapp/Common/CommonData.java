package com.example.mohamedahmedgomaa.servieclyapp.Common;

import android.widget.TextView;

import java.util.Locale;

public class CommonData {
    private static Locale locale;
     public static TextView textView;

    public static Locale getLocale() {
        return locale;
    }

    public static void setLocale(Locale locale) {
        CommonData.locale = locale;
    }

    public static void setDefaultLocale() {

        Locale.setDefault(CommonData.locale);

    }
}
