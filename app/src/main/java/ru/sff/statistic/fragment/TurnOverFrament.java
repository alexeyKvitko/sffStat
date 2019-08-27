package ru.sff.statistic.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.activity.RouteActivity;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.modal.ModalMessage;
import ru.sff.statistic.model.ApiResponse;
import ru.sff.statistic.model.LotoTurn;
import ru.sff.statistic.model.StoredBallSet;
import ru.sff.statistic.recycleview.BasketAdapter;
import ru.sff.statistic.recycleview.LotoTurnAdapter;
import ru.sff.statistic.rest.RestController;
import ru.sff.statistic.utils.CustomAnimation;


public class TurnOverFrament extends BaseFragment implements LotoTurnAdapter.LotoTurnDataObjectHolder.LotoTurnDrawListener {

    private static final String TAG = "TurnOverFrament";

    private RecyclerView mLotoTurnRecView;
    private LotoTurnAdapter mLotoTurnAdapter;

    private ImageView mBackButton;

    private int mSelectedDraw;

    public OnShowDrawsClickListener mListener;

    private List<LotoTurn> mLotoTurns;

    public TurnOverFrament() {}

    public static TurnOverFrament newInstance() {
        TurnOverFrament fragment = new TurnOverFrament();
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
        return inflater.inflate( R.layout.fragment_turn_over, container, false );
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        mSelectedDraw = 1;
        mLotoTurns = new LinkedList<>();
        CustomAnimation.transitionAnimation( getView().findViewById( R.id.lotoTurnsRVId ),
                getView().findViewById( R.id.pleaseWaitContainerId) );
        mBackButton = ( ( RouteActivity ) getActivity() ).getBackBtn();
        mBackButton.setOnClickListener( ( View view ) -> {
            getActivity().onBackPressed();
        } );
        new GetLotoTurnResults().execute();
    }

    private void initRecView() {
        if ( mLotoTurnRecView == null ) {
            mLotoTurnRecView = getView().findViewById( R.id.lotoTurnsRVId );
            mLotoTurnRecView.setLayoutManager( new LinearLayoutManager( getContext(), RecyclerView.VERTICAL, false ) );
            mLotoTurnRecView.setAdapter( mLotoTurnAdapter );
            mLotoTurnRecView.setHasFixedSize( false );
        }
        mLotoTurnRecView.getAdapter().notifyDataSetChanged();
    }

    private void initAdapter() {
        if ( mLotoTurnAdapter == null ) {
            fillLotoTurnAdapter( mLotoTurns );
        }
        mLotoTurnAdapter.setLotoTurnDrawListener( this );
        mLotoTurnAdapter.notifyDataSetChanged();
    }

    private void fillLotoTurnAdapter( List< LotoTurn > entities ) {
        if ( mLotoTurnAdapter == null ) {
            mLotoTurnAdapter = new LotoTurnAdapter( new LinkedList<>() );
        } else {
            mLotoTurnAdapter.deleteAllItem();
        }
        int el = 0;
        for ( int idx = entities.size()-1; idx >= 0; idx-- ) {
            mLotoTurnAdapter.addItem( entities.get(idx), el );
            el++;
        }
    }

    private void startRecView(){
        initAdapter();
        initRecView();
    }

    @Override
    public void onPause() {
        super.onPause();
        if ( mLotoTurnRecView != null ) {
            mLotoTurnRecView.setAdapter( null );
            mLotoTurnRecView = null;
        }
        mLotoTurnAdapter = null;
        mBackButton.setOnClickListener( null );
    }

    public void onAttach( Context context ) {
        super.onAttach( context );
        if ( context instanceof OnShowDrawsClickListener ) {
            mListener = ( OnShowDrawsClickListener ) context;
        } else {
            throw new RuntimeException( context.toString()
                    + " must implement OnShowDrawsClickListener" );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick( View view ) {

    }

    @Override
    public void onLotoTurnDrawClick( int startDraw, int endDraw ) {
        mListener.onShowDrawsClick( startDraw, endDraw );
    }

    private class GetLotoTurnResults extends AsyncTask< Void, Void, String > {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground( Void... args ) {
            String result = null;
            try {
                Call< ApiResponse< List< LotoTurn > > > resultCall =  RestController.getApi().getLotoTurns( AppConstants.AUTH_BEARER
                                    + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndWVzdCIsInNjb3BlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9XSwiaXNzIjoiaHR0cDovL2RldmdsYW4uY29tIiwiaWF0IjoxNTU5ODk5MTY1LCJleHAiOjE1NTk5MTcxNjV9.HnyTQF8mG3m3oPlDWL1-SwZ2_gyDx8YYdD_CWWc8dv4",
                            mSelectedDraw );
                Response< ApiResponse< List< LotoTurn > > > resultResponse = resultCall.execute();
                if ( resultResponse.body() != null ) {
                    if ( resultResponse.body().getStatus() == 200 ) {
                        mLotoTurns = resultResponse.body().getResult();
                    } else {
                        result = resultResponse.body().getMessage();
                    }
                } else {
                    result = getResources().getString( R.string.internal_error );
                }
            } catch ( Exception e ) {
                result = getResources().getString( R.string.internal_error );
                Log.i( TAG, e.getMessage() );
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute( String result ) {
            super.onPostExecute( result );
            if ( result != null ) {
                ModalMessage.show( getActivity(), "Сообщение", new String[]{ result } );
                ( new Handler() ).postDelayed( () -> {
                    getActivity().onBackPressed();
                }, 2000 );
                return;
            }
            CustomAnimation.transitionAnimation( getView().findViewById( R.id.pleaseWaitContainerId ),
                    getView().findViewById( R.id.lotoTurnsRVId ) );
            startRecView();
        }
    }

    public interface OnShowDrawsClickListener {
        void onShowDrawsClick( int startDraw, int endDraw );
    }
}
