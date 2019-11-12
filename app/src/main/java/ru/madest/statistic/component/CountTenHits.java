package ru.madest.statistic.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import ru.madest.statistic.AppConstants;
import ru.madest.statistic.R;
import ru.madest.statistic.model.DigitDraw;
import ru.madest.statistic.utils.AppUtils;

public class CountTenHits extends BaseComponent {
    @Override
    public void onClick ( View view ) {

    }

    public CountTenHits( Context context ) {
        super( context );
        inflate( context, R.layout.count_ten_hits, this );
        initialize();
    }

    public CountTenHits( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.count_ten_hits, this );
        initialize();
    }

    private void initialize(){
        initBaseComponent( this );
    }

    public void setCountTenHits( int hit, DigitDraw digitDraw ){
        initTextView( R.id.countTenHitstValueId, AppConstants.ROBOTO_BLACK ).setText( ""+hit );
        initTextView( R.id.countTenHitstLabelId, AppConstants.ROBOTO_CONDENCED );
        initTextView( R.id.countTenHitstCaseId, AppConstants.ROTONDA_BOLD ).setText( AppUtils.getCases( digitDraw.getCount() ) );
        initTextView( R.id.hitsStartDrawId, AppConstants.ROTONDA_BOLD ).setText( digitDraw.getStartDraw()+"" );
        initTextView( R.id.hitsEndDrawId, AppConstants.ROTONDA_BOLD ).setText( " - "+digitDraw.getEndDraw() );
        initTextView( R.id.hitsStartDateId, AppConstants.ROBOTO_CONDENCED ).setText( digitDraw.getStartDrawDate() );
        initTextView( R.id.hitsEndDateId, AppConstants.ROBOTO_CONDENCED ).setText( " - "+digitDraw.getEndDrawDate() );

    }
}
