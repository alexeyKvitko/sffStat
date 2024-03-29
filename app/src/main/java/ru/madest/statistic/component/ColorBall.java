package ru.madest.statistic.component;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ru.madest.statistic.AppConstants;
import ru.madest.statistic.R;
import ru.madest.statistic.SFFSApplication;
import ru.madest.statistic.manager.GlobalManager;
import ru.madest.statistic.model.Ball;
import ru.madest.statistic.model.BallSetType;
import ru.madest.statistic.model.LotoModel;

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
        String repeatCount = mBall.getBallRepeat()+"";
        if( AppConstants.VIEW_TYPE_PERCENT.equals(  GlobalManager.getResultViewType() ) ){
            repeatCount =  (mBall.getBallRepeat()*100/GlobalManager.getPlayedDraws())+"%";
        }
        if ( BallSetType.RARE.equals( mBall.getBallType() ) ){
            repeatCount = mBall.getDrawRange()+" / " + mBall.getAvgRange();
            if( mBall.getDrawRange() > 99 ){
                mBallRepeat.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10f);
            }
        }
        mBallRepeat.setText( repeatCount );
        mColor = color;
        switch ( color ){
            case AppConstants.RED_BALL:
                applyColorToBall ( R.drawable.ball_red , R.drawable.border_repeat_red, R.color.ballRed );
                break;
            case AppConstants.BLUE_BALL:
                applyColorToBall ( R.drawable.ball_blue , R.drawable.border_repeat_blue, R.color.ballBlue );
                break;
            case AppConstants.GREEN_BALL:
                applyColorToBall ( R.drawable.ball_green , R.drawable.border_repeat_green, R.color.ballGreen );
                break;
            case AppConstants.YELLOW_BALL:
                applyColorToBall ( R.drawable.ball_yellow , R.drawable.border_repeat_yellow, R.color.ballYellow );
                break;
            case AppConstants.CYAN_BALL:
                applyColorToBall ( R.drawable.ball_cyan , R.drawable.border_repeat_cyan, R.color.ballCyan );
                break;
            case AppConstants.ORANGE_BALL:
                applyColorToBall ( R.drawable.ball_orange , R.drawable.border_repeat_orange, R.color.ballOrange );
                break;
            case AppConstants.VIALET_BALL:
                mBallRepeat.setVisibility( View.GONE );
                applyColorToBall ( R.drawable.ball_vialet , R.drawable.border_repeat_vialet, R.color.ballVialet );
                break;
            case AppConstants.GRAY_BALL:
                applyColorToBall ( R.drawable.ball_gray , R.drawable.border_repeat_gray, R.color.ballGray );
                break;
            case AppConstants.BROWN_BALL:
                applyColorToBall ( R.drawable.middle_circle , R.drawable.border_repeat_brown, R.color.ballBrown );
                break;
            case AppConstants.GOLD_BALL:
                applyColorToBall ( R.drawable.ball_stoloto_win , R.drawable.border_repeat_yellow, R.color.goldColor );
                break;

        }
    }

    public void hideCaption(){
        mBallRepeat.setVisibility( View.GONE );
    }

    public void setWinBall(){
        if ( GlobalManager.isShowLastFallBallSet() ){
            LotoModel lotoModel = GlobalManager.getBootstrapModel().getLastFall();
            if ( mBall.getBallNumber() == lotoModel.getSlotOne()
                    || mBall.getBallNumber() == lotoModel.getSlotTwo()
                    || mBall.getBallNumber() == lotoModel.getSlotThree()
                    || mBall.getBallNumber() == lotoModel.getSlotFour()
                    || mBall.getBallNumber() == lotoModel.getSlotFive()
                    || mBall.getBallNumber() == lotoModel.getSlotSix() ){
                applyColorToBall ( R.drawable.ball_stoloto_win , R.drawable.border_repeat_yellow, R.color.goldColor );
            }
        }
    }

    public void selectColorBall(){
        applyColorToBall ( R.drawable.ball_vialet_sel, R.drawable.border_repeat_vialet, R.color.ballVialet );
    }

    private void applyColorToBall( int drawableId, int borderId, int colorId ){
        Resources resources = SFFSApplication.getAppContext().getResources();
        mColorBall.setBackground( resources.getDrawable( drawableId ) );
        mBallNumber.setTextColor( resources.getColor( colorId ) );
        mBallRepeat.setBackground( resources.getDrawable( borderId ) );
        mBallRepeat.setTextColor( resources.getColor( colorId ) );
    }

    public Ball getBall(){
        return mBall;
    }

}
