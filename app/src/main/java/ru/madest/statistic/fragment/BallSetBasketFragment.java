package ru.madest.statistic.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import ru.madest.statistic.AppConstants;
import ru.madest.statistic.R;
import ru.madest.statistic.activity.RouteActivity;
import ru.madest.statistic.manager.GlobalManager;
import ru.madest.statistic.modal.ModalDialog;
import ru.madest.statistic.model.BallSetType;
import ru.madest.statistic.model.StoredBallSet;
import ru.madest.statistic.recycleview.BasketAdapter;
import ru.madest.statistic.utils.CustomAnimation;


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
        ((RouteActivity) getActivity()).getFooter().getRemoveAllBtn().setOnClickListener( (View view) ->{
            CustomAnimation.btnClickAnimation( view );
            removeAllItemsFromBasket();
        } );
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
        ((RouteActivity) getActivity()).getFooter().switchButtonToBasket( true );
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
        ((RouteActivity) getActivity()).getFooter().switchButtonToBasket( false );
    }


    private void removeAllItemsFromBasket(){
        ModalDialog.DialogParams params = ModalDialog.getDialogParms();
        params.setTitle( "Вы действительно хотите удалить все наборы ?" )
                .setMessage( "Подтверждение" )
                .setBlueButtonText( getResources().getString( R.string.button_cancel ) )
                .setBlueButtonId( R.drawable.ic_emog_good_24dp )
                .setWhiteButtonText( getResources().getString( R.string.button_delete ) )
                .setWhiteButtonId( R.drawable.ic_emog_angry_24dp );
        ModalDialog.execute( getActivity(), params, false ).setOnModalBtnClickListener( new ModalDialog.OnModalBtnClickListener() {
            @Override
            public void onBlueButtonClick ( String textValue ) {}

            @Override
            public void onWhiteBtnClick () {
                removeFromStoredBallSet( AppConstants.REMOVE_ALL );
            }
        } );
    }


    @Override
    public void onClick( View view ) {

    }

    @Override
    public void onBasketTrashClick( String entityName, BallSetType ballSetType ) {
        if ( BallSetType.CUSTOM.equals( ballSetType ) ){
            ModalDialog.DialogParams params = ModalDialog.getDialogParms();
            params.setTitle( "Вы действительно хотите удалить набор ?" )
                    .setMessage( "Подтверждение" )
                    .setBlueButtonText( getResources().getString( R.string.button_cancel ) )
                    .setBlueButtonId( R.drawable.ic_emog_good_24dp )
                    .setWhiteButtonText( getResources().getString( R.string.button_delete ) )
                    .setWhiteButtonId( R.drawable.ic_emog_angry_24dp );
            ModalDialog.execute( getActivity(), params, false ).setOnModalBtnClickListener( new ModalDialog.OnModalBtnClickListener() {
                @Override
                public void onBlueButtonClick ( String textValue ) {}

                @Override
                public void onWhiteBtnClick () {
                    removeFromStoredBallSet( entityName );
                }
            } );
        } else {
            removeFromStoredBallSet( entityName );
        }


    }

    private void removeFromStoredBallSet( String entityName ){
        boolean basketEmpty = false;
        if ( AppConstants.REMOVE_ALL.equals( entityName ) ){
            basketEmpty = GlobalManager.removeAllFromStoredBallSet();
        } else {
            basketEmpty = GlobalManager.removeFromStoredBallSet( entityName );
        }
        if( basketEmpty ){
            getActivity().onBackPressed();
            return;
        }
        fillBasketAdapter( GlobalManager.getSortedStoredBallSet() );
        mBasketAdapter.notifyDataSetChanged();
    }

}
