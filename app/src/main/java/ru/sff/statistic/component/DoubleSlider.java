package ru.sff.statistic.component;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;

public class DoubleSlider extends BaseComponent {

    private ThreeCellStat mSliderRequest;
    private DiscreteSeekBar mSliderOne;
    private DiscreteSeekBar mSliderTwo;
    private int mSliderOneValue;
    private int mSliderTwoValue;

    private TextView mSliderTitle;


    public DoubleSlider( Context context ) {
        super( context );
        inflate( context, R.layout.double_slider, this );
    }

    public DoubleSlider( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.double_slider, this );
    }

    public DoubleSlider initDoubleSlider( int minOneValue, int maxOneValue, int oneValue,
                                          int minTwoValue, int maxTwoValue, int twoValue,
                                          String title) {
        initBaseComponent( this );
        mSliderTitle = initTextView( R.id.doubleSliderTitleId, AppConstants.ROTONDA_BOLD );
        mSliderTitle.setText( title );

        mSliderOneValue = oneValue;
        mSliderTwoValue = twoValue;

        mSliderRequest = findViewById( R.id.doubleSliderLabelId );
        mSliderOne = findViewById( R.id.doubleSliderOneId );
        mSliderOne.setThumbColor( SFFSApplication.getAppContext().getResources().getColor( R.color.shokoColor ),
                SFFSApplication.getAppContext().getResources().getColor( R.color.shokoColor ) );

        mSliderOne.setMin( minOneValue );
        mSliderOne.setMax( maxOneValue );
        mSliderOne.setProgress( mSliderOneValue );
        setSliderLabel();
        mSliderOne.setOnTouchListener( ( View view, MotionEvent motionEvent ) -> {
            if ( MotionEvent.ACTION_MOVE == motionEvent.getAction()
                    || MotionEvent.ACTION_UP == motionEvent.getAction() ) {
                mSliderOneValue = mSliderOne.getProgress();
                setSliderLabel();
            }
            return false;
        } );

        mSliderTwo = findViewById( R.id.doubleSliderTwoId );
        mSliderTwo.setThumbColor( SFFSApplication.getAppContext().getResources().getColor( R.color.shokoColor ),
                SFFSApplication.getAppContext().getResources().getColor( R.color.shokoColor ) );

        mSliderTwo.setMin( minTwoValue );
        mSliderTwo.setMax( maxTwoValue );
        mSliderTwo.setProgress( mSliderTwoValue );
        setSliderLabel();
        mSliderTwo.setOnTouchListener( ( View view, MotionEvent motionEvent ) -> {
            if ( MotionEvent.ACTION_MOVE == motionEvent.getAction()
                    || MotionEvent.ACTION_UP == motionEvent.getAction() ) {
                mSliderTwoValue = mSliderTwo.getProgress();
                setSliderLabel();
            }
            return false;
        } );
        return this;
    }

    private void setSliderLabel(){
        mSliderRequest.setLeftCell( mSliderOneValue+"","" );
        mSliderRequest.setMiddleCell( AppConstants.ALL_MONTH_SFX[ mSliderTwoValue-1 ],"" );
    }

    public void setEnableSlider( boolean enabled ) {
        int visible = enabled ? View.VISIBLE : View.GONE;
        findViewById( R.id.doubleSliderContainerId ).setVisibility( visible );
        Resources resources = SFFSApplication.getAppContext().getResources();
        if ( enabled ) {
            mSliderTitle.setBackground( resources.getDrawable( R.drawable.border_left_yellow ) );
//            int yellowColor = resources.getColor( R.color.ballYellow );
//            int shokoColor = resources.getColor( R.color.shokoColor );
//            setSliderColor( mSliderOne, yellowColor, shokoColor );
//            setSliderColor( mSliderTwo, yellowColor, shokoColor );
//            mSliderRequest.enableComponent();
            mSliderTitle.setTextColor( resources.getColor( R.color.splashTextColor ) );
        } else {
            mSliderTitle.setBackground( null );
//            mSliderRequest.disableComponent();
//            int grayColor = resources.getColor( R.color.transpGrayTextColor );
//            setSliderColor( mSliderOne, grayColor, grayColor );
//            setSliderColor( mSliderTwo, grayColor, grayColor );
            mSliderTitle.setTextColor( resources.getColor( R.color.transpGrayTextColor ) );
        }
        mSliderOne.setEnabled( enabled );
        mSliderTwo.setEnabled( enabled );
    }

    private void setSliderColor(DiscreteSeekBar slider, int trackColor, int thumbColor ){
        slider.setScrubberColor( trackColor );
        slider.setTrackColor( trackColor );
        slider.setThumbColor( thumbColor, thumbColor );
    }

    public int getSliderOneValue() {
        return mSliderOneValue;
    }

    public int getSliderTwoValue() {
        return mSliderTwoValue;
    }

    @Override
    public void onClick( View view ) {

    }
}
