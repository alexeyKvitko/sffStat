package ru.sff.statistic.manager;

import ru.sff.statistic.model.BallSetType;

public class GlobalManager {


    private static final GlobalManager GLOBAL_MANAGER = new GlobalManager();

    private static BallSetType[] ballSetTypes;
    private static Integer[] fieldOrintation;


    private GlobalManager() {
    }


    public static GlobalManager getInstance() {
        return GLOBAL_MANAGER;
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
}
