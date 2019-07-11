package ru.sff.statistic.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.sff.statistic.AppConstants;
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
        SimpleDateFormat sdf = new SimpleDateFormat( format, new Locale( "ru" ) );
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


    public static String getFormatedString( Long value ){
        String temp = value.toString();
        return getFormatedString( temp );
    }

    public static String getFormatedString( String temp ){
        StringBuilder result = new StringBuilder();
        if( temp.length() <= 3 ){
            return temp;
        }
        if( temp.length() <= 6 ){
            int idx = temp.length() - 3;
            result.append( temp.substring( 0,idx ) ).append( " " ).append( temp.substring( idx ) );
        }
        if ( temp.length() > 6 && temp.length() <= 9 ){
            int idx1 = temp.length() - 3;
            int idx = temp.length() - 6;
            result.append( temp.substring( 0,idx ) ).append( " " ).append( temp.substring( idx, idx1 ) )
                    .append( " " ).append( temp.substring( idx1 ) );
        }
        if ( temp.length() > 9 && temp.length() <= 12 ){
            int idx2 = temp.length() - 3;
            int idx1 = temp.length() - 6;
            int idx = temp.length() - 9;
            result.append( temp.substring( 0,idx ) ).append( " " ).append( temp.substring( idx, idx1 ) )
                    .append( " " ).append( temp.substring( idx1,idx2 ) )
                    .append( " " ).append( temp.substring( idx2 ) );
        }
        return result.toString();
    }

    public static boolean isStorageReady() {
        return Environment.MEDIA_MOUNTED.equals( Environment.getExternalStorageState() );
    }

    public static String getSnapshotPath(){
        return Environment.getExternalStorageDirectory()+ File.separator+ AppConstants.PICTURE_DIR
                +File.separator+AppConstants.SNAPSHOT_FILENAME + AppConstants.EXTENSION_JPG;
    }

    public static String getTimes( int value ){
        String times = "раз";
        String source = (value+"");
        int lastDigit = Integer.valueOf( source.substring( source.length()-1 ) );
        if ( lastDigit > 1 && lastDigit < 5 ){
            times = "раза";
        }
        return times;
    }

}
