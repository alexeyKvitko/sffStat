package ru.sff.statistic.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.SFFSApplication;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.model.MagnetModel;

public abstract class AppUtils {

    private static final String TAG = "AppUtils";

    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = ( ConnectivityManager )
                SFFSApplication.getAppContext().getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void hideKeyboardFrom( Context context, View view ) {
        InputMethodManager imm = ( InputMethodManager ) context.getSystemService( Activity.INPUT_METHOD_SERVICE );
        if ( imm.isAcceptingText() ) {
            imm.hideSoftInputFromWindow( view.getWindowToken(), 0 );
        }
    }

    public  static String getStoredBallSetKey(String ballSet ){
        String drawPeriod = AppConstants.FAKE_ID == GlobalManager.getCachedRequestByDraw().getEndDraw() ?
                "1-"+GlobalManager.getPlayedDraws().toString()
                : GlobalManager.getCachedRequestByDraw().getStartDraw()+ "-" + GlobalManager.getCachedRequestByDraw().getEndDraw();
        return String.format( ballSet, drawPeriod );
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

    public static Date parseDate(String format , String strDate ) {
        SimpleDateFormat sdf = new SimpleDateFormat( format );
        Date result = null;
        try {
            result = sdf.parse( strDate );
        } catch ( ParseException | java.text.ParseException ex) {
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
        String source = (value+"");
        return getTimes( source );
    }

    public static String getTimes( String source ){
        String times = "раз";
        Integer intValue = Integer.valueOf( source );
        if ( intValue > 10 ){
            int lastTwoDigit = Integer.valueOf( source.substring( source.length()-2 ) );
            if ( lastTwoDigit > 10 && lastTwoDigit < 21 ){
                return times;
            }
        }
        int lastDigit = Integer.valueOf( source.substring( source.length()-1 ) );
        if ( lastDigit > 1 && lastDigit < 5 ){
            times = "раза";
        }
        return times;
    }


    public static String getDays( int value ){
        String source = (value+"");
        return getDays( source );
    }

    public static String getDays( String source ){
        String times = " дней";
        int intValue = Integer.valueOf( source );
        if ( intValue == 1 ){
            return " день";
        }
        if ( intValue > 4 && intValue < 22 ){
            return times;
        }
        int lastDigit = Integer.valueOf( source.substring( source.length()-1 ) );
        if ( lastDigit > 1 && lastDigit < 5 ){
            times = " дня";
        }
        return times;
    }

    public static MagnetModel getMaxMagnetNumber( List< MagnetModel > magnetModels ){
        int max = 0;
        MagnetModel maxMagnetModel =  null;
        for( MagnetModel magnetModel : magnetModels ){
            if( magnetModel.getCount() > max ){
                max = magnetModel.getCount();
                maxMagnetModel = magnetModel;
            }
        }
        return maxMagnetModel;
    }

}
