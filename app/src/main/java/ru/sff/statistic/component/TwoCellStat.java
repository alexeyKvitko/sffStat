package ru.sff.statistic.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;
import ru.sff.statistic.utils.CustomAnimation;

public class TwoCellStat extends BaseComponent {

    public OnCellValueSelectListener mListener;

    public TwoCellStat( Context context ) {
        super( context );
        inflate( context, R.layout.two_cell_stat, this );
        init();
    }

    public TwoCellStat( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.two_cell_stat, this );
        init();
    }

    private void init() {
        initBaseComponent( this );
    }

    public void setTwoCellValue( String leftCellValue, String leftCellComment
            , String rightCellValue, String rightCellComment ) {
        initTextView( R.id.leftCellValueId, AppConstants.ROTONDA_BOLD ).setText( leftCellValue );
        initTextView( R.id.leftCellCommentId, AppConstants.ROBOTO_CONDENCED ).setText( leftCellComment );

        initTextView( R.id.rightCellValueId, AppConstants.ROTONDA_BOLD ).setText( rightCellValue );
        initTextView( R.id.rightCellCommentId, AppConstants.ROBOTO_CONDENCED ).setText( rightCellComment );

        setThisOnClickListener( R.id.leftCellContainerId, R.id.rightCellContainerId );
    }

    public void setBorder() {
        initTextView( R.id.leftCellValueId ).setBackground( SFFSApplication.getAppContext().getResources().getDrawable( R.drawable.border_bottom ) );
        initTextView( R.id.rightCellValueId ).setBackground( SFFSApplication.getAppContext().getResources().getDrawable( R.drawable.border_bottom ) );
    }

    public void setOnCellValueSelectListener( OnCellValueSelectListener listener ) {
        mListener = listener;
    }

    public interface OnCellValueSelectListener {
        void onCellClick( Object value );
    }

    @Override
    public void onClick( View view ) {
        if ( mListener == null ) {
            return;
        }
        TextView value = null;
        switch ( view.getId() ) {
            case R.id.leftCellContainerId:
                value = initTextView( R.id.leftCellValueId );
                break;
            case R.id.rightCellContainerId:
                value = initTextView( R.id.rightCellValueId );
                break;
            default:
                break;
        }
        if ( value != null ) {
            CustomAnimation.clickAnimation( value );
            mListener.onCellClick( value.getText().toString() );
        }
    }
}
