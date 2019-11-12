package ru.madest.statistic.manager;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ru.madest.statistic.AppConstants;
import ru.madest.statistic.model.Ball;
import ru.madest.statistic.model.BallSetType;
import ru.madest.statistic.model.BootstrapModel;
import ru.madest.statistic.model.CachedResponseData;
import ru.madest.statistic.model.PreferenceBasket;
import ru.madest.statistic.model.StoredBallSet;
import ru.madest.statistic.rest.SaveBallSetTask;
import ru.madest.statistic.utils.AppPreferences;

public class GlobalManager {


    private static final GlobalManager GLOBAL_MANAGER = new GlobalManager();

    private static BootstrapModel bootstrapModel;
    private static BallSetType[] ballSetTypes;
    private static Integer[] fieldOrientation;
    private static String resultViewType;
    private static int lastMenuHeight;
    private static Map< String, StoredBallSet > storedBallSet;
    private static CachedResponseData cachedResponseData;
    private static boolean showLastFallBallSet;
    private static boolean backendBusy;

    private GlobalManager() {
    }

    public static GlobalManager getInstance() {
        return GLOBAL_MANAGER;
    }

    public static void initialize() {
        storedBallSet = new LinkedHashMap<>();
        setAllBallSetTypes();
        setFieldOrientation( AppConstants.BALL_FROM_1 );
        setResultViewType( AppConstants.VIEW_TYPE_FALLING_COUNT );
        setCachedResponseData( null );
        setShowLastFallBallSet( false );
    }

    public static void setAllBallSetTypes(){
        setBallSetTypes( new BallSetType[]{ BallSetType.BIGGER, BallSetType.LESS, BallSetType.MIDDLE, BallSetType.CUSTOM, BallSetType.COMBY} );
    }

    public static BallSetType[] getBallSetTypes() {
        return ballSetTypes;
    }

    public static void setBallSetTypes( BallSetType[] ballSetTypes ) {
        GlobalManager.ballSetTypes = ballSetTypes;
    }

    public static Integer[] getFieldOrientation() {
        return fieldOrientation;
    }

    public static void setFieldOrientation( Integer[] fieldOrientation ) {
        GlobalManager.fieldOrientation = fieldOrientation;
    }

    public static String getResultViewType() {
        return resultViewType;
    }

    public static void setResultViewType( String resultViewType ) {
        GlobalManager.resultViewType = resultViewType;
    }


    public static Map< String, StoredBallSet > getStoredBallSet() {
        return storedBallSet;
    }

    public static void setStoredBallSet( Map< String, StoredBallSet > storedBallSet ) {
        GlobalManager.storedBallSet = storedBallSet;
    }

    public static List<StoredBallSet> getSortedStoredBallSet(){
        List<StoredBallSet> sorted = new LinkedList<>( storedBallSet.values() );
        Collections.sort( sorted );
        return sorted;
    }

    public static void setBootstrapModel( BootstrapModel bootstrapModel ) {
        GlobalManager.bootstrapModel = bootstrapModel;
    }

    public static BootstrapModel getBootstrapModel() {
        return bootstrapModel;
    }

    public static Integer getPlayedDraws() {
        return bootstrapModel.getPlayedDraws();
    }

    public static List< Ball > getPlayedBalls() {
        return bootstrapModel.getPlayedBalls();
    }

    public static Ball getBallByNumber( Integer ballNumber ) {
        Ball result = null;
        for ( Ball ball : bootstrapModel.getPlayedBalls() ) {
            if ( ballNumber.equals( ball.getBallNumber() ) ) {
                result = ball;
                break;
            }
        }
        return result;
    }

    public static int getLastMenuHeight() {
        return lastMenuHeight;
    }

    public static void setLastMenuHeight( int lastMenuHeight ) {
        GlobalManager.lastMenuHeight = lastMenuHeight;
    }

    public static CachedResponseData getCachedResponseData() {
        return cachedResponseData;
    }

    public static void setCachedResponseData( CachedResponseData cachedResponseData ) {
        GlobalManager.cachedResponseData = cachedResponseData;
    }

    public static boolean isShowLastFallBallSet() {
        return showLastFallBallSet;
    }

    public static void setShowLastFallBallSet( boolean showLastFallBallSet ) {
        GlobalManager.showLastFallBallSet = showLastFallBallSet;
    }

    public static boolean isBackendBusy() {
        return backendBusy;
    }

    public static void setBackendBusy(boolean backendBusy) {
        GlobalManager.backendBusy = backendBusy;
    }

    public static void addToStoredBallSet( String basketName, StoredBallSet storedBallSet ){
       getStoredBallSet().put( basketName, storedBallSet );
       AppPreferences.setPreference( AppConstants.BASKET_PREF, new PreferenceBasket( getStoredBallSet() ).get()  );
       if ( BallSetType.CUSTOM.equals( storedBallSet.getBallSetType() ) ){
           new SaveBallSetTask().execute( storedBallSet );
       }
    }

    public static boolean removeFromStoredBallSet( String basketName ){
        boolean empty = false;
        getStoredBallSet().remove( basketName );
        if ( getStoredBallSet().isEmpty() ){
                AppPreferences.removePreference( AppConstants.BASKET_PREF );
                empty = true;
            } else {
                AppPreferences.setPreference( AppConstants.BASKET_PREF, new PreferenceBasket( getStoredBallSet() ).get()  );
            }
        return empty;
    }


    public static int getUserLevel(){
        return bootstrapModel.getLevel();
    }


}
