package ru.sff.statistic;

import android.graphics.Typeface;

public abstract class AppConstants {

    public static final Typeface ROBOTO_CONDENCED = Typeface.createFromAsset( SFFSApplication.getAppContext().getAssets(), "font/RobotoCondensed.ttf" );
    public static final Typeface ROTONDA_BOLD = Typeface.createFromAsset( SFFSApplication.getAppContext().getAssets(), "font/RotondaBold.ttf" );

    public static final String AUTH_BEARER = "Bearer ";

    public static final String FULL_DATE_FORMAT = "dd MMMM yyyy, HH:mm";

    public static final String RED_BALL = "RED_BALL";
    public static final String BLUE_BALL = "BLUE_BALL";
    public static final String CYAN_BALL = "CYAN_BALL";
    public static final String YELLOW_BALL = "YELLOW_BALL";
    public static final String GREEN_BALL = "GREEN_BALL";
    public static final String VIALET_BALL = "VIALET_BALL";
    public static final String ORANGE_BALL = "ORANGE_BALL";
    public static final String GRAY_BALL = "GRAY_BALL";
    public static final String BROWN_BALL = "BROWN_BALL";
}
