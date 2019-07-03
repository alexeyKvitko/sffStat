package ru.sff.statistic.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import ru.sff.statistic.R;

public class DrawsPlaneInfo extends LinearLayout {

    public DrawsPlaneInfo( Context context ) {
        super( context );
        inflate(context, R.layout.draws_plane_info, this);
    }

    public DrawsPlaneInfo( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate(context, R.layout.draws_plane_info, this);
    }
}
