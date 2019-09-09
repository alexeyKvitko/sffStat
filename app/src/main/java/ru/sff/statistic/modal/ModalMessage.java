package ru.sff.statistic.modal;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;
import ru.sff.statistic.activity.BaseActivity;
import ru.sff.statistic.activity.SplashActivity;
import ru.sff.statistic.utils.AppUtils;

public class ModalMessage extends RelativeLayout {

    private static final int MESSAGE_HEIGHT = ( int ) AppUtils.convertDpToPixel( 150 );

    private OnModalMessageHideListener mListener;

    private RelativeLayout mModalMessage;
    private ConstraintLayout mContainer;
    private TextView mMessageTitle;
    private Activity mActivity;
    private int mTimeOut;

    public static ModalMessage show( Activity activity, String title, String[] body){
        return new ModalMessage( activity, title, body, 2000 );
    }

    public static ModalMessage show( Activity activity, String title, String[] body, int timeOut){
        return new ModalMessage( activity, title, body, timeOut );
    }


    public ModalMessage( Activity activity, String title, String[] text, int timeOut ) {
        super( activity, null );
        inflate( activity, R.layout.modal_message, this );
        mActivity = activity;
        if ( activity instanceof SplashActivity ){
            mContainer = (( SplashActivity ) activity).getActivityContainer();
        } else {
            mContainer = (( BaseActivity ) activity).getActivityContainer();
        }

        mContainer.addView( this );
        mTimeOut = timeOut;
        initialize( title, text );
    }

    private void initialize(String title, String[] texts){
        LinearLayout cardView = findViewById( R.id.messageContainerId );
        mModalMessage = this;
        mMessageTitle = findViewById( R.id.toastTitleId );
        mMessageTitle.setTypeface( AppConstants.ROTONDA_BOLD );
        mMessageTitle.setText( title );
        for( String text : texts ){
            TextView textView = new TextView( mActivity );
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            params.topMargin = (int) AppUtils.convertDpToPixel( 8 );
            params.leftMargin = (int) AppUtils.convertDpToPixel( 6 );
            params.rightMargin = (int) AppUtils.convertDpToPixel( 6 );
            textView.setLayoutParams(params);
            textView.setGravity( Gravity.CENTER_HORIZONTAL );
            textView.setTypeface( AppConstants.ROBOTO_CONDENCED );
            textView.setTextSize( TypedValue.COMPLEX_UNIT_DIP, 14 );
            textView.setTextColor( mActivity.getResources().getColor( R.color.grayTextColor ) );
            textView.setText( text );
            cardView.addView( textView );
        }
        Animation fadeIn = AnimationUtils.loadAnimation( SFFSApplication.getAppContext(),R.anim.fade_in );
        fadeIn.setDuration( 300 );
        mModalMessage.startAnimation( fadeIn );
        mModalMessage.setOnClickListener( null );
        animateMessageContainer( 0, MESSAGE_HEIGHT );
        mModalMessage.setVisibility( View.VISIBLE );
        ( new Handler() ).postDelayed( () -> {
            Animation fadeOut = AnimationUtils.loadAnimation( SFFSApplication.getAppContext(),R.anim.fade_out );
            fadeOut.setDuration( 300 );
            fadeOut.setAnimationListener( new Animation.AnimationListener() {
                @Override
                public void onAnimationStart( Animation animation ) {}
                @Override
                public void onAnimationEnd( Animation animation ) {
                    if ( mModalMessage != null ){
                        if ( mContainer != null ){
                            mContainer.removeView( mModalMessage );
                        }
                        mModalMessage.setVisibility( View.GONE );
                        mModalMessage = null;
                        if( mListener != null ){
                            mListener.onMessageHide();
                        }
                    }
                }
                @Override
                public void onAnimationRepeat( Animation animation ) {}
            } );
            mModalMessage.startAnimation( fadeOut );
            animateMessageContainer( MESSAGE_HEIGHT, 0 );
        }, mTimeOut );
    }

    private void animateMessageContainer( final int start, final int end ) {
        final CardView messageContainer = findViewById( R.id.modalMessageCardId );
        final RelativeLayout.LayoutParams layoutParams =
                ( RelativeLayout.LayoutParams ) messageContainer.getLayoutParams();
        ValueAnimator valAnimator = ValueAnimator.ofObject( new IntEvaluator(), start, end );
        valAnimator.addUpdateListener( ( ValueAnimator animator ) -> {
            int val = ( Integer ) animator.getAnimatedValue();
            layoutParams.height = val;
            messageContainer.setLayoutParams( layoutParams );
        } );
        valAnimator.setDuration( 300 );
        valAnimator.start();
    }

    public void setOnMessageHideListener(OnModalMessageHideListener listener){
        mListener = listener;
    }

    public interface OnModalMessageHideListener{
        void onMessageHide();
    }
}
