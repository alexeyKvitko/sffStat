package ru.sff.statistic.fragment;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.component.DrawResultTable;
import ru.sff.statistic.component.SixBallSet;
import ru.sff.statistic.component.SixBallWin;
import ru.sff.statistic.component.ThreeCellStat;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.modal.ModalMessage;
import ru.sff.statistic.model.ApiResponse;
import ru.sff.statistic.model.Ball;
import ru.sff.statistic.model.BallSetType;
import ru.sff.statistic.model.DrawDetails;
import ru.sff.statistic.model.DrawInfo;
import ru.sff.statistic.model.LotoModel;
import ru.sff.statistic.rest.RestController;
import ru.sff.statistic.utils.AppPreferences;
import ru.sff.statistic.utils.AppUtils;
import ru.sff.statistic.utils.CustomAnimation;


public class DrawDetailsFragment extends BaseFragment {

    private static final String TAG = "LotoDrawsFragment";

    private static final String LOTO_DRAW = "loto_draw";

    private Integer mDraw;
    private LotoModel mLotoModel;
    private DrawInfo mDrawInfo;

    public DrawDetailsFragment() {}

    public static DrawDetailsFragment newInstance( Integer draw ) {
        DrawDetailsFragment fragment = new DrawDetailsFragment();
        Bundle args = new Bundle();
        args.putInt( LOTO_DRAW, draw );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        if ( getArguments() != null ) {
            mDraw = getArguments().getInt( LOTO_DRAW );
        }
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_draw_details, container, false );
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        CustomAnimation.transitionAnimation( getView().findViewById( R.id.drawDetailsScrollViewId ),
                getView().findViewById( R.id.pleaseWaitContainerId ) );
        new GetDrawDetails().execute( );
    }

    private void populateDrawDetails(){
        SixBallSet drawBallSet = getView().findViewById( R.id.drawDetailsSetId );
        Ball[] balls = new Ball[6];
        balls[0] = GlobalManager.getBallByNumber( mLotoModel.getSlotOne() );
        balls[1] = GlobalManager.getBallByNumber( mLotoModel.getSlotTwo() );
        balls[2] = GlobalManager.getBallByNumber( mLotoModel.getSlotThree() );
        balls[3] = GlobalManager.getBallByNumber( mLotoModel.getSlotFour() );
        balls[4] = GlobalManager.getBallByNumber( mLotoModel.getSlotFive() );
        balls[5] = GlobalManager.getBallByNumber( mLotoModel.getSlotSix() );
        drawBallSet.setBallSet( balls, BallSetType.BIGGER );
        drawBallSet.hideRepeatCaption();
        initTextView( R.id.detailsDrawDateTimeId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.detailsDrawDateTimeValueId, AppConstants.ROTONDA_BOLD, mLotoModel.getDrawDate() );
        ThreeCellStat threeCellDate =  getView().findViewById( R.id.threeCellStatDateId );
        threeCellDate.setLeftCell( AppConstants.DAY_OF_WEEK.get(mLotoModel.getDayOfWeek().toUpperCase() )+",", "" );
        threeCellDate.setMiddleCell( mLotoModel.getNumOfWeekInMonth().toString(), "неделя в месяце," );
        threeCellDate.setRightCell( mLotoModel.getDayOfYear().toString(), "день" );

        initTextView( R.id.detailsDrawSumId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.detailsDrawSumValueId, AppConstants.ROTONDA_BOLD, mLotoModel.getSum().toString() );

        ThreeCellStat threeCellEvnOdd =  getView().findViewById( R.id.threeCellEvenOddId );

        String oddWord = " нечетная цифра";
        String evenWord =" четная, ";
        if ( mLotoModel.getEvenCount() > 1 && mLotoModel.getEvenCount() < 5 ){
            evenWord = " четные, ";
        } else if ( mLotoModel.getEvenCount() > 4 ){
            evenWord = " четныx, ";
        }
        if ( mLotoModel.getOddCount() > 1 && mLotoModel.getOddCount() < 5 ){
            oddWord = " нечетные цифры";
        } else if ( mLotoModel.getOddCount() > 4 ){
            oddWord = " нечетныx цифр";
        }
        threeCellEvnOdd.setLeftCell( mLotoModel.getEvenCount().toString(), evenWord );
        threeCellEvnOdd.setMiddleCell( mLotoModel.getOddCount().toString(), oddWord );

        initTextView( R.id.detailsDrawCombinationId, AppConstants.ROTONDA_BOLD );
        SixBallWin combinationBalls = getView().findViewById( R.id.detailsDrawCombinationValueId );
        combinationBalls.setSixBallWins( mLotoModel.getDigitOne(),mLotoModel.getDigitTwo(),
                mLotoModel.getDigitThree(), mLotoModel.getDigitFour(), mLotoModel.getDigitFive(),
                mLotoModel.getDigitSix() );

        ThreeCellStat threeCellCombination =  getView().findViewById( R.id.threeCellStatCombiId );
        threeCellCombination.setLeftCell( AppUtils.getFormatedString( mLotoModel.getCombination().toString() ), " сочетание из " );
        threeCellCombination.setMiddleCell( "8 145 060", "" );

        initTextView( R.id.detailsDrawResultId, AppConstants.ROTONDA_BOLD );
        DrawResultTable drawResultTable = getView().findViewById( R.id.drawResultTableId );
        drawResultTable.setSixBallResult( mDrawInfo.getSixGuessedWin(),
                                            mDrawInfo.getSixGuessedAmount(),
                                                    mDrawInfo.getSixGuessedTotal() );
        drawResultTable.setFiveBallResult( mDrawInfo.getFiveGuessedWin(),
                mDrawInfo.getFiveGuessedAmount(),
                mDrawInfo.getFiveGuessedTotal() );

        drawResultTable.setFourBallResult( mDrawInfo.getFourGuessedWin(),
                mDrawInfo.getFourGuessedAmount(),
                mDrawInfo.getFourGuessedTotal() );

        drawResultTable.setThreeBallResult( mDrawInfo.getThreeGuessedWin(),
                mDrawInfo.getThreeGuessedAmount(),
                mDrawInfo.getThreeGuessedTotal() );

        drawResultTable.setTwoBallResult( mDrawInfo.getTwoGuessedWin(),
                mDrawInfo.getTwoGuessedAmount(),
                mDrawInfo.getTwoGuessedTotal() );

        initTextView( R.id.detailsDrawTicketCountId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.detailsDrawTicketValueId, AppConstants.ROTONDA_BOLD,
                AppUtils.getFormatedString( mDrawInfo.getPersonCount() ) );

        initTextView( R.id.detailsDrawPayAmountLabelId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.detailsDrawPayAmountValueId, AppConstants.ROTONDA_BOLD,
                AppUtils.getFormatedString( mDrawInfo.getPaidAmount() )+" руб." );

        initTextView( R.id.detailsDrawPrizeAmountLabelId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.detailsDrawPrizeAmountValueId, AppConstants.ROTONDA_BOLD,
                AppUtils.getFormatedString( mDrawInfo.getSuperPrize() )+" руб." );


        CustomAnimation.transitionAnimation( getView().findViewById( R.id.pleaseWaitContainerId ),
                getView().findViewById( R.id.drawDetailsScrollViewId ) );
    }

    @Override
    public void onClick( View view ) {

    }


    private class GetDrawDetails extends AsyncTask< Void, Void, String > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground( Void... draw ) {
            String result = null;
            try {
                Call< ApiResponse< DrawDetails > > resultCall = RestController
                        .getApi().getDrawDetails( AppPreferences.getUniqueId(), mDraw );
                Response< ApiResponse< DrawDetails> > resultResponse = resultCall.execute();
                if ( resultResponse.body() != null ) {
                    if ( resultResponse.body().getStatus() == 200 ) {
                        mDrawInfo = resultResponse.body().getResult().getDrawInfo();
                        mLotoModel =  resultResponse.body().getResult().getLotoDraw();
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
            populateDrawDetails();
        }
    }



}
