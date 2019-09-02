package ru.sff.statistic.recycleview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;
import ru.sff.statistic.model.LotoTurn;
import ru.sff.statistic.utils.AppUtils;
import ru.sff.statistic.utils.CustomAnimation;

public class LotoTurnAdapter extends CommonBaseAdapter< LotoTurn > {

    private static final String CLASS_TAG = "LotoTurnAdapter";

    private LotoTurnDataObjectHolder.LotoTurnDrawListener mListener;


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

    public void setLotoTurnDrawListener( LotoTurnDataObjectHolder.LotoTurnDrawListener listener ) {
        mListener = listener;
    }


    @Override
    public void onBindViewHolder( final BaseDataObjectHolder h, final int position ) {
        LotoTurnAdapter.LotoTurnDataObjectHolder holder = ( LotoTurnAdapter.LotoTurnDataObjectHolder ) h;
        final LotoTurn turn = mItemList.get( position );
        holder.lotoTurnStartDraw.setText(turn.getStartDraw().getDraw().toString() );
        holder.lotoTurnStartDate.setText(turn.getStartDraw().getDrawDate() );

        holder.lotoTurnEndDraw.setText( " - " + turn.getEndDraw().getDraw().toString() );
        holder.lotoTurnEndDate.setText( " - " + turn.getEndDraw().getDrawDate() );
        holder.lotoTurnDrawValue.setText( turn.getDrawCount().toString() );
        holder.lotoTurnDrawLabel.setText( AppUtils.getDrawsSfx( turn.getDrawCount() ) );
        if( turn.getBorderZero() != null ){
            holder.lotoTurnDrawValue.setBackground( SFFSApplication.getAppContext().getResources().getDrawable( R.drawable.ball_turn_over_dis ) );
            holder.lotoTurnDrawLabel.setTextColor( SFFSApplication.getAppContext().getResources().getColor( R.color.ballRed ) );
            holder.lotoTurnZeroLeft.setVisibility( View.VISIBLE );
            holder.lotoTurnZeroLeft.setText( ", не выпало чисел: " + turn.getBorderZero().toString() );
        } else {
            holder.lotoTurnDrawValue.setBackground( SFFSApplication.getAppContext().getResources().getDrawable( R.drawable.ball_turn_over ) );
            holder.lotoTurnDrawLabel.setTextColor( SFFSApplication.getAppContext().getResources().getColor( R.color.splashTextColor ) );
            holder.lotoTurnZeroLeft.setVisibility( View.GONE );
        }
        boolean allEmpty = true;
        if ( turn.getLeftFiveDigitDraw() != null ){
            holder.lotoTurnFiveContainer.setVisibility( View.VISIBLE );
            holder.lotoTurnLeftFive.setOnClickListener( (View view) ->{
                drawRangeClick( view, turn.getStartDraw().getDraw(), turn.getLeftFiveDigitDraw().getDraw() );
            } );
            holder.lotoTurnLeftFive.setText( turn.getLeftFiveDigitDraw().getDraw().toString() );
            allEmpty = false;
        } else {
            holder.lotoTurnFiveContainer.setVisibility( View.GONE );
        }

        if ( turn.getLeftThreeDigitDraw() != null ){
            holder.lotoTurnThreeContainer.setVisibility( View.VISIBLE );
            holder.lotoTurnLeftThree.setOnClickListener( (View view) ->{
                drawRangeClick( view, turn.getStartDraw().getDraw(), turn.getLeftThreeDigitDraw().getDraw() );
            } );
            holder.lotoTurnLeftThree.setText( turn.getLeftThreeDigitDraw().getDraw().toString() );
            allEmpty = false;
        } else {
            holder.lotoTurnThreeContainer.setVisibility( View.GONE );
        }

        if ( turn.getLeftOneDigitDraw() != null ){
            holder.lotoTurnOneContainer.setVisibility( View.VISIBLE );
            holder.lotoTurnLeftOne.setOnClickListener( (View view) ->{
                drawRangeClick( view, turn.getStartDraw().getDraw(), turn.getLeftOneDigitDraw().getDraw() );
            } );
            holder.lotoTurnLeftOne.setText( turn.getLeftOneDigitDraw().getDraw().toString() );
            allEmpty = false;
        } else {
            holder.lotoTurnOneContainer.setVisibility( View.GONE );
        }
        holder.lotoTurnRangeContainer.setOnClickListener( (View view) -> {
            drawRangeClick( view, turn.getStartDraw().getDraw(), turn.getEndDraw().getDraw() );
        } );
        holder.lotoTurnLeftLabel.setVisibility( allEmpty ? View.GONE : View.VISIBLE);
    }

    private void drawRangeClick(View view, int startDraw, int endDraw ){
        CustomAnimation.clickAnimation( view );
        mListener.onLotoTurnDrawClick( startDraw, endDraw );
    }

    public static class LotoTurnDataObjectHolder extends BaseDataObjectHolder {

        public TextView lotoTurnDrawValue;
        public TextView lotoTurnDrawLabel;
        public TextView lotoTurnZeroLeft;
        public TextView lotoTurnStartDraw;
        public TextView lotoTurnStartDate;
        public TextView lotoTurnEndDraw;
        public TextView lotoTurnEndDate;
        public TextView lotoTurnLeftFive;
        public TextView lotoTurnLeftThree;
        public TextView lotoTurnLeftOne;
        public TextView lotoTurnLeftLabel;
        public LinearLayout lotoTurnFiveContainer;
        public LinearLayout lotoTurnThreeContainer;
        public LinearLayout lotoTurnOneContainer;
        public LinearLayout lotoTurnRangeContainer;


        public LotoTurnDataObjectHolder( final View itemView ) {
            super( itemView );
            lotoTurnStartDraw = itemView.findViewById( R.id.turnStartDrawId );
            lotoTurnStartDraw.setTypeface( AppConstants.ROTONDA_BOLD );

            lotoTurnStartDate = itemView.findViewById( R.id.turnStartDateId );
            lotoTurnStartDate.setTypeface( AppConstants.ROBOTO_CONDENCED );

            lotoTurnEndDraw = itemView.findViewById( R.id.turnEndDrawId );
            lotoTurnEndDraw.setTypeface( AppConstants.ROTONDA_BOLD );

            lotoTurnEndDate = itemView.findViewById( R.id.turnEndDateId );
            lotoTurnEndDate.setTypeface( AppConstants.ROBOTO_CONDENCED );

            lotoTurnLeftFive = itemView.findViewById( R.id.turnLeftFiveId );
            lotoTurnLeftFive.setTypeface( AppConstants.ROTONDA_BOLD );

            lotoTurnLeftThree = itemView.findViewById( R.id.turnLeftThreeId );
            lotoTurnLeftThree.setTypeface( AppConstants.ROTONDA_BOLD );

            lotoTurnLeftOne = itemView.findViewById( R.id.turnLeftOneId );
            lotoTurnLeftOne.setTypeface( AppConstants.ROTONDA_BOLD );

            ( ( TextView ) itemView.findViewById( R.id.turnLeftFiveLabelId ) ).setTypeface( AppConstants.ROBOTO_CONDENCED );
            ( ( TextView ) itemView.findViewById( R.id.turnLeftThreeLabelId ) ).setTypeface( AppConstants.ROBOTO_CONDENCED );
            ( ( TextView ) itemView.findViewById( R.id.turnLeftOneLabelId ) ).setTypeface( AppConstants.ROBOTO_CONDENCED );
            lotoTurnLeftLabel = itemView.findViewById( R.id.turnLeftLabelId );
            lotoTurnLeftLabel.setTypeface( AppConstants.ROTONDA_BOLD );

            lotoTurnLeftFive = itemView.findViewById( R.id.turnLeftFiveId );
            lotoTurnLeftFive.setTypeface( AppConstants.ROTONDA_BOLD );

            lotoTurnDrawValue = itemView.findViewById( R.id.turnDrawCountValueId );
            lotoTurnDrawValue.setTypeface( AppConstants.ROTONDA_BOLD );

            lotoTurnDrawLabel = itemView.findViewById( R.id.turnDrawCountLabelId );
            lotoTurnDrawLabel.setTypeface( AppConstants.ROBOTO_CONDENCED );

            lotoTurnZeroLeft = itemView.findViewById( R.id.turnZeroLeftId );
            lotoTurnZeroLeft.setTypeface( AppConstants.ROBOTO_CONDENCED );

            lotoTurnFiveContainer = itemView.findViewById( R.id.turnLeftFiveContainerId );
            lotoTurnThreeContainer = itemView.findViewById( R.id.turnLeftThreeContainerId );
            lotoTurnOneContainer = itemView.findViewById( R.id.turnLeftOneContainerId );
            lotoTurnRangeContainer = itemView.findViewById( R.id.turnDrawRangeId );
        }

        @Override
        public void onClick( View v ) {
            return;
        }

        public interface LotoTurnDrawListener {
            void onLotoTurnDrawClick( int startDraw, int endDraw );
        }

    }


}


