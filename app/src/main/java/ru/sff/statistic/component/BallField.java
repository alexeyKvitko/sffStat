package ru.sff.statistic.component;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
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
import ru.sff.statistic.utils.CustomAnimation;

public class BallField extends LinearLayout implements View.OnClickListener {

    private Ball mBall;

    private TextView mBallNumber;
    private TextView mBallRepeat;
    private LinearLayout mBallLayout;
    private int mOrientation;
    private boolean mCircleVisible;

    public BallField(Context context) {
        super(context);
        inflate(context, R.layout.ball_field, this);
        init( AppConstants.VERTICAL_ORIENTATION );
    }

    public BallField(Context context, int orientation ) {
        super(context);
        int layout = R.layout.ball_field;
        if ( AppConstants.HORIZONTAL_ORIENTATION == orientation ){
            layout = R.layout.ball_field_hor;
        }
        inflate(context, layout, this);
        init( orientation );
    }

    public BallField(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.ball_field, this);
        init( AppConstants.VERTICAL_ORIENTATION );
    }

    private void init( int orientation ) {
        mOrientation = orientation;
        mCircleVisible = false;
        mBallNumber = this.findViewById(R.id.ballNumberId);
        mBallRepeat = this.findViewById(R.id.ballRepeatId);
        mBallLayout = this.findViewById(R.id.ballFieldLayoutId);
        mBallNumber.setTypeface(AppConstants.ROTONDA_BOLD);
        mBallRepeat.setTypeface(AppConstants.ROBOTO_CONDENCED);
        this.setOnClickListener( this );
    }

    public void setBall(Ball ball) {
        if ( ball == null ){
            mBallNumber.setVisibility( View.GONE );
            mBallRepeat.setVisibility( View.GONE );
            mBallLayout.setBackgroundColor( SFFSApplication.getAppContext().getResources()
                                                    .getColor( R.color.transparentBackground ) );
            this.setOnClickListener( null );
            return;
        }
        mBall = ball;
        mBallNumber.setText(mBall.getBallNumber()+"");
        String repeatCount = mBall.getBallRepeat()+"";
        if( AppConstants.VIEW_TYPE_PERCENT.equals(  GlobalManager.getResultViewType() ) ){
            repeatCount =  (mBall.getBallRepeat()*100/GlobalManager.getPlayedDraws() )+"%";
        }
        mBallRepeat.setText( repeatCount );
        for( BallSetType ballSetType : GlobalManager.getInstance().getBallSetTypes() ){
            if ( BallSetType.BIGGER.equals( ballSetType )
                    && ballSetType.equals( mBall.getBallType() ) ) {
                setBiggerBall();
            }
            if ( BallSetType.LESS.equals( ballSetType )
                    && ballSetType.equals( mBall.getBallType() ) ) {
                setLessBall();
            }
            if ( BallSetType.MIDDLE.equals( ballSetType )
                    && ballSetType.equals( mBall.getBallType() ) ) {
                setMiddleBall();
            }
            if ( BallSetType.CUSTOM.equals( ballSetType )
                    && ballSetType.equals( mBall.getBallType() ) ) {
                setCustomBall();
            }
            if ( BallSetType.COMBY.equals( ballSetType )
                    && ballSetType.equals( mBall.getBallType() ) ) {
                setCombyBall();
            }
        }
        if( AppConstants.HORIZONTAL_ORIENTATION == mOrientation && mBall.getBallRepeat() == 0){
            mBallRepeat.setVisibility( View.GONE );
        }
    }

    public Ball getBall() {
        return mBall;
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

    private void setCustomBall(){
        Resources resources = SFFSApplication.getAppContext().getResources();
        mBallLayout.setBackground( resources.getDrawable(R.drawable.circle_custom));
        mBallNumber.setTextColor( resources.getColor( R.color.white ) );
        mBallRepeat.setTextColor( resources.getColor( R.color.transparentYellowColor) );
    }

    private void setCombyBall(){
        Resources resources = SFFSApplication.getAppContext().getResources();
        int drawableId = AppConstants.FAKE_ID;
//        PAIRS
        if( mBall.getComby().contains( BallSetType.CUSTOM ) &&
                mBall.getComby().contains( BallSetType.BIGGER )){
            drawableId = R.drawable.cm_br_circle;
        }
        if( mBall.getComby().contains( BallSetType.CUSTOM ) &&
                mBall.getComby().contains( BallSetType.LESS )){
            drawableId = R.drawable.cm_ls_circle;
        }
        if( mBall.getComby().contains( BallSetType.CUSTOM ) &&
                mBall.getComby().contains( BallSetType.MIDDLE)){
            drawableId = R.drawable.cm_ml_circle;
        }
        if( mBall.getComby().contains( BallSetType.BIGGER ) &&
                mBall.getComby().contains( BallSetType.LESS )){
            drawableId = R.drawable.br_ls_circle;
        }
        if( mBall.getComby().contains( BallSetType.BIGGER ) &&
                mBall.getComby().contains( BallSetType.MIDDLE )){
            drawableId = R.drawable.br_ml_circle;
        }
        if( mBall.getComby().contains( BallSetType.LESS ) &&
                mBall.getComby().contains( BallSetType.MIDDLE )){
            drawableId = R.drawable.ls_ml_circle;
        }
//        THREES
        if( mBall.getComby().contains( BallSetType.CUSTOM ) &&
                mBall.getComby().contains( BallSetType.BIGGER ) &&
                    mBall.getComby().contains( BallSetType.LESS )){
            drawableId = R.drawable.cm_br_ls_circle;
        }
        if( mBall.getComby().contains( BallSetType.CUSTOM ) &&
                mBall.getComby().contains( BallSetType.BIGGER ) &&
                    mBall.getComby().contains( BallSetType.MIDDLE )){
            drawableId = R.drawable.cm_bg_ml_circle;
        }
        if( mBall.getComby().contains( BallSetType.CUSTOM ) &&
                mBall.getComby().contains( BallSetType.LESS ) &&
                    mBall.getComby().contains( BallSetType.MIDDLE )){
            drawableId = R.drawable.cm_ls_ml_circle;
        }
        if( mBall.getComby().contains( BallSetType.BIGGER ) &&
                mBall.getComby().contains( BallSetType.LESS ) &&
                  mBall.getComby().contains( BallSetType.MIDDLE )){
            drawableId = R.drawable.br_ls_ml_circle;
        }
        if( mBall.getComby().contains( BallSetType.CUSTOM ) &&
                mBall.getComby().contains( BallSetType.BIGGER ) &&
                    mBall.getComby().contains( BallSetType.LESS ) &&
                        mBall.getComby().contains( BallSetType.MIDDLE )){
            drawableId = R.drawable.cm_br_ls_ml_circle;
        }
        mBallLayout.setBackground( resources.getDrawable( drawableId ));
        mBallNumber.setTextColor( resources.getColor( R.color.white ) );
        mBallRepeat.setTextColor( resources.getColor( R.color.white) );
    }


    @Override
    public void onClick( View view ) {
        CustomAnimation.bounceAnimation( view );
        if( AppConstants.HORIZONTAL_ORIENTATION == mOrientation ){
            mCircleVisible = !mCircleVisible;
            findViewById( R.id.ballCircleId ).setVisibility( mCircleVisible ? View.VISIBLE : View.GONE);
        } else {
            ( new Handler() ).postDelayed( () -> {
                Intent intent = new Intent( AppConstants.ROUTE_ACTION_MESSAGE );
                intent.putExtra( AppConstants.ROUTE_ACTION_TYPE, AppConstants.BALL_SELECT_ACTION );
                intent.putExtra( AppConstants.BALL_SELECT_ACTION, mBall );
                SFFSApplication.getAppContext().sendBroadcast( intent );
            }, 300 );
        }
    }

    public boolean isCircleVisible() {
        return mCircleVisible;
    }
}
