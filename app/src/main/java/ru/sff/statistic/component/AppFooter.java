package ru.sff.statistic.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import ru.sff.statistic.R;

public class AppFooter extends LinearLayout {
    public AppFooter( Context context ) {
        super( context );
        inflate( context, R.layout.footer, this );
    }

    public AppFooter( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.footer, this );
    }
}
