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

//    private static final String START_DRAW = "start_draw";
//    private static final String END_DRAW = "end_draw";

    private DrawsPlaneInfo mDrawsPlaneInfo;

    public DrawsPlaneFragment() {}

    public static DrawsPlaneFragment newInstance() {
        return new DrawsPlaneFragment();
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_draws_plane, container, false );
        return view;
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        mDrawsPlaneInfo = getView().findViewById( R.id.allDrawsPanelInfoId );
        mDrawsPlaneInfo.setActivity( getActivity(), getView().findViewById( R.id.pleaseWaitContainerId ),
                getView().findViewById( R.id.scrollContainerId ));
        mDrawsPlaneInfo.populateDrawsPlane();
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
