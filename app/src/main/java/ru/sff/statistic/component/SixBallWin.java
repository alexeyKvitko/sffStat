package ru.sff.statistic.component;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;

public class SixBallWin extends BaseComponent {

    private static Integer[] BALL_IDS = new Integer[]{ R.id.ballWinOneId, R.id.ballWinTwoId,
            R.id.ballWinThreeId, R.id.ballWinFourId, R.id.ballWinFiveId, R.id.ballWinSixId };

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
        int idx = 0;
        for( Integer ballId : BALL_IDS ){
            if ( balls[ idx ] != AppConstants.FAKE_ID ){
                initTextView( ballId, AppConstants.ROTONDA_BOLD ).setText( balls[ idx ].toString() );
            } else {
                ((View) initTextView( ballId, AppConstants.ROTONDA_BOLD ).getParent()).setVisibility( View.GONE );
            }
           idx++;
        }
    }

    public void setActiveBall( Integer ballNumber ){
        String digit = ballNumber.toString();
        for( Integer ballId : BALL_IDS ){
            TextView ballText = findViewById( ballId );
            if( digit.equals( ballText.getText().toString())  ){
                LinearLayout ballLayout = ( LinearLayout ) ballText.getParent();
                ballLayout.setBackground( SFFSApplication.getAppContext().getResources()
                                                        .getDrawable( R.drawable.circle_active ) );
                ballText.setTextSize( TypedValue.COMPLEX_UNIT_DIP, 18 );
                break;
            }
        }
    }

    @Override
    public void onClick( View view ) {}
}
