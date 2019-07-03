package ru.sff.statistic.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.sff.statistic.R;
import ru.sff.statistic.component.DrawsPlaneInfo;

public class DrawsPlaneFragment extends BaseFragment {

    private DrawsPlaneInfo mDrawsPlaneInfo;

    public DrawsPlaneFragment() {}

    public static DrawsPlaneFragment newInstance() {
        DrawsPlaneFragment fragment = new DrawsPlaneFragment();
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
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
    }

    public void changeViewType(){
        mDrawsPlaneInfo.changeViewType();
    }

    @Override
    public void onClick( View view ) {

    }
}
