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

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.modal.ModalMessage;
import ru.sff.statistic.model.ApiResponse;
import ru.sff.statistic.model.LotoModel;
import ru.sff.statistic.recycleview.LotoDrawAdapter;
import ru.sff.statistic.rest.RestController;


public class LotoDrawsFragment extends BaseFragment implements LotoDrawAdapter.
                                                LotoDrawDataObjectHolder.LotoDrawDetailsListener {


    private static final String TAG = "LotoDrawsFragment";

    public OnDrawDetailsClickListener mListener;

    private RecyclerView mLotoDrawRecView;
    private LotoDrawAdapter mLotoDrawAdapter;

    private List< LotoModel > mLotoModelDraws;

    public LotoDrawsFragment() {}


    public static LotoDrawsFragment newInstance() {
        LotoDrawsFragment fragment = new LotoDrawsFragment();
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        return inflater.inflate( R.layout.fragment_loto_draws, container, false );
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        Calendar cal = Calendar.getInstance();
        new GetLotoDraws().execute( cal.get( Calendar.YEAR ) );
    }

    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );
        if ( context instanceof OnDrawDetailsClickListener ) {
            mListener = ( OnDrawDetailsClickListener ) context;
        } else {
            throw new RuntimeException( context.toString()
                    + " must implement OnDrawDetailsClickListener" );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initRecView() {
        if ( mLotoDrawRecView == null ) {
            mLotoDrawRecView = getView().findViewById( R.id.lotoDrawRVId );
            mLotoDrawRecView.setLayoutManager( new LinearLayoutManager( getContext(), RecyclerView.VERTICAL, false ) );
            mLotoDrawRecView.setAdapter( mLotoDrawAdapter );
            mLotoDrawRecView.setHasFixedSize( false );
        }
        mLotoDrawRecView.getAdapter().notifyDataSetChanged();
    }

    private void initAdapter() {
        if ( mLotoDrawAdapter == null ) {
            fillLotoDrawAdapter( mLotoModelDraws );
        }
        mLotoDrawAdapter.setLotoDrawDetailsListener( this );
        mLotoDrawAdapter.notifyDataSetChanged();
    }

    private void fillLotoDrawAdapter( List< LotoModel > entities ) {
        if ( mLotoDrawAdapter == null ) {
            mLotoDrawAdapter = new LotoDrawAdapter( new LinkedList<>() );
        } else {
            mLotoDrawAdapter.deleteAllItem();
        }
        int el = 0;
        for ( LotoModel lotoModel : entities ) {
            mLotoDrawAdapter.addItem( lotoModel, el );
            el++;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if ( mLotoDrawRecView != null ) {
            mLotoDrawRecView.setAdapter( null );
            mLotoDrawRecView = null;
        }
        mLotoDrawAdapter = null;
    }

    @Override
    public void onClick( View view ) {}

    private void populateLotoDraws() {
        initAdapter();
        initRecView();
    }

    @Override
    public void onLotoDrawDetailsClick( String drawStr ) {
        if ( mListener != null ){
            Integer draw = Integer.valueOf( drawStr );
            for( LotoModel lotoModel : mLotoModelDraws ){
                if( lotoModel.getDraw() != null && lotoModel.getDraw().equals( draw ) ){
                    mListener.onDrawDetailsClick( lotoModel );
                    break;
                }
            }
        }
    }

    public interface OnDrawDetailsClickListener{
        void onDrawDetailsClick( LotoModel lotoModel);
    }

    private class GetLotoDraws extends AsyncTask< Integer, Void, String > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground( Integer... years ) {
            String result = null;
            try {
                Call< ApiResponse< List< LotoModel > > > resultCall = RestController
                        .getApi().getLotoDrawsByYear( AppConstants.AUTH_BEARER
                                        + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndWVzdCIsInNjb3BlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9XSwiaXNzIjoiaHR0cDovL2RldmdsYW4uY29tIiwiaWF0IjoxNTU5ODk5MTY1LCJleHAiOjE1NTk5MTcxNjV9.HnyTQF8mG3m3oPlDWL1-SwZ2_gyDx8YYdD_CWWc8dv4",
                                years[ 0 ] );
                Response< ApiResponse< List< LotoModel > > > resultResponse = resultCall.execute();
                if ( resultResponse.body() != null ) {
                    if ( resultResponse.body().getStatus() == 200 ) {
                        mLotoModelDraws = resultResponse.body().getResult();
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
            populateLotoDraws();
        }
    }
}