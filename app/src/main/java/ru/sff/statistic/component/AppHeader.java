package ru.sff.statistic.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;

public class AppHeader extends LinearLayout {

    public AppHeader( Context context ) {
        super( context );
        inflate( context, R.layout.header, this );
        init();
    }

    public AppHeader( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.header, this );
        init();
    }

    private void init() {
        ( ( TextView ) findViewById( R.id.statisticsTitleId ) ).setTypeface( AppConstants.ROTONDA_BOLD );
        ( ( TextView ) findViewById( R.id.lotoTypeId ) ).setTypeface( AppConstants.ROBOTO_CONDENCED );
    }

    public void setHeader( int emojiId, int titleId ) {
        setHeader( emojiId, SFFSApplication.getAppContext().getResources().getString( titleId ) );
    }

    public void setHeader( int emojiId, String title ) {
        ( ( TextView ) findViewById( R.id.lotoTypeId ) )
                .setText( title );
        ( ( ImageView ) findViewById( R.id.emojiId ) )
                .setImageDrawable( SFFSApplication.getAppContext()
                        .getResources().getDrawable( emojiId ) );

    }
}
