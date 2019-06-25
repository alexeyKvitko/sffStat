package ru.sff.statistic.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ru.sff.statistic.SFFSApplication;

public abstract class AppUtils {

    private static final String TAG = "AppUtils";

    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = ( ConnectivityManager )
                SFFSApplication.getAppContext().getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


    public static int getRandomBetweenRange( int min, int max ) {
        Double x = ( Math.random() * ( ( max - min ) + 1 ) ) + min;
        return x.intValue();
    }


    public static boolean nullOrEmpty( String value ) {
        return value == null || ( value != null && value.trim().length() == 0 );
    }

    public static boolean nullOrEmpty( List value ) {
        return value == null || ( value != null && value.size() == 0 );
    }

    public static String formatDate( String format, Date date ) {
        SimpleDateFormat sdf = new SimpleDateFormat( format );
        String result = null;
        try {
            result = sdf.format(date );
        } catch ( Exception ex) {
            Log.e( TAG,"Ошибка преобразования: "+ex.getMessage() );
        }
        return result;
    }

    public static float convertDpToPixel( float dp ){
        return dp * ((float) SFFSApplication.getAppContext().getResources()
                                .getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertPixelsToDp( float px ){
        return px / ((float) SFFSApplication.getAppContext().getResources()
                                .getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

}
