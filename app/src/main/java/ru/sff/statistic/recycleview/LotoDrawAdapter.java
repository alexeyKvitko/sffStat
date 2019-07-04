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
import ru.sff.statistic.model.Loto;
import ru.sff.statistic.utils.AppUtils;

public class LotoDrawAdapter extends CommonBaseAdapter< Loto > {

    private static final String CLASS_TAG = "LotoDrawAdapter";

    private int mTrashVisibility;

    public LotoDrawAdapter( LinkedList< Loto > mItemList ) {
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
        final Loto lotoDraw = mItemList.get( position );
        int monthTitleVisibility = View.GONE;
        int cardVisibility = View.GONE;
        if ( lotoDraw.getDraw() == null ){
             monthTitleVisibility = View.VISIBLE;
             holder.lotoDrawMonthTitle.setText( lotoDraw.getMonth() );
        } else {
            cardVisibility = View.VISIBLE;
            holder.lotoBallWin.setSixBallWins( lotoDraw.getSlotOne(),lotoDraw.getSlotTwo()
                    ,lotoDraw.getSlotThree(),lotoDraw.getSlotFour()
                    ,lotoDraw.getSlotFive(),lotoDraw.getSlotSix());
            holder.lotoDrawNum.setText( lotoDraw.getDraw().toString() );
            String drawDate = AppUtils.formatDate( AppConstants.FULL_DATE_FORMAT, lotoDraw.getDrawDate() );
            holder.lotoDrawDateTime.setText( drawDate );
            holder.lotoDrawPrize.setText( lotoDraw.getSuperPrize() );

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
