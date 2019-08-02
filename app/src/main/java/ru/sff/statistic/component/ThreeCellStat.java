package ru.sff.statistic.component;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;

public class ThreeCellStat extends BaseComponent {

    public ThreeCellStat( Context context ) {
        super( context );
        inflate( context, R.layout.three_cell_stat, this );
        init();
    }

    public ThreeCellStat( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.three_cell_stat, this );
        init();
    }

    private void init(){
        initBaseComponent( this );
    }

    public void setLeftCell( String leftCellValue, String leftCellComment ){
        initTextView( R.id.leftThreeCellValueId, AppConstants.ROTONDA_BOLD ).setText( leftCellValue );
        initTextView( R.id.leftThreeCellCommentId, AppConstants.ROBOTO_CONDENCED ).setText( leftCellComment );
    }

    public void setMiddleCell( String middleCellValue, String middleCellComment ){
        initTextView( R.id.middleThreeCellValueId, AppConstants.ROTONDA_BOLD ).setText( middleCellValue );
        initTextView( R.id.middleThreeCellCommentId, AppConstants.ROBOTO_CONDENCED ).setText( middleCellComment );
    }

    public void setRightCell( String rightCellValue, String rightCellComment ){
        initTextView( R.id.rightThreeCellValueId, AppConstants.ROTONDA_BOLD ).setText( rightCellValue );
        initTextView( R.id.rightThreeCellCommentId, AppConstants.ROBOTO_CONDENCED ).setText( rightCellComment );
    }

    @Override
    public void onClick( View view ) {

    }

    public void disableComponent(){
        Resources resources= SFFSApplication.getAppContext().getResources();
        initTextView( R.id.leftThreeCellValueId ).setTextColor( resources.getColor( R.color.transpGrayTextColor ) );
        initTextView( R.id.leftThreeCellCommentId ).setTextColor( resources.getColor( R.color.transpGrayTextColor ) );
        initTextView( R.id.middleThreeCellValueId ).setTextColor( resources.getColor( R.color.transpGrayTextColor ) );
        initTextView( R.id.middleThreeCellCommentId ).setTextColor( resources.getColor( R.color.transpGrayTextColor ) );
        initTextView( R.id.rightThreeCellValueId ).setTextColor( resources.getColor( R.color.transpGrayTextColor ) );
        initTextView( R.id.rightThreeCellCommentId ).setTextColor( resources.getColor( R.color.transpGrayTextColor ) );
    }

    public void enableComponent(){
        Resources resources= SFFSApplication.getAppContext().getResources();
        initTextView( R.id.leftThreeCellValueId ).setTextColor( resources.getColor( R.color.shokoColor ) );
        initTextView( R.id.leftThreeCellCommentId ).setTextColor( resources.getColor( R.color.splashTextColor ) );
        initTextView( R.id.middleThreeCellValueId ).setTextColor( resources.getColor( R.color.shokoColor ) );
        initTextView( R.id.middleThreeCellCommentId ).setTextColor( resources.getColor( R.color.splashTextColor ) );
        initTextView( R.id.rightThreeCellValueId ).setTextColor( resources.getColor( R.color.shokoColor ) );
        initTextView( R.id.rightThreeCellCommentId ).setTextColor( resources.getColor( R.color.splashTextColor ) );
    }

}
