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
import ru.sff.statistic.model.MagnetModel;
import ru.sff.statistic.model.RequestByDate;

import static ru.sff.statistic.manager.GlobalManager.getCachedResponseData;
import static ru.sff.statistic.manager.GlobalManager.getPlayedDraws;

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
        String drawPeriod = "";
        switch ( getCachedResponseData().getLastRequest() ){
            case BY_DAY:
                drawPeriod = "по " + getCachedResponseData().getRequestByDate().getDay() + " числам";
                break;
            case BY_MONTH:
                drawPeriod = AppConstants.ALL_OF_MONTH.get( getCachedResponseData().getRequestByDate().getMonth() )+" месяц";
                break;
            case BY_DAY_WEEK:
                drawPeriod = AppConstants.ALL_DAY_OF_WEEK.get( getCachedResponseData().getRequestByDate().getDayOfWeek() )+"";
                break;
            case BY_DAY_MONTH:
                drawPeriod = getCachedResponseData().getRequestByDate().getDayNumber()+" "+AppConstants.ALL_MONTH_SFX[getCachedResponseData().getRequestByDate().getMonthNumber()-1];
                break;
            case BY_PERIOD:
                drawPeriod = getCachedResponseData().getRequestByDate().getStartDay()+" - "+
                                            getCachedResponseData().getRequestByDate().getEndDay();
                break;
            case ALL_DRAW:
                 drawPeriod = "1-"+ getPlayedDraws().toString();
                break;
            case DRAW_BETWEEN:
                drawPeriod = getCachedResponseData().getRequestByDraw().getStartDraw()
                        + "-" + getCachedResponseData().getRequestByDraw().getEndDraw();
                break;
            case BY_SUM:
                drawPeriod = "S "+getCachedResponseData().getRequestBySOB().getBegin()
                        + "-" + getCachedResponseData().getRequestBySOB().getEnd();
                break;
            case BALL_BETWEEN:
                drawPeriod = getCachedResponseData().getRequestBySOB().getBegin()
                        + ":" + getCachedResponseData().getRequestBySOB().getEnd();
                break;
        }
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

    public static String getDraws( int source ){
        String draws = " тиражей";
        if ( source > 4 && source < 21 ){
            return source + draws;
        }
        int lastDigit = Integer.valueOf( (source+"").substring( (source+"").length()-1 ) );
        if ( lastDigit == 1 ){
            draws = " тираж";
        }
        if ( lastDigit > 1 && lastDigit < 5 ){
            draws = " тиража";
        }
        return source+draws;
    }

    public static String getDrawsSfx( int source ){
        String draws = " тиражей";
        if ( source > 4 && source < 21 ){
            return draws;
        }
        int lastDigit = Integer.valueOf( (source+"").substring( (source+"").length()-1 ) );
        if ( lastDigit == 1 ){
            draws = " тираж";
        }
        if ( lastDigit > 1 && lastDigit < 5 ){
            draws = " тиража";
        }
        return draws;
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

    public static String getRequestByDateHeader( RequestByDate requestByDate ){
        String header = "По Датам";
        switch ( requestByDate.getRequestType() ) {
            case BY_DAY:
                header = "за " + requestByDate.getDay() + " день месяца";
                break;
            case BY_MONTH:
                header = "за " + AppConstants.ALL_OF_MONTH.get( requestByDate.getMonth() ) + " месяц";
                break;
            case BY_DAY_WEEK:
                header = "по " + AppConstants.ALL_DAY_OF_WEEK_SFX.get( requestByDate.getDayOfWeek() );
                break;
            case BY_DAY_MONTH:
                header = "за " + requestByDate.getDayNumber() + " " + AppConstants.ALL_MONTH_SFX[ requestByDate.getMonthNumber() ];
                break;
            case BY_PERIOD:
                header = "c " + requestByDate.getStartDay() + " по " + requestByDate.getEndDay();
                break;
            default:
                break;

        }
        return header;
    }

}
