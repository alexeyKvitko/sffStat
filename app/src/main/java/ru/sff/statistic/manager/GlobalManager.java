package ru.sff.statistic.manager;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.model.Ball;
import ru.sff.statistic.model.BallSetType;
import ru.sff.statistic.model.BootstrapModel;
import ru.sff.statistic.model.CachedRequestByDraw;
import ru.sff.statistic.model.StoredBallSet;

public class GlobalManager {


    private static final GlobalManager GLOBAL_MANAGER = new GlobalManager();

    private static BootstrapModel bootstrapModel;
    private static BallSetType[] ballSetTypes;
    private static Integer[] fieldOrientation;
    private static String resultViewType;
    private static SortedMap<String, StoredBallSet > storedBallSet;
    private static CachedRequestByDraw cachedRequestByDraw;

    private GlobalManager() {
    }

    public static GlobalManager getInstance() {
        return GLOBAL_MANAGER;
    }

    public static void initialize(){
        storedBallSet = new TreeMap<>( ( String keyOne, String keyTwo ) -> {
                keyOne = keyOne.substring( keyOne.indexOf("(" )+1,keyOne.indexOf( ")" )  ).trim();
                keyTwo = keyTwo.substring( keyTwo.indexOf("(" )+1,keyTwo.indexOf( ")" )  ).trim();
                String[] keysOne = keyOne.split( "-" );
                String[] keysTwo = keyTwo.split( "-" );
                int deltaOne = Integer.valueOf( keysOne[1] ) - Integer.valueOf( keysOne[0] );
                int deltaTwo = Integer.valueOf( keysTwo[1] ) - Integer.valueOf( keysTwo[0] );
                return deltaTwo-deltaOne;
        } );
        setBallSetTypes( new BallSetType[]{BallSetType.BIGGER, BallSetType.LESS, BallSetType.MIDDLE } );
        setFieldOrientation( AppConstants.BALL_FROM_1 );
        setResultViewType( AppConstants.VIEW_TYPE_FALLING_COUNT );
        setCachedRequestByDraw( null );
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

    public static void setBootstrapModel( BootstrapModel bootstrapModel ) {
        GlobalManager.bootstrapModel = bootstrapModel;
    }

    public static Integer getPlayedDraws(){
        return bootstrapModel.getPlayedDraws();
    }

    public static List< Ball > getPlayedBalls(){
        return bootstrapModel.getPlayedBalls();
    }

    public static Ball getBallByNumber( Integer ballNumber ){
        Ball result = null;
        for( Ball ball : bootstrapModel.getPlayedBalls() ){
            if( ballNumber.equals( ball.getBallNumber() ) ){
                result = ball;
                break;
            }
        }
        return result;
    }

    public static CachedRequestByDraw getCachedRequestByDraw() {
        if (cachedRequestByDraw == null ){
            cachedRequestByDraw = new CachedRequestByDraw();
        }
        return cachedRequestByDraw;
    }

    public static void setCachedRequestByDraw( CachedRequestByDraw cachedRequestByDraw ) {
        GlobalManager.cachedRequestByDraw = cachedRequestByDraw;
    }
}
