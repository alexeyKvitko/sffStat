package ru.sff.statistic.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.model.Ball;
import ru.sff.statistic.model.BallSetType;

public class SixBallSet extends LinearLayout {

    private static final String DEF_COLOR[] = new String[]{ AppConstants.RED_BALL, AppConstants.BLUE_BALL,
            AppConstants.ORANGE_BALL, AppConstants.CYAN_BALL, AppConstants.GREEN_BALL, AppConstants.YELLOW_BALL };

    private static final int[] BALL_IDX = new int[]{ R.id.colorBallOneId, R.id.colorBallTwoId
            , R.id.colorBallThreeId, R.id.colorBallFourId
            , R.id.colorBallFiveId, R.id.colorBallSixId };

    private Ball[] mBallSet;
    private BallSetType mSetType;

    public SixBallSet( Context context ) {
        super( context );
        inflate( context, R.layout.six_ball_set, this );
    }

    public SixBallSet( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.six_ball_set, this );
    }

    public void redrawBalls(){
        String[] colors = new String[6];
        if ( BallSetType.LESS.equals( mSetType ) ) {
            for ( int i = 0; i < 6; i++ ) {
                colors[ i ] = AppConstants.GRAY_BALL;
            }
        } else if ( BallSetType.MIDDLE.equals( mSetType ) ) {
            for ( int i = 0; i < 6; i++ ) {
                colors[ i ] = AppConstants.BROWN_BALL;
            }
        } else {
            colors = DEF_COLOR;
        }
        for ( int i = 0; i < 6; i++ ) {
            ( ( ColorBall ) findViewById( BALL_IDX[ i ] ) )
                    .setColorBall( mBallSet[ i ], colors[ i ] );
        }
    }

    public void setBallSet( Ball[] ballSet, BallSetType setType ) {
        this.mBallSet = ballSet;
        this.mSetType = setType;
        redrawBalls();
    }


}
