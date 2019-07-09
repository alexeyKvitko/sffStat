package ru.sff.statistic.component;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.utils.AppUtils;

public class DrawResultTable extends BaseComponent {
    public DrawResultTable( Context context ) {
        super( context );
        inflate(context, R.layout.draw_result_table, this);
        initialize();
    }

    public DrawResultTable( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate(context, R.layout.draw_result_table, this);
        initialize();
    }

    private void initialize(){
        initBaseComponent( this );
        initTextView( R.id.resultTableHeaderLeftId, AppConstants.ROBOTO_CONDENCED );
        initTextView( R.id.resultTableHeaderMiddleId, AppConstants.ROBOTO_CONDENCED );
        initTextView( R.id.resultTableHeaderRightId, AppConstants.ROBOTO_CONDENCED );
    }

    public void setSixBallResult(Long winner, Long amount, Long totalAmount ){
        initTextView( R.id.resultTableRowSixLeftId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.resultTableRowSixMiddleId, AppConstants.ROBOTO_CONDENCED, Typeface.BOLD, AppConstants.FAKE_ID )
                    .setText( AppUtils.getFormatedString( winner ) );
        String payAmount = winner > 0 ? " x " + AppUtils.getFormatedString( amount ) + " = " + AppUtils.getFormatedString( totalAmount ) : "0";
            initTextView( R.id.resultTableRowSixRightId, AppConstants.ROBOTO_CONDENCED, Typeface.BOLD, AppConstants.FAKE_ID)
                    .setText( payAmount );
    }

    public void setFiveBallResult(Long winner, Long amount, Long totalAmount ){
        initTextView( R.id.resultTableRowFiveLeftId, AppConstants.ROTONDA_BOLD );
            initTextView( R.id.resultTableRowFiveMiddleId, AppConstants.ROBOTO_CONDENCED, Typeface.BOLD, AppConstants.FAKE_ID )
                    .setText( AppUtils.getFormatedString( winner ) );
        String payAmount = winner > 0 ? " x " +  AppUtils.getFormatedString( amount ) + " = " + AppUtils.getFormatedString( totalAmount ) : "0";
            initTextView( R.id.resultTableRowFiveRightId, AppConstants.ROBOTO_CONDENCED, Typeface.BOLD, AppConstants.FAKE_ID)
                    .setText( payAmount );

    }

    public void setFourBallResult(Long winner, Long amount, Long totalAmount ){
        initTextView( R.id.resultTableRowFourLeftId, AppConstants.ROTONDA_BOLD );
            initTextView( R.id.resultTableRowFourMiddleId, AppConstants.ROBOTO_CONDENCED, Typeface.BOLD, AppConstants.FAKE_ID )
                    .setText( AppUtils.getFormatedString( winner ) );
        String payAmount = winner > 0 ? " x " + AppUtils.getFormatedString( amount ) + " = " + AppUtils.getFormatedString( totalAmount ) : "0";
            initTextView( R.id.resultTableRowFourRightId, AppConstants.ROBOTO_CONDENCED, Typeface.BOLD, AppConstants.FAKE_ID)
                    .setText( payAmount );
    }

    public void setThreeBallResult(Long winner, Long amount, Long totalAmount ){
        initTextView( R.id.resultTableRowThreeLeftId, AppConstants.ROTONDA_BOLD );
            initTextView( R.id.resultTableRowThreeMiddleId, AppConstants.ROBOTO_CONDENCED, Typeface.BOLD, AppConstants.FAKE_ID )
                    .setText( AppUtils.getFormatedString( winner ) );
        String payAmount = winner > 0 ? " x " + AppUtils.getFormatedString( amount ) + " = " + AppUtils.getFormatedString( totalAmount ) : "0";
            initTextView( R.id.resultTableRowThreeRightId, AppConstants.ROBOTO_CONDENCED, Typeface.BOLD, AppConstants.FAKE_ID)
                    .setText( payAmount );
    }

    public void setTwoBallResult(Long winner, Long amount, Long totalAmount ){
        initTextView( R.id.resultTableRowTwoLeftId, AppConstants.ROTONDA_BOLD );
            initTextView( R.id.resultTableRowTwoMiddleId, AppConstants.ROBOTO_CONDENCED, Typeface.BOLD, AppConstants.FAKE_ID )
                    .setText( AppUtils.getFormatedString( winner ) );
        String payAmount = winner > 0 ? " x " + AppUtils.getFormatedString( amount ) + " = " + AppUtils.getFormatedString( totalAmount ) : "0";
            initTextView( R.id.resultTableRowTwoRightId, AppConstants.ROBOTO_CONDENCED, Typeface.BOLD, AppConstants.FAKE_ID)
                    .setText( payAmount );
    }

    @Override
    public void onClick( View view ) {

    }
}
