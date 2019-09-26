package ru.sff.statistic.recycleview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.component.SixBallSet;
import ru.sff.statistic.model.BallSetType;
import ru.sff.statistic.model.StoredBallSet;
import ru.sff.statistic.utils.CustomAnimation;


public class BasketAdapter extends CommonBaseAdapter< StoredBallSet > {

    private static final String CLASS_TAG = "BasketAdapter";

    private BasketDataObjectHolder.BasketTrashListener mListener;

    private int mTrashVisibility;

    public BasketAdapter( LinkedList< StoredBallSet > mItemList ) {
        super( mItemList );
        mTrashVisibility = View.VISIBLE;
    }

    @Override
    public BasketAdapter.BasketDataObjectHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate( R.layout.rv_item_basket, parent, false);
        BasketAdapter.BasketDataObjectHolder dataObjectHolder = new BasketAdapter.BasketDataObjectHolder( view );
        return dataObjectHolder;
    }

    public void setBasketTrashListener( BasketDataObjectHolder.BasketTrashListener listener ){
        mListener = listener;
    }

    public void hideTrashButton() {
        this.mTrashVisibility = View.GONE;
    }

    @Override
    public void onBindViewHolder(final BaseDataObjectHolder h, final int position) {
        BasketAdapter.BasketDataObjectHolder holder = ( BasketAdapter.BasketDataObjectHolder ) h;
        final StoredBallSet ballSet = mItemList.get( position );
        holder.basketBallSet.setBallSet( ballSet.getBallSets(), ballSet.getBallSetType() );
        holder.basketEntityName.setText( ballSet.getBallSetName() );
        holder.basketEntityDraws.setText( ballSet.getDrawCount() );
        holder.basketEntityDate.setText( ballSet.getStoredDate() );
        holder.basketTrashImg.setOnClickListener( ( View view )->{
            CustomAnimation.clickAnimation( view );
            mListener.onBasketTrashClick( ballSet.getBallSetName(), ballSet.getBallSetType() );
        } );
    }

    public static class BasketDataObjectHolder extends BaseDataObjectHolder {

        public SixBallSet basketBallSet;
        public LinearLayout basketTrashImg;
        public TextView basketEntityName;
        public TextView basketEntityDraws;
        public TextView basketEntityDate;

        public BasketDataObjectHolder( final View itemView) {
            super(itemView);
            basketBallSet = itemView.findViewById( R.id.ballSetDigitsId );
            basketTrashImg = itemView.findViewById( R.id.removeBallSetId );
            basketEntityName = itemView.findViewById( R.id.ballSetNameId );
            basketEntityName.setTypeface( AppConstants.ROTONDA_BOLD );
            basketEntityDraws = itemView.findViewById( R.id.ballSetDrawsId );
            basketEntityDraws.setTypeface( AppConstants.ROBOTO_CONDENCED );

            basketEntityDate = itemView.findViewById( R.id.ballSetDateTimeId );
            basketEntityDate.setTypeface( AppConstants.ROBOTO_CONDENCED );
        }

        @Override
        public void onClick( View v ) {
            return;
        }

        public interface BasketTrashListener {
            void onBasketTrashClick( String entityName, BallSetType ballSetType );
        }

    }


}

