package ru.sff.statistic.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.model.Ball;
import ru.sff.statistic.model.BallSetType;
import ru.sff.statistic.utils.AppUtils;

public class SixBallSet extends LinearLayout {

    private static final String DEF_COLOR[] = new String[]{ AppConstants.CYAN_BALL,
            AppConstants.RED_BALL, AppConstants.GREEN_BALL, AppConstants.ORANGE_BALL,
            AppConstants.BLUE_BALL, AppConstants.VIALET_BALL, AppConstants.YELLOW_BALL
    };
    private static final int[] BALL_IDX = new int[] { R.id.colorBallOneId , R.id.colorBallTwoId
                                                    , R.id.colorBallThreeId , R.id.colorBallFourId
                                                    ,R.id.colorBallFiveId , R.id.colorBallSixId};

    private Ball[] mBallSet;
    private String[] mColors;

    public SixBallSet( Context context ) {
        super( context );
        inflate( context, R.layout.six_ball_set, this );
        init();
    }

    public SixBallSet( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.six_ball_set, this );
        init();
    }

    private void init() {
        mColors = new String[6];
    }

    public void setBallSet( Ball[] ballSet, BallSetType setType ) {
        this.mBallSet = ballSet;
        if( BallSetType.LESS.equals( setType ) ){
            for(int i = 0; i<6; i++){
                mColors[i] = AppConstants.GRAY_BALL;
            }
        } else if( BallSetType.MIDDLE.equals( setType ) ){
            for(int i = 0; i<6; i++){
                mColors[i] = AppConstants.BROWN_BALL;
            }
        } else {
            setBallColors();
        }
        for( int i = 0; i < 6; i++ ){
            ( ( ColorBall ) findViewById( BALL_IDX[ i ]) )
                    .setColorBall( mBallSet[ i ], mColors[ i ] );
        }
    }

    private void setBallColors(){
        int colorSet = 0;
        String existColor = "";
        while( colorSet < 6 ){
            int idx = AppUtils.getRandomBetweenRange( 0,6 );
            if( existColor.indexOf( idx+"" ) == -1 ){
                mColors[ colorSet ] = DEF_COLOR[ idx ];
                colorSet++;
                existColor += (idx+"");
            }
        }
    }
}
