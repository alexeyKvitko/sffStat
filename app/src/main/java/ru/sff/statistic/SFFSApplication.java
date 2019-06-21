package ru.sff.statistic;

import android.app.Application;
import android.content.Context;

public class SFFSApplication extends Application {

    private static Context context;

    public void onCreate(){
        super.onCreate();
        SFFSApplication.context = getApplicationContext();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static Context getAppContext() {
        return SFFSApplication.context;
    }
}
