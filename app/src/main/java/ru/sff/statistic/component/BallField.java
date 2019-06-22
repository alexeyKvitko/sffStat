package ru.sff.statistic.component;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;

public class BallField extends LinearLayout {

    private String mBallNumberValue;
    private String mBallRepeatValue;

    private TextView mBallNumber;
    private TextView mBallRepeat;

    public BallField( Context context ) {
        super( context );
        inflate( context, R.layout.ball_field, this );
        initialize();
    }

    private void initialize(){}{
        mBallNumber = findViewById( R.id.ballNumberId );
        mBallRepeat = findViewById( R.id.ballRepeatId );
        mBallNumber.setTypeface( AppConstants.ROTONDA_BOLD );
        mBallRepeat.setTypeface( AppConstants.ROBOTO_CONDENCED );
    }

    public void setBallNumberValue( String ballNumberValue ) {
        this.mBallNumberValue = ballNumberValue;
        mBallNumber.setText( ballNumberValue );
    }

    public void setBallRepeatValue( String ballRepeatValue ) {
        this.mBallRepeatValue = ballRepeatValue;
        mBallRepeat.setText( ballRepeatValue );
    }
}
