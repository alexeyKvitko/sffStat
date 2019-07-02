package ru.sff.statistic.manager;

import java.util.HashMap;
import java.util.Map;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.model.Ball;
import ru.sff.statistic.model.BallSetType;
import ru.sff.statistic.model.StoredBallSet;

public class GlobalManager {


    private static final GlobalManager GLOBAL_MANAGER = new GlobalManager();

    private static BallSetType[] ballSetTypes;
    private static Integer[] fieldOrintation;
    private static String resultViewType;
    private static int totalDrawCount;
    private static Map<String, StoredBallSet > storedBallSet;

    private GlobalManager() {
    }

    public static GlobalManager getInstance() {
        return GLOBAL_MANAGER;
    }

    public static void initialize(){
        storedBallSet = new HashMap<>();
                setBallSetTypes( new BallSetType[]{BallSetType.BIGGER, BallSetType.LESS, BallSetType.MIDDLE } );
        setFieldOrintation( AppConstants.BALL_12_RIGHT );
        setResultViewType( AppConstants.VIEW_TYPE_FALLING_COUNT );
        setTotalDrawCount( 4487 );
    }

    public static BallSetType[] getBallSetTypes() {
        return ballSetTypes;
    }

    public static void setBallSetTypes( BallSetType[] ballSetTypes ) {
        GlobalManager.ballSetTypes = ballSetTypes;
    }

    public static Integer[] getFieldOrintation() {
        return fieldOrintation;
    }

    public static void setFieldOrintation( Integer[] fieldOrintation ) {
        GlobalManager.fieldOrintation = fieldOrintation;
    }

    public static String getResultViewType() {
        return resultViewType;
    }

    public static void setResultViewType( String resultViewType ) {
        GlobalManager.resultViewType = resultViewType;
    }

    public static int getTotalDrawCount() {
        return totalDrawCount;
    }

    public static void setTotalDrawCount( int totalDrawCount ) {
        GlobalManager.totalDrawCount = totalDrawCount;
    }

    public static Map< String, StoredBallSet > getStoredBallSet() {
        return storedBallSet;
    }
}
