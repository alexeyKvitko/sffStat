package ru.madest.statistic.model;

public enum RequestType {

    BY_DAY,
    BY_MONTH,
    BY_DAY_WEEK,
    BY_DAY_MONTH,
    BY_PERIOD,
    ALL_DRAW,
    DRAW_BETWEEN,
    BY_SUM,
    BALL_BETWEEN;


    public static boolean isDateRequest( RequestType requestType ){
        return BY_DAY.equals( requestType )
                || BY_MONTH.equals( requestType )
                    || BY_DAY_WEEK.equals( requestType )
                        || BY_DAY_MONTH.equals( requestType )
                            || BY_PERIOD.equals( requestType );
    }

    public static boolean isDrawRequest( RequestType requestType ){
        return ALL_DRAW.equals( requestType )
                || DRAW_BETWEEN.equals( requestType );
    }

    public static boolean isSumRequest( RequestType requestType ){
        return BY_SUM.equals( requestType )
                || BALL_BETWEEN.equals( requestType );
    }

}
