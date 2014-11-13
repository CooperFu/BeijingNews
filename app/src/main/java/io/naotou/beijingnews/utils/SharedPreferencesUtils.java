package io.naotou.beijingnews.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Jack_Cooper on 2014/11/12.
 * Have a nice day!
 */
public class SharedPreferencesUtils {
    public static String CONFIG = "config";
    public static SharedPreferences sharedPreferences;

    /**
     * @param context
     * @param key
     * @param value   存储
     */
    public static void setStringData(Context context, String key, String value) {

        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        sharedPreferences.edit().putString(key, value).commit();
    }

    /**
     * @param context
     * @param key
     * @param value
     * @return获取
     */
    public static String getStringData(Context context, String key, String value) {

        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getString(key, value);
    }
}
