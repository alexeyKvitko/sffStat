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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.activity.RouteActivity;
import ru.sff.statistic.component.ColorBall;
import ru.sff.statistic.component.FiveNineTable;
import ru.sff.statistic.component.SixBallSet;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.modal.ModalMessage;
import ru.sff.statistic.model.ApiResponse;
import ru.sff.statistic.model.Ball;
import ru.sff.statistic.model.BallSetType;
import ru.sff.statistic.model.BallsInfo;
import ru.sff.statistic.rest.RestController;
import ru.sff.statistic.utils.AppUtils;


public class AllResultsFragment extends BaseFragment {

    private static final String TAG = "AllResultsFragment";

    private OnMenuOptionSelectListener mListener;

    private BallsInfo mBallsInfo;
    private ImageView mBackButton;

    private TextView mFirstDraw;
    private TextView mFirstDrawDate;

    private TextView mLastDraw;
    private TextView mLastDrawDate;

    private FiveNineTable mBallTable;

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
        initialize();
    }

    private void initialize() {
        mBackButton = ( ( RouteActivity ) getActivity() ).getBackBtn();
        mBackButton.setOnClickListener( ( View view ) -> {
            getActivity().onBackPressed();
        } );
        mFirstDraw = initTextView( R.id.firstDrawNumId );
        mFirstDrawDate = initTextView( R.id.firstDrawDateId );
        mLastDraw = initTextView( R.id.lastDrawNumId );
        mLastDrawDate = initTextView( R.id.lastDrawDateId );
        mFirstDraw.setTypeface( AppConstants.ROTONDA_BOLD );
        mFirstDrawDate.setTypeface( AppConstants.ROBOTO_CONDENCED );
        mLastDraw.setTypeface( AppConstants.ROTONDA_BOLD );
        mLastDrawDate.setTypeface( AppConstants.ROBOTO_CONDENCED );
        initTextView( R.id.drawTitleId ).setTypeface( AppConstants.ROTONDA_BOLD );
        initTextView( R.id.oftenDigitId ).setTypeface( AppConstants.ROTONDA_BOLD );
        initTextView( R.id.lessDigitId ).setTypeface( AppConstants.ROTONDA_BOLD );
        initTextView( R.id.middleDigitId ).setTypeface( AppConstants.ROTONDA_BOLD );
        initTextView( R.id.resultOnTableId ).setTypeface( AppConstants.ROTONDA_BOLD );
        ( ( RadioButton ) getView().findViewById( R.id.biggerButtonId ) ).setTypeface( AppConstants.ROTONDA_BOLD );
        ( ( RadioButton ) getView().findViewById( R.id.lessButtonId ) ).setTypeface( AppConstants.ROTONDA_BOLD );
        ( ( RadioButton ) getView().findViewById( R.id.middleButtonId ) ).setTypeface( AppConstants.ROTONDA_BOLD );

        setThisOnClickListener( R.id.biggerButtonId, R.id.lessButtonId, R.id.middleButtonId );

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
        new GetAllResults().execute();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBackButton.setOnClickListener( null );
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
        switch ( view.getId() ) {
            case R.id.biggerButtonId:
                redrawTable( BallSetType.BIGGER );
                break;
            case R.id.lessButtonId:
                redrawTable( BallSetType.LESS );
                break;
            case R.id.middleButtonId:
                redrawTable( BallSetType.MIDDLE );
                break;
        }
    }

    private void redrawTable(BallSetType ballSetType ){
        if( GlobalManager.getSelBallType().equals( ballSetType ) ){
            return;
        }
        GlobalManager.setSelBallType( ballSetType );
        mBallTable.redrawTable();
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
                        .getApi().fetchAllResults( AppConstants.AUTH_BEARER
                                + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndWVzdCIsInNjb3BlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9XSwiaXNzIjoiaHR0cDovL2RldmdsYW4uY29tIiwiaWF0IjoxNTU5ODk5MTY1LCJleHAiOjE1NTk5MTcxNjV9.HnyTQF8mG3m3oPlDWL1-SwZ2_gyDx8YYdD_CWWc8dv4" );
                Response< ApiResponse< BallsInfo > > resultResponse = resultCall.execute();
                if ( resultResponse.body() != null ) {
                    if ( resultResponse.body().getStatus() == 200 ) {
                        mBallsInfo = resultResponse.body().getResult();
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
            if ( result != null ){
            ModalMessage.show( getActivity(), "Сообщение", new String[]{ result } );
            ( new Handler() ).postDelayed( () -> {
                getActivity().onBackPressed();
            }, 2000 );
            }

            mBallTable = getView().findViewById( R.id.fiveNineTableId );
            mBallTable.setBallsInfo( mBallsInfo );

            SixBallSet biggerBallSet = getView().findViewById( R.id.biggerBallSetId );
            biggerBallSet.setBallSet( mBallsInfo.getMoreOften(), BallSetType.BIGGER );

            SixBallSet lessBallSet = getView().findViewById( R.id.lessBallSetId );
            lessBallSet.setBallSet( mBallsInfo.getLessOfter(), BallSetType.LESS );

            SixBallSet middleBallSet = getView().findViewById( R.id.middleBallSetId );
            middleBallSet.setBallSet( mBallsInfo.getMiddleOften(), BallSetType.MIDDLE );

            mFirstDraw.setText( "с № " + mBallsInfo.getFirstDraw() );
            mLastDraw.setText( "по № " + mBallsInfo.getLastDraw() );
            mFirstDrawDate.setText( mBallsInfo.getFirstDrawDate() );
            mLastDrawDate.setText( mBallsInfo.getLastDrawDate() );

        }
    }
}
