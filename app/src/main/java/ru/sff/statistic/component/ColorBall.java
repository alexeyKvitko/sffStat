package ru.sff.statistic.component;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;
import ru.sff.statistic.model.Ball;

public class ColorBall extends LinearLayout {

    private Ball mBall;
    private String mColor;

    private LinearLayout mColorBall;
    private TextView mBallNumber;
    private TextView mBallRepeat;

    public ColorBall(Context context) {
        super(context);
        inflate(context, R.layout.color_ball, this);
        init();
    }

    public ColorBall(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.color_ball, this);
        init();
    }

    private void init(){
        mColorBall = findViewById( R.id.colorBallLayoutId );
        mBallNumber = findViewById( R.id.colorBallNumberId );
        mBallNumber.setTypeface( AppConstants.ROTONDA_BOLD );
        mBallRepeat = findViewById( R.id.colorBallRepeatId );
        mBallRepeat.setTypeface( AppConstants.ROTONDA_BOLD );
    }

    public void setColorBall(Ball ball, String color ){
        mBall = ball;
        mBallNumber.setText( mBall.getBallNumber()+"" );
        mBallRepeat.setText( mBall.getBallRepeat()+"" );
        mColor = color;
        Resources resources = SFFSApplication.getAppContext().getResources();
        switch ( color ){
            case AppConstants.RED_BALL:
                mColorBall.setBackground( resources.getDrawable( R.drawable.ball_red ) );
                mBallNumber.setTextColor( resources.getColor( R.color.ballRed ) );
                break;
            case AppConstants.BLUE_BALL:
                mColorBall.setBackground( resources.getDrawable( R.drawable.ball_blue ) );
                mBallNumber.setTextColor( resources.getColor( R.color.ballBlue ) );
                break;
            case AppConstants.GREEN_BALL:
                mColorBall.setBackground( resources.getDrawable( R.drawable.ball_green ) );
                mBallNumber.setTextColor( resources.getColor( R.color.ballGreen ) );
                break;
            case AppConstants.YELLOW_BALL:
                mColorBall.setBackground( resources.getDrawable( R.drawable.ball_yellow ) );
                mBallNumber.setTextColor( resources.getColor( R.color.ballYellow ) );
                break;
            case AppConstants.CYAN_BALL:
                mColorBall.setBackground( resources.getDrawable( R.drawable.ball_cyan ) );
                mBallNumber.setTextColor( resources.getColor( R.color.ballCyan ) );
                break;
            case AppConstants.ORANGE_BALL:
                mColorBall.setBackground( resources.getDrawable( R.drawable.ball_orange ) );
                mBallNumber.setTextColor( resources.getColor( R.color.ballOrange ) );
                break;
            case AppConstants.VIALET_BALL:
                mColorBall.setBackground( resources.getDrawable( R.drawable.ball_vialet ) );
                mBallNumber.setTextColor( resources.getColor( R.color.ballVialet ) );
                break;
            case AppConstants.GRAY_BALL:
                mColorBall.setBackground( resources.getDrawable( R.drawable.ball_gray ) );
                mBallNumber.setTextColor( resources.getColor( R.color.ballGray ) );
                break;
            case AppConstants.BROWN_BALL:
                mColorBall.setBackground( resources.getDrawable( R.drawable.ball_brown ) );
                mBallNumber.setTextColor( resources.getColor( R.color.ballBrown ) );
                break;
        }
    }
}
