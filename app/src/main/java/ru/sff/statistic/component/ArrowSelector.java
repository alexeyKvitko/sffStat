package ru.sff.statistic.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.utils.CustomAnimation;

public class ArrowSelector extends BaseComponent {

    private String[] mValues;
    private int mCurrentIndex;
    private TextView mSelectorValue;

    public ArrowSelector( Context context ) {
        super( context );
        inflate( context, R.layout.arrow_selector, this );
        init();
    }

    public ArrowSelector( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.arrow_selector, this );
        init();
    }

    private void init(){
        initBaseComponent( this );
        mSelectorValue = initTextView( R.id.arrowSelectorTextId, AppConstants.ROBOTO_BLACK );
        setThisOnClickListener( R.id.arrowLeftId,  R.id.arrowRightId);
    }

    public void setValues(String values[], int defIndex ){
        mValues = values;
        mCurrentIndex = defIndex;
        mSelectorValue.setText( mValues[ mCurrentIndex ] );
    }

    public String getValue(){
        return mValues[ mCurrentIndex ];
    }

    @Override
    public void onClick( View view ) {
        CustomAnimation.clickAnimation( view );
        switch ( view.getId() ){
            case R.id.arrowLeftId :
                mCurrentIndex--;
                if ( mCurrentIndex == -1 ){
                    mCurrentIndex = mValues.length - 1;
                }
                break;
            case R.id.arrowRightId :
                mCurrentIndex++;
                if( mCurrentIndex == mValues.length ){
                    mCurrentIndex = 0;
                }
                break;
        }
        mSelectorValue.setText( mValues[ mCurrentIndex ] );
    }

    public int getCurrentIndex() {
        return mCurrentIndex;
    }
}
