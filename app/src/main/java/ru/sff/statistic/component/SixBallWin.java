package ru.sff.statistic.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;

public class SixBallWin extends BaseComponent {
    public SixBallWin( Context context ) {
        super( context );
        inflate( context, R.layout.six_ball_win, this );
        initBaseComponent( this );
    }

    public SixBallWin( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.six_ball_win, this );
        initBaseComponent( this );
    }

    public void setSixBallWins(Integer... balls){
        initTextView( R.id.ballWinOneId, AppConstants.ROTONDA_BOLD ).setText( balls[0].toString() );
        initTextView( R.id.ballWinTwoId, AppConstants.ROTONDA_BOLD ).setText( balls[1].toString() );
        initTextView( R.id.ballWinThreeId, AppConstants.ROTONDA_BOLD ).setText( balls[2].toString() );
        initTextView( R.id.ballWinFourId, AppConstants.ROTONDA_BOLD ).setText( balls[3].toString() );
        initTextView( R.id.ballWinFiveId, AppConstants.ROTONDA_BOLD ).setText( balls[4].toString() );
        initTextView( R.id.ballWinSixId, AppConstants.ROTONDA_BOLD ).setText( balls[5].toString() );
    }

    @Override
    public void onClick( View view ) {}
}
