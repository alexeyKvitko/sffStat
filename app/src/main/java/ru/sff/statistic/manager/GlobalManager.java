package ru.sff.statistic.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.model.Ball;
import ru.sff.statistic.model.BallSetType;
import ru.sff.statistic.model.BootstrapModel;
import ru.sff.statistic.model.StoredBallSet;

public class GlobalManager {


    private static final GlobalManager GLOBAL_MANAGER = new GlobalManager();

    private static BootstrapModel bootstrapModel;
    private static BallSetType[] ballSetTypes;
    private static Integer[] fieldOrintation;
    private static String resultViewType;
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

}
