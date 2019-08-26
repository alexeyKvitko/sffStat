package ru.sff.statistic.recycleview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.component.SixBallSet;
import ru.sff.statistic.component.ThreeCellStat;
import ru.sff.statistic.component.TwoCellStat;
import ru.sff.statistic.model.LotoTurn;
import ru.sff.statistic.utils.AppUtils;

public class LotoTurnAdapter extends CommonBaseAdapter< LotoTurn > {

    private static final String CLASS_TAG = "LotoTurnAdapter";

    private LotoTurnDataObjectHolder.LotoTurnTrashListener mListener;


    public LotoTurnAdapter( LinkedList< LotoTurn > mItemList ) {
        super( mItemList );

    }

    @Override
    public LotoTurnAdapter.LotoTurnDataObjectHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.rv_item_loto_turn, parent, false );
        LotoTurnAdapter.LotoTurnDataObjectHolder dataObjectHolder = new LotoTurnAdapter.LotoTurnDataObjectHolder( view );
        return dataObjectHolder;
    }

    public void setLotoTurnTrashListener( LotoTurnDataObjectHolder.LotoTurnTrashListener listener ) {
        mListener = listener;
    }


    @Override
    public void onBindViewHolder( final BaseDataObjectHolder h, final int position ) {
        LotoTurnAdapter.LotoTurnDataObjectHolder holder = ( LotoTurnAdapter.LotoTurnDataObjectHolder ) h;
        final LotoTurn turn = mItemList.get( position );
        holder.lotoTurnDate.setTwoCellValue( turn.getStartDraw().getDraw().toString(), turn.getStartDraw().getDrawDate() ,
                 turn.getEndDraw().getDraw().toString(), turn.getEndDraw().getDrawDate() );
        holder.lotoTurnDrawValue.setText( turn.getDrawCount().toString() );
        holder.lotoTurnDrawLabel.setText( AppUtils.getDrawsSfx( turn.getDrawCount() ) );
        int zeroVisible = turn.getBorderZero() != null ? View.VISIBLE : View.GONE;
        holder.lotoTurnZeroLeft.setText( turn.getBorderZero() != null ?  turn.getBorderZero().toString() : "");

    }

    public static class LotoTurnDataObjectHolder extends BaseDataObjectHolder {

        public TwoCellStat lotoTurnDate;
        public TextView lotoTurnDrawValue;
        public TextView lotoTurnDrawLabel;
        public TextView lotoTurnZeroLeft;


        public LotoTurnDataObjectHolder( final View itemView ) {
            super( itemView );
            lotoTurnDate = itemView.findViewById( R.id.turnDateLabelId);
            lotoTurnDrawValue = itemView.findViewById( R.id.turnDrawCountValueId );
            lotoTurnDrawLabel = itemView.findViewById( R.id.turnDrawCountLabelId );
            lotoTurnZeroLeft = itemView.findViewById( R.id.turnZeroLeftId );
            lotoTurnDrawValue.setTypeface( AppConstants.ROTONDA_BOLD );
            lotoTurnDrawLabel.setTypeface( AppConstants.ROBOTO_CONDENCED );
            lotoTurnZeroLeft.setTypeface( AppConstants.ROBOTO_CONDENCED );
        }

        @Override
        public void onClick( View v ) {
            return;
        }

        public interface LotoTurnTrashListener {
            void onLotoTurnTrashClick( String entityName );
        }

    }


}


