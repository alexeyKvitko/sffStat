package ru.sff.statistic.manager;

import ru.sff.statistic.model.BallSetType;

public class GlobalManager {


    private static final GlobalManager GLOBAL_MANAGER = new GlobalManager();

    private static BallSetType selBallType;


    private GlobalManager() {
    }


    public static GlobalManager getInstance() {
        return GLOBAL_MANAGER;
    }

    public static BallSetType getSelBallType() {
        return selBallType;
    }

    public static void setSelBallType( BallSetType selBallType ) {
        GlobalManager.selBallType = selBallType;
    }
}
