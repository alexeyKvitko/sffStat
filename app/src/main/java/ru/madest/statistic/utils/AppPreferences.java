package ru.madest.statistic.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.UUID;

import ru.madest.statistic.SFFSApplication;

public abstract class AppPreferences {

    private static final String TAG = "AppPreferences";

    private static final String PREF_UNIQUE_ID = "ru.sff.statistic.utils.PREF_UNIQUE_ID";

    private static final SharedPreferences SHARED_PREFERENCES
            = PreferenceManager.getDefaultSharedPreferences( SFFSApplication.getAppContext() );

    /**
     *  Set Application Preferences
     *  @param key - key
     *  @param value - value
     */
    public static void setPreference( String key,Object value ){
        SharedPreferences.Editor editor = SHARED_PREFERENCES.edit();
        if ( value instanceof String ){
            editor.putString( key, (String) value );
        } else if ( value instanceof Float ){
            editor.putFloat(key, (Float) value);
        } else if ( value instanceof Integer ){
            editor.putInt(key, (Integer) value);
        } else {
            Gson gson =  new Gson();
            String jsonString = gson.toJson( value );
            editor.putString( key, jsonString );
        }
        editor.apply();
    }

    /**
     *  Get String Application Preferences
     *  @param key - key
     *  @param defValue - value
     */
    public static String getPreference( String key, String defValue ){
        return SHARED_PREFERENCES.getString( key, defValue );
    }

    /**
     *  Get float Application Preferences
     *  @param key - key
     *  @param defValue - value
     */
    public static float getPreference(  String key, double defValue ){
        return SHARED_PREFERENCES.getFloat(key, (float) defValue);
    }

    public static Object getObjectPreference(  String key, Type type ){
        Object object = null;
        try{
            Gson gson =  new Gson();
            String jsonClient = getPreference( key, null );
            if(  jsonClient != null ){
                object = gson.fromJson( jsonClient, type );
            }

        } catch ( Exception e ){
            Log.e( TAG, "Can not get data, from preferences:"+e.getMessage() );
            e.printStackTrace();
        }
        return object;
    }

    /**
     *  Get int Application Preferences
     *  @param key - key
     *  @param defValue - value
     */
    public static int getPreference( String key, int defValue ){
        return SHARED_PREFERENCES.getInt(key, defValue);
    }

    public static void removePreference(String key){
        SharedPreferences.Editor editor = SHARED_PREFERENCES.edit();
        editor.remove( key );
        editor.commit();
    }

    public static String getUniqueId() {
        String uniqueID = getPreference( PREF_UNIQUE_ID, null );
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                setPreference( PREF_UNIQUE_ID, uniqueID );
        }
        return uniqueID;
    }

    public static Integer getIntFromUniqueId(){
        Integer value = -1;
        String uniqueID = getPreference( PREF_UNIQUE_ID, null );
        String digits = uniqueID.substring( uniqueID.length()-12 ).replaceAll("[^0-9.]", "");
        try {
            value = Integer.valueOf( digits );
        } catch ( Exception e ){
            return -1;
        }
        return value;
    }

}
