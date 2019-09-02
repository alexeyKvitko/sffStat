package ru.sff.statistic;

import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

public abstract class AppConstants {

    public static final Typeface ROBOTO_CONDENCED = Typeface.createFromAsset( SFFSApplication.getAppContext().getAssets(), "font/RobotoCondensed.ttf" );
    public static final Typeface ROTONDA_BOLD = Typeface.createFromAsset( SFFSApplication.getAppContext().getAssets(), "font/RotondaBold.ttf" );
    public static final Typeface ROBOTO_BLACK = Typeface.createFromAsset( SFFSApplication.getAppContext().getAssets(), "font/Roboto-Black.ttf" );

    public static final String FLOAT_MENU_MESSAGE = "ru.sff.statistic.component.FLOAT_MENU_MESSAGE";
    public static final String ROUTE_ACTION_MESSAGE = "ru.sff.statistic.component.ROUTE_ACTION_MESSAGE";
    public static final String ROUTE_ACTION_TYPE = "ru.sff.statistic.component.ROUTE_ACTION_TYPE";

    public static final String FLOAT_MENU_ACTION = "ru.sff.statistic.component.FLOAT_MENU_ACTION";
    public static final String BALL_SELECT_ACTION = "ru.sff.statistic.component.BALL_SELECT_ACTION";
    public static final String DRAW_SELECT_ACTION = "ru.sff.statistic.component.DRAW_SELECT_ACTION";
    public static final String FLOAT_MENU_CHANGE_VIEW_TYPE = "ru.sff.statistic.component.FLOAT_MENU_CHANGE_VT";
    public static final String FLOAT_MENU_SHOW_BASKET = "ru.sff.statistic.component.FLOAT_MENU_SHOW_BASKET";

    public static final String SHOW_ALL_DRAW_SCREEN = "ru.sff.statistic.SHOW_ALL_DRAW_SCREEN";
    public static final String SHOW_BY_DRAW_SCREEN = "ru.sff.statistic.SHOW_BY_DRAW_SCREEN";
    public static final String SHOW_BY_DATE_SCREEN = "ru.sff.statistic.SHOW_BY_DATE_SCREEN";
    public static final String SHOW_BY_SUM_SCREEN = "ru.sff.statistic.SHOW_BY_SUM_SCREEN";
    public static final String SHOW_BY_TURN_SCREEN = "ru.sff.statistic.SHOW_BY_TURN_SCREEN";
    public static final String SHOW_BY_CONSIDER_SCREEN = "ru.sff.statistic.SHOW_BY_CONSIDER_SCREEN";
    public static final String ROUTE_ACTION = "ru.sff.statistic.ROUTE_ACTION";

    public static final String VIEW_TYPE_PERCENT = "view_type_percent";
    public static final String VIEW_TYPE_FALLING_COUNT = "view_type_falling_count";

    public static final String BALL_SET_TOTAL = "ВСЕ_ТИРАЖИ";

    public static final String BALL_SET_BIGGER = "ЧАСТО ( %s )";
    public static final String BALL_SET_LESS = "РЕДКО ( %s )";
    public static final String BALL_SET_MIDDLE = "СРЕДНЕЕ ( %s )";

    public static final String AUTH_BEARER = "Bearer ";

    public static final int FAKE_ID = -1;
    public static final int MIN_SUM = 21;
    public static final int MAX_SUM = 255;

    public static final int VERTICAL_ORIENTATION = 0;
    public static final int HORIZONTAL_ORIENTATION = 1;

    public static final int MIN_BALL= 1;
    public static final int MAX_BALL = 45;

    public static final String FULL_DATE_FORMAT = "dd MMMM yyyy в HH:mm";
    public static final String DATE_FORMAT = "dd MMMM yyyy";

    public static final String PICTURE_DIR = "stoloto";
    public static final String SNAPSHOT_FILENAME = "lotoapp";
    public static final String EXTENSION_JPG = ".png";
    public static final String EXTENSION_PNG = ".jpg";
    public static final String EXTENSION_NOMEDIA = ".nomedia";

    public static final String RED_BALL = "RED_BALL";
    public static final String BLUE_BALL = "BLUE_BALL";
    public static final String CYAN_BALL = "CYAN_BALL";
    public static final String YELLOW_BALL = "YELLOW_BALL";
    public static final String GREEN_BALL = "GREEN_BALL";
    public static final String VIALET_BALL = "VIALET_BALL";
    public static final String ORANGE_BALL = "ORANGE_BALL";
    public static final String GRAY_BALL = "GRAY_BALL";
    public static final String BROWN_BALL = "BROWN_BALL";

    public static final Map<String, String> DAY_OF_WEEK = new HashMap<String, String>(){{
        put("ПН","Понедельник");
        put("ВТ","Вторник");
        put("СР","Среда");
        put("ЧТ","Четверг");
        put("ПТ","Пятница");
        put("СБ","Суббота");
        put("ВС","Воскресенье");
    }};

    public static final Map<Integer, String> ALL_DAY_OF_WEEK = new HashMap<Integer, String>(){{
        put(1,"Понедельник");
        put(2,"Вторник");
        put(3,"Среда");
        put(4,"Четверг");
        put(5,"Пятница");
        put(6,"Суббота");
        put(7,"Воскресенье");
    }};

    public static final Map<Integer, String> ALL_DAY_OF_WEEK_SFX = new HashMap<Integer, String>(){{
        put(1,"Понедельникам");
        put(2,"Вторникам");
        put(3,"Средам");
        put(4,"Четвергам");
        put(5,"Пятницам");
        put(6,"Субботам");
        put(7,"Воскресеньям");
    }};

    public static final Map<Integer, String> ALL_OF_MONTH = new HashMap<Integer, String>(){{
        put(1,"Январь");
        put(2,"Февраль");
        put(3,"Март");
        put(4,"Апрель");
        put(5,"Май");
        put(6,"Июнь");
        put(7,"Июль");
        put(8,"Август");
        put(9,"Сентябрь");
        put(10,"Октябрь");
        put(11,"Ноябрь");
        put(12,"Декабрь");
    }};

    public static final String[] ALL_MONTH_SFX = new String[] { "Января","Февраля","Марта","Апреля",
            "Мая","Июня","Июля","Августа","Сентября","Октября","Ноября","Декабря"};

    public static final int HEADER_ACTION_REMOVE = -1;
    public static final int HEADER_ACTION_RESTORE = 1;

    public static final Integer[] BALL_FROM_1 = new Integer[] { 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15
            ,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48 };
    public static final Integer[] BALL_FROM_45 = new Integer[] { 48,47,46,45,44,43,42,41,40,39,38,37,36,35,34,33,
            32,31,30,29,28,27,26,25,24,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,0 };

    public static final Integer[] BALL_12_RIGHT = new Integer[] {48,25,26,27,28,29,30 ,47,24,9,10,11,12,31
            ,46,23,8,1,2,13,32,45,22,7,0,3,14,33,44,21,6,5,4,15,34,43,20,19,18,17,16,35,42,41,40,39,38,37,36};
    public static final Integer[] BALL_3_RIGHT = new Integer[] { 42,43,44,45,46,47,48,41,20,21,22,23,24,25
            ,40,19,6,7,8,9,26,39,18,5,0,1,10,27,38,17,4,3,2,11,28,37,16,15,14,13,12,29,36,35,34,33,32,31,30 };
    public static final Integer[] BALL_6_RIGHT = new Integer[] {36,37,38,39,40,41,42,35,16,17,18,19,20,43
        ,34,15,4,5,6,21,44,33,14,3,0,7,22,45,32,13,2,1,8,23,46,31,12,11,10,9,24,47,30,29,28,27,26,25,48};
    public static final Integer[] BALL_9_RIGHT = new Integer[] {30,31,32,33,34,35,36,29,12,13,14,15,16,37
        ,28,11,2,3,4,17,38,27,10,1,0,5,18,38,26,9,8,7,6,19,40,25,24,23,22,21,20,41,48,47,46,45,44,43,42};

    public static final Integer[] BALL_12_LEFT = new Integer[] {30,29,28,27,26,25,48,31,12,11,10,9,24,47,32,13,2,1,8,23,46
        ,33,14,3,0,7,22,45,34,15,4,5,6,21,44,35,13,17,18,19,20,43,36,37,38,39,40,41,42};
    public static final Integer[] BALL_3_LEFT = new Integer[] {36,35,34,33,32,31,30,37,16,15,14,13,12,29,38,17,4,3,2,11,28
        ,39,18,5,0,1,10,27,40,19,6,7,8,9,26,41,20,21,22,23,24,25,42,43,44,45,46,47,48};
    public static final Integer[] BALL_6_LEFT = new Integer[] {42,41,40,39,38,37,36,43,20,19,18,17,16,35,44,21,6,5,4,15,34
        ,45,22,7,0,3,14,33,46,23,8,1,2,13,32,47,24,9,10,11,12,31,48,25,26,27,28,29,30};
    public static final Integer[] BALL_9_LEFT = new Integer[] {48,47,46,45,44,43,42,25,24,23,22,21,20,41,26,9,8,7,6,19,40
        ,27,10,1,0,5,18,39,28,11,2,3,4,17,38,29,12,13,14,15,16,37,30,31,32,33,34,35,36};
    
}
