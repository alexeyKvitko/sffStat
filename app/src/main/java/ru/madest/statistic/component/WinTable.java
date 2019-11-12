package ru.madest.statistic.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import ru.madest.statistic.AppConstants;
import ru.madest.statistic.R;
import ru.madest.statistic.utils.AppUtils;

public class WinTable extends BaseComponent {

    public WinTable( Context context ) {
        super( context );
        inflate( context, R.layout.win_table, this );
        init();
    }

    public WinTable( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.win_table, this );
        init();
    }

    private void init(){
        initBaseComponent( this );
        initTextView( R.id.winNumberId, AppConstants.ROBOTO_CONDENCED );
        initTextView( R.id.winNumberCountId, AppConstants.ROBOTO_CONDENCED );

        initTextView( R.id.winNumberSixId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.winNumberFiveId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.winNumberFourId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.winNumberThreeId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.winNumberTwoId, AppConstants.ROTONDA_BOLD );
    }

    public void setWinBalls( Long ballSixCount,Long ballFiveCount,Long ballFourCount,
                             Long ballThreeCount,Long ballTwoCount){

        initTextView( R.id.winNumberSixCountId, AppConstants.ROTONDA_BOLD )
                .setText( AppUtils.getFormatedString( ballSixCount ) );
        initTextView( R.id.winNumberFiveCountId, AppConstants.ROTONDA_BOLD )
                .setText( AppUtils.getFormatedString( ballFiveCount ) );
        initTextView( R.id.winNumberFourCountId, AppConstants.ROTONDA_BOLD )
                .setText( AppUtils.getFormatedString( ballFourCount ) );
        initTextView( R.id.winNumberThreeCountId, AppConstants.ROTONDA_BOLD )
                .setText( AppUtils.getFormatedString( ballThreeCount ) );
        initTextView( R.id.winNumberTwoCountId, AppConstants.ROTONDA_BOLD )
                .setText( AppUtils.getFormatedString( ballTwoCount ) );
    }

    @Override
    public void onClick( View view ) {

    }
}
