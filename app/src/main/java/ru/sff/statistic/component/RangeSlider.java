package ru.sff.statistic.component;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.innovattic.rangeseekbar.RangeSeekBar;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;

public class RangeSlider extends BaseComponent {

    public static final int RANGE_SLIDER_SUM = 0;
    public static final int RANGE_SLIDER_BALL = 1;

    private ThreeCellStat mSliderRequest;
    private RangeSeekBar mRangeSlider;
    private TextView mRangeTitle;

    private int mSliderMinValue;
    private int mSliderMaxValue;
    private int mSliderType;
    private int mDefMinValue;
    private boolean mIsEnabled;

    public RangeSlider( Context context ) {
        super( context );
        inflate( context, R.layout.range_slider, this );
    }

    public RangeSlider( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.range_slider, this );
    }

    public RangeSlider initSlider( int minValue, int maxValue, int minDefValue,int minRange, String title, int sliderType ) {
        initBaseComponent( this );
        mIsEnabled = true;
        mRangeTitle = initTextView( R.id.rangeSliderTitleId, AppConstants.ROTONDA_BOLD );
        mRangeTitle.setText( title );
        mSliderRequest = findViewById( R.id.rangeSliderLabelId );
        mRangeSlider = findViewById( R.id.rangeSliderId );

        mDefMinValue = minDefValue;

        mSliderMinValue = minValue;
        mSliderMaxValue = maxValue;
        mSliderType = sliderType;

        mRangeSlider.setMinThumbValue( minValue );
        mRangeSlider.setMaxThumbValue( minValue );
        mRangeSlider.setMax( maxValue );
        mRangeSlider.setMinRange( minRange );

        setSliderLabel();
        mRangeSlider.setSeekBarChangeListener( new RangeSeekBar.SeekBarChangeListener() {
            @Override
            public void onStartedSeeking() {}

            @Override
            public void onStoppedSeeking() {}

            @Override
            public void onValueChanged( int min, int max ) {
                if( !mIsEnabled ){
                    mRangeSlider.setMinThumbValue( mSliderMinValue );
                    mRangeSlider.setMaxThumbValue( mSliderMaxValue );
                    return;
                }
                if ( min < mDefMinValue ){
                    min = mDefMinValue;
                    mRangeSlider.setMinThumbValue( min );
                }
                if ( max < mDefMinValue ){
                    max = mDefMinValue;
                    mRangeSlider.setMaxThumbValue( max );
                }
                if( min > max ){
                    min = max;
                    mRangeSlider.setMinThumbValue( min );
                }
                if( max < min ){
                    max = min;
                    mRangeSlider.setMaxThumbValue( max );
                }

                mSliderMinValue = min;
                mSliderMaxValue = max;
                setSliderLabel();
            }
        } );
        return this;
    }

    private void setSliderLabel() {
        switch ( mSliderType ) {
            case RANGE_SLIDER_SUM:
                setSumLabel();
                break;
            case RANGE_SLIDER_BALL:
                setBallLabel();
                break;
            default:
                break;
        }
    }

    private void setSumLabel() {
        mSliderRequest.setLeftCell( "", "от " );
        mSliderRequest.setMiddleCell( mSliderMinValue + "", " по " );
        mSliderRequest.setRightCell( mSliderMaxValue + "", "");
    }

    private void setBallLabel() {
        mSliderRequest.setLeftCell( "", "c " );
        mSliderRequest.setMiddleCell( mSliderMinValue + "", " до " );
        mSliderRequest.setRightCell( mSliderMaxValue + "", "");
    }


    public void setEnableSlider( boolean enabled ) {
        int visible = enabled ? View.VISIBLE : View.GONE;
        findViewById( R.id.rangeSliderContainerId ).setVisibility( visible );
        Resources resources = SFFSApplication.getAppContext().getResources();
        if ( enabled ) {
            mRangeTitle.setBackground( resources.getDrawable( R.drawable.border_left_yellow ) );
            mRangeTitle.setTextColor( resources.getColor( R.color.splashTextColor ) );
        } else {
            mRangeTitle.setBackground( null );
            mRangeTitle.setTextColor( resources.getColor( R.color.transpGrayTextColor ) );
        }
        mRangeSlider.setEnabled( enabled );
        mIsEnabled = enabled;
    }

    public int getSliderMinValue() {
        return mSliderMinValue;
    }

    public int getSliderMaxValue() {
        return mSliderMaxValue;
    }

    @Override
    public void onClick( View view ) {

    }
}
