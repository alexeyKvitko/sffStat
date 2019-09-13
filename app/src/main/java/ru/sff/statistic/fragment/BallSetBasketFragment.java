package ru.sff.statistic.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import ru.sff.statistic.R;
import ru.sff.statistic.activity.RouteActivity;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.model.StoredBallSet;
import ru.sff.statistic.recycleview.BasketAdapter;


public class BallSetBasketFragment extends BaseFragment implements BasketAdapter.BasketDataObjectHolder.BasketTrashListener {

    private RecyclerView mBasketRecView;
    private BasketAdapter mBasketAdapter;

    public BallSetBasketFragment() {}

    public static BallSetBasketFragment newInstance( ) {
        BallSetBasketFragment fragment = new BallSetBasketFragment();
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
        return inflater.inflate( R.layout.fragment_ball_set_basket, container, false );
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
    }

    private void initRecView() {
        if ( mBasketRecView == null ) {
            mBasketRecView = getView().findViewById( R.id.basketRVId );
            mBasketRecView.setLayoutManager( new LinearLayoutManager( getContext(), RecyclerView.VERTICAL, false ) );
            mBasketRecView.setAdapter( mBasketAdapter );
            mBasketRecView.setHasFixedSize( false );
        }
        mBasketRecView.getAdapter().notifyDataSetChanged();
    }

    private void initAdapter() {
        if ( mBasketAdapter == null ) {
            fillBasketAdapter( GlobalManager.getSortedStoredBallSet() );
        }
        mBasketAdapter.setBasketTrashListener( this );
        mBasketAdapter.notifyDataSetChanged();
    }

    private void fillBasketAdapter( List< StoredBallSet > entities ) {
        if ( mBasketAdapter == null ) {
            mBasketAdapter = new BasketAdapter( new LinkedList<>() );
        } else {
            mBasketAdapter.deleteAllItem();
        }
        int el = 0;
        for ( int idx = entities.size()-1; idx >= 0; idx-- ) {
            mBasketAdapter.addItem( entities.get(idx), el );
            el++;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initAdapter();
        initRecView();
    }

    @Override
    public void onPause() {
        super.onPause();
        if ( mBasketRecView != null ) {
            mBasketRecView.setAdapter( null );
            mBasketRecView = null;
        }
        mBasketAdapter = null;
        mBackButton.setOnClickListener( null );
    }



    @Override
    public void onClick( View view ) {

    }

    @Override
    public void onBasketTrashClick( String entityName ) {
        GlobalManager.getStoredBallSet().remove( entityName );
        if( GlobalManager.getStoredBallSet().size() == 0 ){
            getActivity().onBackPressed();
            return;
        }
        fillBasketAdapter( GlobalManager.getSortedStoredBallSet() );
        mBasketAdapter.notifyDataSetChanged();
    }
}
