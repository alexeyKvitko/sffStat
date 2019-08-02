package ru.sff.statistic.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.component.DrawsPlaneInfo;

public class DrawsPlaneFragment extends BaseFragment {

    private static final String START_DRAW = "start_draw";
    private static final String END_DRAW = "end_draw";

    private DrawsPlaneInfo mDrawsPlaneInfo;

    private int mStartDraw;
    private int mEndDraw;

    public DrawsPlaneFragment() {}

    public static DrawsPlaneFragment newInstance(int startDraw, int endDraw) {
        DrawsPlaneFragment fragment = new DrawsPlaneFragment();
        Bundle args = new Bundle();
        args.putInt( START_DRAW, startDraw );
        args.putInt( END_DRAW, endDraw );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        if ( getArguments() != null ) {
            mStartDraw = getArguments().getInt( START_DRAW );
            mEndDraw = getArguments().getInt( END_DRAW );
        }
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_draws_plane, container, false );
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        mDrawsPlaneInfo = getView().findViewById( R.id.allDrawsPanelInfoId );
        mDrawsPlaneInfo.setActivity( getActivity(), getView().findViewById( R.id.pleaseWaitContainerId ),
                getView().findViewById( R.id.scrollContainerId ));
        mDrawsPlaneInfo.feetchDrawResults( mStartDraw, mEndDraw );
    }

    public DrawsPlaneInfo getDrawsPlaneInfo() {
        return mDrawsPlaneInfo;
    }

    public void changeViewType(){
        mDrawsPlaneInfo.changeViewType();
    }

    @Override
    public void onClick( View view ) {

    }
}
