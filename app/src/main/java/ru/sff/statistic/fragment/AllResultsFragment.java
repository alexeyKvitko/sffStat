package ru.sff.statistic.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.component.ColorBall;
import ru.sff.statistic.component.FiveNineTable;
import ru.sff.statistic.component.SixBallSet;
import ru.sff.statistic.model.ApiResponse;
import ru.sff.statistic.model.Ball;
import ru.sff.statistic.model.BallSetType;
import ru.sff.statistic.model.BallsInfo;
import ru.sff.statistic.rest.RestController;


public class AllResultsFragment extends BaseFragment {

    private static final String TAG = "AllResultsFragment";

    private OnMenuOptionSelectListener mListener;

    private BallsInfo mBallsInfo;

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
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if ( mListener != null ) {
            mListener.onMainStatisticSelect();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        new GetAllResults().execute(  );
    }

    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );
        if ( context instanceof OnMenuOptionSelectListener ) {
            mListener = ( OnMenuOptionSelectListener ) context;
        } else {
            throw new RuntimeException( context.toString()
                    + " must implement OnMenuOptionSelectListener" );
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

    public interface OnMenuOptionSelectListener {
        // TODO: Update argument type and name
        void onMainStatisticSelect();
    }

    private class GetAllResults extends AsyncTask< Void, Void, String > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground( Void... args ) {
            String result = null;
            try {
                Call< ApiResponse< BallsInfo > > resultCall = RestController
                        .getApi().fetchAllResults();
                Response< ApiResponse< BallsInfo > > resultResponse = resultCall.execute();
                if ( resultResponse.body() != null ) {
                    if ( resultResponse.body().getStatus() == 200 ) {
                        mBallsInfo = ( BallsInfo ) resultResponse.body().getResult();
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
//            ModalMessage.show( getActivity(), "Сообщение", new String[]{ result } );
//            ( new Handler() ).postDelayed( () -> {
//                backPressed();
//            }, 2000 );
            FiveNineTable table = getView().findViewById( R.id.fiveNineTableId );
            table.fillFiveNineTable( mBallsInfo.getDrawBalls() );
            SixBallSet biggerBallSet = getView().findViewById( R.id.biggerBallSetId );
            biggerBallSet.setBallSet( mBallsInfo.getMoreOften(), BallSetType.BIGGER );
        }
    }
}
