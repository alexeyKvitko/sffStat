package ru.sff.statistic.component;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.model.Ball;
import ru.sff.statistic.model.BallSetType;

public class BallField extends LinearLayout {

    private Ball mBall;

    private TextView mBallNumber;
    private TextView mBallRepeat;
    private LinearLayout mBallLayout;

    public BallField(Context context) {
        super(context);
        inflate(context, R.layout.ball_field, this);
        init();
    }

    public BallField(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.ball_field, this);
        init();
    }

    private void init() {
        mBallNumber = this.findViewById(R.id.ballNumberId);
        mBallRepeat = this.findViewById(R.id.ballRepeatId);
        mBallLayout = this.findViewById(R.id.ballFieldLayoutId);
        mBallNumber.setTypeface(AppConstants.ROTONDA_BOLD);
        mBallRepeat.setTypeface(AppConstants.ROBOTO_CONDENCED);
    }

    public void setBall(Ball ball) {
        if ( ball == null ){
            mBallNumber.setVisibility( View.GONE );
            mBallRepeat.setVisibility( View.GONE );
            mBallLayout.setBackgroundColor( SFFSApplication.getAppContext().getResources()
                                                    .getColor( R.color.transparentBackground ) );
            return;
        }
        mBall = ball;
        mBallNumber.setText(mBall.getBallNumber()+"");
        mBallRepeat.setText(mBall.getBallRepeat()+"");
        if ( BallSetType.BIGGER.equals( GlobalManager.getSelBallType() )
                    && GlobalManager.getSelBallType().equals( mBall.getBallType() ) ) {
            setBiggerBall();
        }
        if ( BallSetType.LESS.equals( GlobalManager.getSelBallType() )
                && GlobalManager.getSelBallType().equals( mBall.getBallType() ) ) {
            setLessBall();
        }
        if ( BallSetType.MIDDLE.equals( GlobalManager.getSelBallType() )
                && GlobalManager.getSelBallType().equals( mBall.getBallType() ) ) {
            setMiddleBall();
        }

    }

    private void setBiggerBall(){
        Resources resources = SFFSApplication.getAppContext().getResources();
        mBallLayout.setBackground( resources.getDrawable(R.drawable.filled_circle));
        mBallNumber.setTextColor( resources.getColor( R.color.textYellowColor ) );
        mBallRepeat.setTextColor( resources.getColor( R.color.lightYellowColor ) );
    }

    private void setLessBall(){
        Resources resources = SFFSApplication.getAppContext().getResources();
        mBallLayout.setBackground( resources.getDrawable(R.drawable.less_circle));
        mBallNumber.setTextColor( resources.getColor( R.color.lightGrayColor ) );
        mBallRepeat.setTextColor( resources.getColor( R.color.lightGrayColor ) );
    }

    private void setMiddleBall(){
        Resources resources = SFFSApplication.getAppContext().getResources();
        mBallLayout.setBackground( resources.getDrawable(R.drawable.middle_circle));
        mBallNumber.setTextColor( resources.getColor( R.color.textYellowColor ) );
        mBallRepeat.setTextColor( resources.getColor( R.color.lightYellowColor ) );
    }



}
