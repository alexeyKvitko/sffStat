package ru.madest.statistic.component;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import ru.madest.statistic.AppConstants;
import ru.madest.statistic.R;
import ru.madest.statistic.SFFSApplication;

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
        mSliderOne.setOnProgressChangeListener( new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged ( DiscreteSeekBar seekBar, int value, boolean fromUser ) {
                mSliderOneValue = mSliderOne.getProgress();
                setSliderLabel();
            }

            @Override
            public void onStartTrackingTouch ( DiscreteSeekBar seekBar ) {

            }

            @Override
            public void onStopTrackingTouch ( DiscreteSeekBar seekBar ) {
                mSliderOneValue = mSliderOne.getProgress();
                setSliderLabel();
            }
        } );

        mSliderTwo = findViewById( R.id.doubleSliderTwoId );
        mSliderTwo.setThumbColor( SFFSApplication.getAppContext().getResources().getColor( R.color.shokoColor ),
                SFFSApplication.getAppContext().getResources().getColor( R.color.shokoColor ) );

        mSliderTwo.setMin( minTwoValue );
        mSliderTwo.setMax( maxTwoValue );
        mSliderTwo.setProgress( mSliderTwoValue );
        setSliderLabel();
        mSliderTwo.setOnProgressChangeListener( new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged ( DiscreteSeekBar seekBar, int value, boolean fromUser ) {
                mSliderTwoValue = mSliderTwo.getProgress();
                setSliderLabel();
            }

            @Override
            public void onStartTrackingTouch ( DiscreteSeekBar seekBar ) {

            }

            @Override
            public void onStopTrackingTouch ( DiscreteSeekBar seekBar ) {
                mSliderTwoValue = mSliderTwo.getProgress();
                setSliderLabel();
            }
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
