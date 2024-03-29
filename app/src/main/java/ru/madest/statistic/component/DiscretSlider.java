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

public class DiscretSlider extends BaseComponent {

    public static final int DISCRET_SLIDER_DRAW = 0;
    public static final int DISCRET_SLIDER_DAY = 1;
    public static final int DISCRET_SLIDER_MONTH = 2;
    public static final int DISCRET_SLIDER_DAY_WEEK = 3;

    private ThreeCellStat mSliderRequest;
    private DiscreteSeekBar mDiscreteSlider;
    private TextView mSliderTitle;
    private int mSliderValue;
    private int mSliderType;


    public DiscretSlider( Context context ) {
        super( context );
        inflate( context, R.layout.discret_slider, this );
    }

    public DiscretSlider( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.discret_slider, this );
    }

    public DiscretSlider initSlider( int minValue, int maxValue, int value, String title, int sliderType ) {
        initBaseComponent( this );
        mSliderRequest = findViewById( R.id.discretSliderLabelId );
        mDiscreteSlider = findViewById( R.id.discretSliderId );
        mDiscreteSlider.setThumbColor( SFFSApplication.getAppContext().getResources().getColor( R.color.shokoColor ),
                SFFSApplication.getAppContext().getResources().getColor( R.color.shokoColor ) );

        mSliderTitle = initTextView( R.id.discretSliderTitleId, AppConstants.ROTONDA_BOLD );
        mSliderTitle.setText( title );

        mSliderValue = value;
        mSliderType = sliderType;

        mDiscreteSlider.setMin( minValue );
        mDiscreteSlider.setMax( maxValue );
        mDiscreteSlider.setProgress( mSliderValue );
        setSliderLabel();
        mDiscreteSlider.setOnProgressChangeListener( new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged ( DiscreteSeekBar seekBar, int value, boolean fromUser ) {
                mSliderValue = mDiscreteSlider.getProgress();
                setSliderLabel();
            }

            @Override
            public void onStartTrackingTouch ( DiscreteSeekBar seekBar ) {}

            @Override
            public void onStopTrackingTouch ( DiscreteSeekBar seekBar ) {
                mSliderValue = mDiscreteSlider.getProgress();
                setSliderLabel();
            }
        } );
        return this;
    }

    private void setSliderLabel() {
        switch ( mSliderType ) {
            case DISCRET_SLIDER_DRAW:
                setDrawCountLabel();
                break;
            case DISCRET_SLIDER_DAY:
                setDayLabel();
                break;
            case DISCRET_SLIDER_MONTH:
                setMonthLabel();
                break;
            case DISCRET_SLIDER_DAY_WEEK:
                setDayWeekLabel();
                break;
            default:
                break;
        }
    }

    private void setDrawCountLabel() {
        String drawLabel = " тиражей";
        String lastLabel = "Последних ";
        int mod = mSliderValue % 10;
        if ( mSliderValue == 1 || mSliderValue == 21 || mSliderValue == 31 || mSliderValue == 41 ) {
            lastLabel = "Последний ";
            drawLabel = " тираж";
        } else if ( ( mSliderValue < 11 || mSliderValue > 15 ) &&
                ( mod > 1 && mod < 5 ) ) {
            drawLabel = " тиража";
        }
        mSliderRequest.setLeftCell( "", lastLabel );
        mSliderRequest.setMiddleCell( mSliderValue + "", drawLabel );
    }

    private void setDayLabel() {
        mSliderRequest.setLeftCell( mSliderValue + "", "день" );
    }

    private void setMonthLabel() {
        mSliderRequest.setLeftCell( AppConstants.ALL_OF_MONTH.get( mSliderValue ) + "", "" );
    }

    private void setDayWeekLabel() {
        mSliderRequest.setLeftCell( AppConstants.ALL_DAY_OF_WEEK.get( mSliderValue ) + "", "" );
    }

    public void setEnableSlider( boolean enabled ) {
        int visible = enabled ? View.VISIBLE : View.GONE;
        findViewById( R.id.discretSliderContainerId ).setVisibility( visible );
        Resources resources = SFFSApplication.getAppContext().getResources();
        if ( enabled ) {
            mSliderTitle.setBackground( resources.getDrawable( R.drawable.border_left_yellow ) );
//            int yellowColor = resources.getColor( R.color.ballYellow );
//            int shokoColor = resources.getColor( R.color.shokoColor );
//            mDiscreteSlider.setScrubberColor( yellowColor );
//            mDiscreteSlider.setTrackColor( yellowColor );
//            mDiscreteSlider.setThumbColor( shokoColor, shokoColor );
//            mSliderRequest.enableComponent();
            mSliderTitle.setTextColor( resources.getColor( R.color.splashTextColor ) );
        } else {
            mSliderTitle.setBackground( null );
//            mSliderRequest.disableComponent();
//            int grayColor = resources.getColor( R.color.transpGrayTextColor );
//            mDiscreteSlider.setScrubberColor( grayColor );
//            mDiscreteSlider.setTrackColor( grayColor );
//            mDiscreteSlider.setThumbColor( grayColor, grayColor );
            mSliderTitle.setTextColor( resources.getColor( R.color.transpGrayTextColor ) );
        }
        mDiscreteSlider.setEnabled( enabled );
    }

    public int getSliderValue() {
        return mSliderValue;
    }

    @Override
    public void onClick( View view ) {

    }
}
