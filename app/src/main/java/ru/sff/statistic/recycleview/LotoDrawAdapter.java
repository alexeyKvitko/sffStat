package ru.sff.statistic.recycleview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.LinkedList;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.component.SixBallWin;
import ru.sff.statistic.model.LotoModel;
import ru.sff.statistic.utils.AppUtils;

public class LotoDrawAdapter extends CommonBaseAdapter< LotoModel > {

    private static final String CLASS_TAG = "LotoDrawAdapter";

    private int mTrashVisibility;

    public LotoDrawAdapter( LinkedList< LotoModel > mItemList ) {
        super( mItemList );
        mTrashVisibility = View.VISIBLE;
    }

    public void setLotoDrawDetailsListener( LotoDrawDataObjectHolder.LotoDrawDetailsListener listener ){
        LotoDrawAdapter.LotoDrawDataObjectHolder.mListener = listener;
    }

    @Override
    public LotoDrawAdapter.LotoDrawDataObjectHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate( R.layout.rv_item_loto, parent, false);
        LotoDrawAdapter.LotoDrawDataObjectHolder dataObjectHolder = new LotoDrawAdapter.LotoDrawDataObjectHolder( view );
        return dataObjectHolder;
    }


    public void hideTrashButton() {
        this.mTrashVisibility = View.GONE;
    }

    @Override
    public void onBindViewHolder(final BaseDataObjectHolder h, final int position) {
        LotoDrawAdapter.LotoDrawDataObjectHolder holder = ( LotoDrawAdapter.LotoDrawDataObjectHolder ) h;
        final LotoModel lotoModelDraw = mItemList.get( position );
        int monthTitleVisibility = View.GONE;
        int cardVisibility = View.GONE;
        if ( lotoModelDraw.getDraw() == null ){
             monthTitleVisibility = View.VISIBLE;
             holder.lotoDrawMonthTitle.setText( lotoModelDraw.getMonth() );
        } else {
            cardVisibility = View.VISIBLE;
            holder.lotoBallWin.setSixBallWins( lotoModelDraw.getSlotOne(), lotoModelDraw.getSlotTwo()
                    , lotoModelDraw.getSlotThree(), lotoModelDraw.getSlotFour()
                    , lotoModelDraw.getSlotFive(), lotoModelDraw.getSlotSix());
            holder.lotoDrawNum.setText( lotoModelDraw.getDraw().toString() );
            holder.lotoDrawDateTime.setText( lotoModelDraw.getDrawDate() );
            holder.lotoDrawPrize.setText( lotoModelDraw.getSuperPrize() );

        }
        holder.lotoDrawMonthTitle.setVisibility( monthTitleVisibility );
        holder.lotoDrawCard.setVisibility( cardVisibility );
    }

    public static class LotoDrawDataObjectHolder extends BaseDataObjectHolder {

        public static LotoDrawDetailsListener mListener;

        public CardView lotoDrawCard;
        public SixBallWin lotoBallWin;
        public TextView lotoDrawMonthTitle;
        public TextView lotoDrawNum;
        public TextView lotoDrawDateTime;
        public TextView lotoDrawPrize;

        public LotoDrawDataObjectHolder( final View itemView) {
            super(itemView);
            lotoDrawCard = itemView.findViewById( R.id.lotoDrawCardId );
            lotoBallWin = itemView.findViewById( R.id.lotoDrawDigitsId );
            lotoDrawMonthTitle = itemView.findViewById( R.id.lotoDrawMonthId );
            lotoDrawMonthTitle.setTypeface( AppConstants.ROBOTO_BLACK );
            lotoDrawNum = itemView.findViewById( R.id.lotoDrawNumId );
            lotoDrawNum.setTypeface( AppConstants.ROTONDA_BOLD );
            lotoDrawDateTime = itemView.findViewById( R.id.lotoDrawDateTimeId );
            lotoDrawDateTime.setTypeface(  AppConstants.ROBOTO_CONDENCED );
            lotoDrawPrize = itemView.findViewById( R.id.lotoDrawPrizeValueId );
            lotoDrawPrize.setTypeface( AppConstants.ROTONDA_BOLD );
            TextView prizeLabel = itemView.findViewById( R.id.lotoDrawPrizeLabelId );
            prizeLabel.setTypeface( AppConstants.ROBOTO_CONDENCED );
       }


        @Override
        public void onClick( View v ) {
            if ( mListener != null ){
                mListener.onLotoDrawDetailsClick( ((TextView) v.findViewById( R.id.lotoDrawNumId )).getText().toString() );
            }
            return;
        }

        public interface LotoDrawDetailsListener {
            void onLotoDrawDetailsClick( String draw );
        }

    }


}
