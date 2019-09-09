package ru.sff.statistic.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.component.BaseComponent;
import ru.sff.statistic.utils.CustomAnimation;


public class DonateFragment extends BaseFragment {

    private static final int[] DONATE_IDS = new int[]{ R.id.donateHundredId, R.id.donateTwoHundredId, R.id.donateThreeHundredId,
            R.id.donateFiveHundredId, R.id.donateSevenHundredId };


    private OnDonateChooseListener mListener;

    public DonateFragment() {}

    public static DonateFragment newInstance() {
        DonateFragment fragment = new DonateFragment();
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        return inflater.inflate( R.layout.fragment_donate, container, false );
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        initTextView( R.id.donateLabelId, AppConstants.ROTONDA_BOLD );

        initTextView( R.id.donateHundredId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.donateTwoHundredId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.donateThreeHundredId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.donateFiveHundredId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.donateSevenHundredId, AppConstants.ROTONDA_BOLD );

        setThisOnClickListener( R.id.donateHundredId, R.id.donateTwoHundredId, R.id.donateThreeHundredId,
                R.id.donateFiveHundredId, R.id.donateSevenHundredId );

    }

    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );
        if ( context instanceof OnDonateChooseListener ) {
            mListener = ( OnDonateChooseListener ) context;
        } else {
            throw new RuntimeException( context.toString()
                    + " must implement OnDonateChooseListener" );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick( View view ) {
        CustomAnimation.clickAnimation( view );
        for( int id: DONATE_IDS ){
            (( TextView) getView().findViewById( id )).setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_checkbox_blank_outline_black_18dp, 0, 0, 0 );
        }
        (( TextView) view ).setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_box_outline_black_18dp, 0, 0, 0 );

    }


    public interface OnDonateChooseListener {
        void onDonateAction( int amount );

    }
}
