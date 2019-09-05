package ru.sff.statistic.activity;

import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class BaseActivity extends AppCompatActivity {

    protected FrameLayout mMainRouteContainer;

    protected ConstraintLayout mActivityContainer;

    public ConstraintLayout getActivityContainer() {
        return mActivityContainer;
    }

    public FrameLayout getMainRouteContainer() {
        return mMainRouteContainer;
    }
}
