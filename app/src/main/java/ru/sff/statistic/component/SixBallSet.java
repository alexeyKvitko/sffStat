package ru.sff.statistic.component;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;
import ru.sff.statistic.model.Ball;
import ru.sff.statistic.model.BallSetType;
import ru.sff.statistic.utils.CustomAnimation;

public class SixBallSet extends BaseComponent {

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
        initialize();
    }

    public SixBallSet( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.six_ball_set, this );
        initialize();
    }

    private void initialize(){
        initBaseComponent( this );
        setThisOnClickListener( R.id.colorBallOneId, R.id.colorBallTwoId, R.id.colorBallThreeId,
                                R.id.colorBallFourId, R.id.colorBallFiveId, R.id.colorBallSixId );
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
        } else if ( BallSetType.CUSTOM.equals( mSetType ) ) {
            for ( int i = 0; i < 6; i++ ) {
                colors[ i ] = AppConstants.VIALET_BALL;
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

    public void hideRepeatCaption(){
        for ( int i = 0; i < 6; i++ ) {
            ( ( ColorBall ) findViewById( BALL_IDX[ i ] ) ).hideCaption();
        }
    }

    public void setWinBall(){
        for ( int i = 0; i < 6; i++ ) {
            ( ( ColorBall ) findViewById( BALL_IDX[ i ] ) ).setWinBall();
        }
    }


    @Override
    public void onClick( View view ) {
        CustomAnimation.clickAnimation( view );
        Intent intent = new Intent( AppConstants.ROUTE_ACTION_MESSAGE );
        intent.putExtra( AppConstants.ROUTE_ACTION_TYPE, AppConstants.BALL_SELECT_ACTION );
        intent.putExtra( AppConstants.BALL_SELECT_ACTION, (( ColorBall) view).getBall() );
        SFFSApplication.getAppContext().sendBroadcast( intent );
    }
}
