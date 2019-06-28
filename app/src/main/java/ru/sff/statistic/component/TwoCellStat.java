package ru.sff.statistic.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;

public class TwoCellStat extends BaseComponent {

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

    private void init(){
        initBaseComponent( this );
    }

    public void setTwoCellValue( String leftCellValue, String leftCellComment
                                            , String rightCellValue, String rightCellComment ){
        initTextView( R.id.leftCellValueId, AppConstants.ROTONDA_BOLD ).setText( leftCellValue );
        initTextView( R.id.leftCellCommentId, AppConstants.ROBOTO_CONDENCED ).setText( leftCellComment );

        initTextView( R.id.rightCellValueId, AppConstants.ROTONDA_BOLD ).setText( rightCellValue );
        initTextView( R.id.rightCellCommentId, AppConstants.ROBOTO_CONDENCED ).setText( rightCellComment );
    }

    @Override
    public void onClick( View view ) {

    }
}
