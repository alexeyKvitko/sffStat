package ru.sff.statistic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import ru.sff.statistic.R;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.model.DrawRequestType;
import ru.sff.statistic.model.RequestByDraw;


public class AllResultsFragment extends TabbedFragment {

    private static final String TAG = "AllResultsFragment";


    public AllResultsFragment() {
    }

    public static AllResultsFragment newInstance() {
        AllResultsFragment fragment = new AllResultsFragment();
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        return inflater.inflate( R.layout.fragment_all_result, container, false );
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        GlobalManager.setCachedRequestByDraw( null );
        RequestByDraw requestByDraw =  new RequestByDraw();
        requestByDraw.setDrawRequestType( DrawRequestType.ALL_DRAW );
        fetchDrawData( requestByDraw );

    }

    public void changeViewType() {
        mDrawsPlaneFragment.changeViewType();
    }



    @Override
    public void onClick( View view ) {}

}
