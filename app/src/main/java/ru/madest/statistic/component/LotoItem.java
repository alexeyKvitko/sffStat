package ru.madest.statistic.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import ru.madest.statistic.AppConstants;
import ru.madest.statistic.R;
import ru.madest.statistic.model.LotoModel;

public class LotoItem extends BaseComponent {

    private CardView lotoDrawCard;
    private SixBallWin lotoBallWin;
    private TextView lotoDrawNum;
    private TextView lotoDrawDateTime;
    private TextView lotoDrawPrize;
    private TextView lotoDrawSum;

    public LotoItem( Context context ) {
        super( context );
        inflate( context, R.layout.rv_item_loto, this );
        initialize();
    }

    public LotoItem( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.rv_item_loto, this );
        initialize();
    }

    private void initialize(){
        initBaseComponent( this );
        lotoDrawCard = findViewById( R.id.lotoDrawCardId );
        lotoBallWin = findViewById( R.id.lotoDrawDigitsId );
        findViewById( R.id.lotoDrawMonthId ).setVisibility( View.GONE );
        lotoDrawNum = initTextView( R.id.lotoDrawNumId, AppConstants.ROTONDA_BOLD );
        lotoDrawDateTime = initTextView(  R.id.lotoDrawDateTimeId, AppConstants.ROBOTO_CONDENCED );
        lotoDrawSum = initTextView( R.id.lotoDrawSumId, AppConstants.ROBOTO_CONDENCED );
        lotoDrawPrize = initTextView( R.id.lotoDrawPrizeValueId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.lotoDrawPrizeLabelId, AppConstants.ROBOTO_CONDENCED );

    }
    
    public void setLotoItem( LotoModel lotoModelDraw){
        lotoBallWin.setSixBallWins( lotoModelDraw.getSlotOne(), lotoModelDraw.getSlotTwo()
                , lotoModelDraw.getSlotThree(), lotoModelDraw.getSlotFour()
                , lotoModelDraw.getSlotFive(), lotoModelDraw.getSlotSix());
        lotoDrawNum.setText( lotoModelDraw.getDraw().toString() );
        lotoDrawDateTime.setText( lotoModelDraw.getDrawDate() );
        lotoDrawPrize.setText( lotoModelDraw.getSuperPrize() );
        lotoDrawSum.setText( lotoModelDraw.getSum().toString() );
        lotoDrawCard.setVisibility( View.VISIBLE );
    }

    @Override
    public void onClick ( View view ) {

    }


}
