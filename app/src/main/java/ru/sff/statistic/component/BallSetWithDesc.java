package ru.sff.statistic.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.model.Ball;
import ru.sff.statistic.model.MagnetNumber;
import ru.sff.statistic.utils.AppUtils;

public class BallSetWithDesc extends BaseComponent {

    private Integer mDraw;

    public BallSetWithDesc( Context context ) {
        super( context );
        inflate( context, R.layout.ball_set_desc, this );
        initialize();
    }

    public BallSetWithDesc( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.ball_set_desc, this );
        initialize();
    }

    private void initialize() {
        initBaseComponent( this );
    }

    public void setBallSetWithDigit( Ball[] balls, Integer draw, Integer digit ) {
        mDraw = draw;
        SixBallWin hitBalls = findViewById( R.id.ballSetDigitId );
        hitBalls.setSixBallWins( balls[0].getBallNumber(), balls[1].getBallNumber(),balls[2].getBallNumber(),
                                    balls[3].getBallNumber(), balls[4].getBallNumber(),balls[5].getBallNumber() );
        hitBalls.setActiveBall( digit );
        initTextView( R.id.ballSetCountId, AppConstants.ROTONDA_BOLD ).setText( draw.toString() );
        TextView descText = initTextView( R.id.ballSetDescId, AppConstants.ROBOTO_CONDENCED );
        descText.setText( "Тираж" );
        descText.setVisibility( View.VISIBLE );
    }

    public void setBallSetWithDigit( MagnetNumber magnetNumber, Integer digit ) {
        String[] balls = magnetNumber.getCombination().replaceAll( "<", "" ).replaceAll( ">", "" ).split( "," );
        String magnetCount = "";
        if ( magnetNumber != null ) {
            magnetCount = " - " + magnetNumber.getCount().toString() + " " + AppUtils.getTimes( magnetNumber.getCount() );
        }
        initTextView( R.id.ballSetCountId, AppConstants.ROTONDA_BOLD ).setText( magnetCount );
        SixBallWin pairBalls = findViewById( R.id.ballSetDigitId );
        switch ( balls.length ) {
            case 2:
                pairBalls.setSixBallWins( Integer.valueOf( balls[ 0 ].trim() ), Integer.valueOf( balls[ 1 ].trim() ),
                        AppConstants.FAKE_ID, AppConstants.FAKE_ID, AppConstants.FAKE_ID, AppConstants.FAKE_ID );
                break;
            case 3:
                pairBalls.setSixBallWins( Integer.valueOf( balls[ 0 ].trim() ), Integer.valueOf( balls[ 1 ].trim() ),
                        Integer.valueOf( balls[ 2 ].trim() ), AppConstants.FAKE_ID, AppConstants.FAKE_ID, AppConstants.FAKE_ID );
                break;
            case 4:
                pairBalls.setSixBallWins( Integer.valueOf( balls[ 0 ].trim() ), Integer.valueOf( balls[ 1 ].trim() ),
                        Integer.valueOf( balls[ 2 ].trim() ), Integer.valueOf( balls[ 3 ].trim() )
                                                    , AppConstants.FAKE_ID, AppConstants.FAKE_ID );
                break;
            default:
                break;
        }
        pairBalls.setActiveBall( digit );
    }

    public Integer getDraw() {
        return mDraw;
    }

    @Override
    public void onClick( View view ) {

    }
}
