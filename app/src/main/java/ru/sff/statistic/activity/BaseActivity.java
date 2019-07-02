package ru.sff.statistic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class BaseActivity extends AppCompatActivity {

    protected ConstraintLayout mActivityContainer;

    public ConstraintLayout getActivityContainer() {
        return mActivityContainer;
    }


}
