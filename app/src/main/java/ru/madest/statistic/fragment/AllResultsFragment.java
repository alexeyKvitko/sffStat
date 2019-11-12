package ru.madest.statistic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import ru.madest.statistic.R;
import ru.madest.statistic.manager.GlobalManager;
import ru.madest.statistic.model.CachedResponseData;
import ru.madest.statistic.model.RequestByDraw;
import ru.madest.statistic.model.RequestType;


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
        RequestByDraw requestByDraw =  new RequestByDraw();
        requestByDraw.setRequestDrawType( RequestType.ALL_DRAW );
        if ( GlobalManager.getCachedResponseData() != null
                    && RequestType.ALL_DRAW.equals( GlobalManager.getCachedResponseData().getLastRequest() )
                            && GlobalManager.getCachedResponseData().getBallsInfo() != null  ){
            mFirstRequest = false;
            initTabs();
        } else {
            GlobalManager.setCachedResponseData( new CachedResponseData() );
            GlobalManager.getCachedResponseData().setLastRequest( RequestType.ALL_DRAW );
            GlobalManager.getCachedResponseData().setRequestByDraw( requestByDraw );
            GlobalManager.getCachedResponseData().setRequestByDate( null );
            GlobalManager.getCachedResponseData().setLastRequest( RequestType.ALL_DRAW );

            fetchDrawData( requestByDraw );
        }
    }

    public void changeViewType() {
        mDrawsPlaneFragment.changeViewType();
    }



    @Override
    public void onClick( View view ) {}

}
