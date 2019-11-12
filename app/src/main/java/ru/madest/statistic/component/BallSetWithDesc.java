package ru.madest.statistic.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ru.madest.statistic.AppConstants;
import ru.madest.statistic.R;
import ru.madest.statistic.SFFSApplication;
import ru.madest.statistic.model.Ball;
import ru.madest.statistic.model.MagnetModel;
import ru.madest.statistic.utils.AppUtils;

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

        TextView drawText = initTextView( R.id.ballSetCountId, AppConstants.ROTONDA_BOLD );
        drawText.setText( draw.toString() );
        drawText.setBackground( SFFSApplication.getAppContext().getResources().getDrawable( R.drawable.border_bottom ) );

        TextView descText = initTextView( R.id.ballSetDescId, AppConstants.ROBOTO_CONDENCED );
        descText.setText( "Тираж" );
        descText.setVisibility( View.VISIBLE );
    }

    public void setEmptySet(String emptyMsg){
        TextView descText = initTextView( R.id.ballSetDescId, AppConstants.ROBOTO_CONDENCED );
        descText.setText( emptyMsg );
        descText.setTextColor( SFFSApplication.getAppContext().getResources().getColor( R.color.grayTextColor ) );
        descText.setVisibility( View.VISIBLE );
        findViewById( R.id.ballSetDigitId ).setVisibility( View.GONE );
    }

    public void setBallSetWithDigit( String[] ballSet, Integer digit ){
        int len = ballSet.length;
        String[] balls = new String[ len-1 ];
        for( int i = 0; i < len-1; i++ ){
            balls[i] =ballSet[i];
        }
        setBallSetWithDigit( balls, digit, ballSet[len-1] );
    }

    private void setBallSetWithDigit( String[] balls, Integer digit, String magnetCount ) {
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


    public void setBallSetWithDigit( MagnetModel magnetModel, Integer digit ) {
        String[] balls = magnetModel.getCombination().replaceAll( "<", "" ).replaceAll( ">", "" ).split( "," );
        String magnetCount = "";
        if ( magnetModel != null ) {
            magnetCount = " - " + magnetModel.getCount().toString() + " " + AppUtils.getTimes( magnetModel.getCount() );
        }
        setBallSetWithDigit( balls, digit, magnetCount );
    }

    public Integer getDraw() {
        return mDraw;
    }

    @Override
    public void onClick( View view ) {

    }
}
