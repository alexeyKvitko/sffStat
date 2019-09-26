package ru.sff.statistic.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;
import ru.sff.statistic.model.HeaderModel;

public class AppHeader extends LinearLayout {

    private HeaderModel mHeader;

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

    public void showHeader(){
        findViewById( R.id.appHeaderContainerId ).setVisibility( View.VISIBLE );
    }

    public void hideHeader(){
        findViewById( R.id.appHeaderContainerId ).setVisibility( View.GONE );
    }

    public void setHeader( HeaderModel header ) {
        mHeader = header;
        if ( header!= null ){
            ( ( TextView ) findViewById( R.id.lotoTypeId ) )
                    .setText( header.getTitle() != null ? header.getTitle() : "" );
            ( ( ImageView ) findViewById( R.id.emojiId ) )
                    .setImageDrawable( SFFSApplication.getAppContext()
                            .getResources().getDrawable( header.getEmogii()) );
        }
    }

    public HeaderModel getHeaderModel() {
        return mHeader;
    }
}
