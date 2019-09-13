package ru.sff.statistic.manager;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.model.Ball;
import ru.sff.statistic.model.BallSetType;
import ru.sff.statistic.model.BootstrapModel;
import ru.sff.statistic.model.CachedResponseData;
import ru.sff.statistic.model.StoredBallSet;

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
}
