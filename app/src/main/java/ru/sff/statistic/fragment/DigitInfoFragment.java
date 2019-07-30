package ru.sff.statistic.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;
import ru.sff.statistic.activity.RouteActivity;
import ru.sff.statistic.component.BallSetWithDesc;
import ru.sff.statistic.component.SixBallWin;
import ru.sff.statistic.component.ThreeCellStat;
import ru.sff.statistic.component.TwoCellStat;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.modal.ModalMessage;
import ru.sff.statistic.model.ApiResponse;
import ru.sff.statistic.model.Ball;
import ru.sff.statistic.model.DigitInfo;
import ru.sff.statistic.model.MagnetNumber;
import ru.sff.statistic.model.SimpleLotoModel;
import ru.sff.statistic.rest.RestController;
import ru.sff.statistic.utils.AppUtils;
import ru.sff.statistic.utils.CustomAnimation;


public class DigitInfoFragment extends BaseFragment {

    private static final String TAG = "DigitInfoFragment";

    private static final String BALL_DIGIT = "ball_digit";

    private Ball mBall;
    private DigitInfo mDigitInfo;
    private View mPleaseWait;
    private View mFragmentContainer;
    private LinearLayout mPairsContainer;
    private LinearLayout mThreesContainer;
    private LinearLayout mFoursContainer;
    private LinearLayout mHitFiveContainer;
    private LinearLayout mHitSixContainer;

    private boolean mPairShown;
    private boolean mThreesShown;
    private boolean mFoursShown;
    private boolean mFiveShown;
    private boolean mSixShown;

    public DigitInfoFragment() {}

    public static DigitInfoFragment newInstance( Ball ball ) {
        DigitInfoFragment fragment = new DigitInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable( BALL_DIGIT, ball );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        if ( getArguments() != null ) {
            mBall = ( Ball ) getArguments().getSerializable( BALL_DIGIT );
        }
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        return inflater.inflate( R.layout.fragment_digit_info, container, false );
    }


    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        mPairShown = false;
        mThreesShown = false;
        mFoursShown = false;
        mFiveShown = false;
        mSixShown = false;

        mPleaseWait = getView().findViewById( R.id.pleaseWaitContainerId );
        mFragmentContainer =  getView().findViewById( R.id.digitInfoScrollViewId );
        mFragmentContainer.setVisibility( View.GONE );
        mPleaseWait.setVisibility( View.VISIBLE );
        new GetDigitInfo().execute( );
        initTextView( R.id.digitInfoNumberId, AppConstants.ROTONDA_BOLD, mBall.getBallNumber()+"" );
        initTextView( R.id.digitInfoRepeatLabelId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.digitInfoLastLabelId, AppConstants.ROTONDA_BOLD );
        TwoCellStat repeatCount = getView().findViewById( R.id.twoCellRepeatCountId );
        String percentRepeat = ( mBall.getBallRepeat()*100/ GlobalManager.getPlayedDraws() )+"";
        repeatCount.setTwoCellValue( mBall.getBallRepeat()+"",
                AppUtils.getTimes( mBall.getBallRepeat() ), percentRepeat,"%" );
        (( RouteActivity ) getActivity() ).getBackBtn().setOnClickListener( ( View view ) -> {
            CustomAnimation.clickAnimation( view );
            getActivity().onBackPressed();
        });
    }

    private void populateDigitInfo(){
        if (  mDigitInfo.getLastFallDay() != null ){
            initTextView( R.id.digitInfoLastValueId, AppConstants.ROTONDA_BOLD, mDigitInfo.getLastFallDay()+", " );
        }
        initTextView( R.id.digitInfoDateTimeValueId, AppConstants.ROTONDA_BOLD, mDigitInfo.getLastFall().getDrawDate() );

        ThreeCellStat threeCellDate = getView().findViewById( R.id.threeCellDigitInfoDateId );
        threeCellDate.setLeftCell( AppConstants.DAY_OF_WEEK.get(mDigitInfo.getLastFall().getDayOfWeek().toUpperCase() )+",", "" );
        threeCellDate.setMiddleCell( mDigitInfo.getLastFall().getNumOfWeekInMonth().toString(), "неделя в месяце," );
        threeCellDate.setRightCell( mDigitInfo.getLastFall().getDayOfYear().toString(), "день" );

        SixBallWin combinationBalls = getView().findViewById( R.id.digitInfoCombinationValueId );
        combinationBalls.setSixBallWins( mDigitInfo.getLastFall().getDigitOne(),mDigitInfo.getLastFall().getDigitTwo(),
                mDigitInfo.getLastFall().getDigitThree(), mDigitInfo.getLastFall().getDigitFour(),
                mDigitInfo.getLastFall().getDigitFive(),mDigitInfo.getLastFall().getDigitSix() );
        combinationBalls.setActiveBall( mBall.getBallNumber() );

        initTextView( R.id.digitInfoSeriesLabelId, AppConstants.ROTONDA_BOLD );

        ThreeCellStat threeCellSeries = getView().findViewById( R.id.threeCellDigitInfoSeriesId);
        String shootLabel = AppUtils.getTimes( mDigitInfo.getSeriesCount() )+" подряд, тиражы с ";
        threeCellSeries.setLeftCell(""+(mDigitInfo.getSeriesDrawEnd()- mDigitInfo.getSeriesDrawStart()+1), shootLabel );
        threeCellSeries.setMiddleCell( mDigitInfo.getSeriesDrawStart().toString(), " по " );
        threeCellSeries.setRightCell( mDigitInfo.getSeriesDrawEnd().toString(), "" );

        initTextView( R.id.digitInfoFrequenciesLabelId, AppConstants.ROTONDA_BOLD );

        ThreeCellStat threeCellFreq= getView().findViewById( R.id.threeCellDigitInfoFrequinciesId );
        String rateLabel = AppUtils.getTimes( mDigitInfo.getDayFreqMaxValue() )+", через ";
        threeCellFreq.setLeftCell( mDigitInfo.getDayFreqMaxValue().toString(), rateLabel );
        threeCellFreq.setMiddleCell( mDigitInfo.getDayFreqMax().toString(), AppUtils.getDays(  mDigitInfo.getDayFreqMax() ) );
        threeCellFreq.setRightCell( "", "" );

        ThreeCellStat threeCellFreqAvg= getView().findViewById( R.id.threeCellDigitInfoFreqAvgId );
        threeCellFreqAvg.setLeftCell( "", "В среднем каждые " );
        threeCellFreqAvg.setMiddleCell( mDigitInfo.getDayFreqAvg().toString(), AppUtils.getDays(  mDigitInfo.getDayFreqAvg() ) );
        threeCellFreqAvg.setRightCell( "", "" );

        initTextView( R.id.digitInfoPairLabelId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.digitInfoThreesLabelId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.digitInfoFoursLabelId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.digitInfoFiveLabelId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.digitInfoSixLabelId, AppConstants.ROTONDA_BOLD );


        initTextView( R.id.showPairsId, AppConstants.ROBOTO_CONDENCED );
        initTextView( R.id.showThreesId, AppConstants.ROBOTO_CONDENCED );
        initTextView( R.id.showFoursId, AppConstants.ROBOTO_CONDENCED );
        initTextView( R.id.showHitFiveId, AppConstants.ROBOTO_CONDENCED );
        initTextView( R.id.showHitSixId, AppConstants.ROBOTO_CONDENCED );

        Collections.sort( mDigitInfo.getTwoBalls() );
        Collections.sort( mDigitInfo.getThreeBalls());
        Collections.sort( mDigitInfo.getFourBalls() );

        BallSetWithDesc pairBallSet = getView().findViewById( R.id.digitInfoPairBallSetId );
        pairBallSet.setBallSetWithDigit( mDigitInfo.getTwoBalls().get( 0 ), mDigitInfo.getBallDigit() );
        mPairsContainer = getView().findViewById( R.id.maxPairContainerId );
        addPairs( mPairsContainer, mDigitInfo.getTwoBalls() );

        BallSetWithDesc threesBallSet = getView().findViewById( R.id.digitInfoThreesBallSetId );
        threesBallSet.setBallSetWithDigit( mDigitInfo.getThreeBalls().get( 0 ), mDigitInfo.getBallDigit() );
        mThreesContainer = getView().findViewById( R.id.maxThreesContainerId );
        addPairs( mThreesContainer, mDigitInfo.getThreeBalls() );

        BallSetWithDesc fourBallSet = getView().findViewById( R.id.digitInfoFoursBallSetId );
        fourBallSet.setBallSetWithDigit( mDigitInfo.getFourBalls().get( 0 ), mDigitInfo.getBallDigit() );
        mFoursContainer = getView().findViewById( R.id.maxFoursContainerId );
        addPairs( mFoursContainer, mDigitInfo.getFourBalls() );

        ThreeCellStat fiveHitStat = getView().findViewById( R.id.digitInfoFiveHitRate );
        fiveHitStat.setLeftCell( mDigitInfo.getFiveWinCount().toString(), AppUtils.getTimes( mDigitInfo.getFiveWinCount() ) +", последний " );
        fiveHitStat.setMiddleCell( mDigitInfo.getHitToFive().get(0).getDrawDate(),"" );
        mHitFiveContainer = getView().findViewById( R.id.hitFiveContainerId );
        addHitBalls( mHitFiveContainer, mDigitInfo.getHitToFive() );

        ThreeCellStat sixHitStat = getView().findViewById( R.id.digitInfoSixHitRate );
        sixHitStat.setLeftCell( mDigitInfo.getHitToSix().size()+"", AppUtils.getTimes( mDigitInfo.getHitToSix().size() ) +", последний " );
        sixHitStat.setMiddleCell( mDigitInfo.getHitToSix().get(0).getDrawDate(),"" );
        mHitSixContainer = getView().findViewById( R.id.hitSixContainerId );
        addHitBalls( mHitSixContainer, mDigitInfo.getHitToSix() );

        setThisOnClickListener( R.id.showPairsId, R.id.showThreesId, R.id.showFoursId, R.id.showHitFiveId, R.id.showHitSixId );

        CustomAnimation.transitionAnimation( mPleaseWait, mFragmentContainer );
    }

    private void addPairs( LinearLayout parentLayout, List<MagnetNumber> magnetNumbers){
        for( int idx = 1;idx < magnetNumbers.size(); idx++ ){
            BallSetWithDesc ballSetWithDesc = new BallSetWithDesc( getActivity() );
            ballSetWithDesc.setBallSetWithDigit( magnetNumbers.get( idx ), mDigitInfo.getBallDigit() );
            parentLayout.addView( ballSetWithDesc );
        }
        parentLayout.setVisibility( View.GONE );
    }

    private void addHitBalls( LinearLayout parentLayout, List< SimpleLotoModel > lotoModels ){
        for( SimpleLotoModel loto : lotoModels ){
            BallSetWithDesc ballSetWithDesc = new BallSetWithDesc( getActivity() );
            ballSetWithDesc.setBallSetWithDigit( loto.getBalls(), loto.getDraw(), mDigitInfo.getBallDigit() );
            ballSetWithDesc.setOnClickListener( (View view ) ->{
                Integer draw = ( ( BallSetWithDesc ) view ).getDraw();
                showDrawDetails( draw );
            } );
            parentLayout.addView( ballSetWithDesc );
        }
        parentLayout.setVisibility( View.GONE );
    }

    private void showDrawDetails( Integer draw ){
        Intent intent = new Intent( AppConstants.DRAW_SELECT_MESSAGE );
        intent.putExtra( AppConstants.DRAW_SELECT_ACTION, draw );
        SFFSApplication.getAppContext().sendBroadcast( intent );
    }

    @Override
    public void onClick( View view ) {
        CustomAnimation.clickAnimation( view );
        switch ( view.getId() ){
            case R.id.showPairsId:
                mPairShown = !mPairShown;
                  updateContainer( mPairsContainer, mPairShown, R.id.showPairsId );
                break;
            case R.id.showThreesId:
                mThreesShown = !mThreesShown;
                updateContainer( mThreesContainer, mThreesShown, R.id.showThreesId );
                break;
            case R.id.showFoursId:
                mFoursShown = !mFoursShown;
                updateContainer( mFoursContainer, mFoursShown, R.id.showFoursId );
                break;
            case R.id.showHitFiveId:
                mFiveShown = !mFiveShown;
                updateContainer( mHitFiveContainer, mFiveShown, R.id.showHitFiveId );
                break;
            case R.id.showHitSixId:
                mSixShown = !mSixShown;
                updateContainer( mHitSixContainer, mSixShown, R.id.showHitSixId );
                break;
        }
    }

    private void updateContainer( LinearLayout container, Boolean flag, int buttonId ){
        int visibility =View.VISIBLE;
        int msgColor = getActivity().getResources().getColor( R.color.grayTextColor );
        String msg = "Скрыть";
        if( !flag ){
            visibility =View.GONE;
            msgColor = getActivity().getResources().getColor( R.color.shokoTextColor );
            msg = "Показать";
        }
        container.setVisibility( visibility );
        TextView buttonText = initTextView( buttonId, msg );
        buttonText.setTextColor( msgColor );
    }

    @Override
    public void onPause() {
        getView().findViewById( R.id.digitInfoHeaderId ).setVisibility( View.GONE );
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        (( RouteActivity ) getActivity() ).hidePanelWihMenu();
        getView().findViewById( R.id.digitInfoHeaderId ).setVisibility( View.VISIBLE );
    }

    private class GetDigitInfo extends AsyncTask< Void, Void, String > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground( Void... draw ) {
            String result = null;
            try {
                Call< ApiResponse< DigitInfo > > resultCall = RestController
                        .getApi().getDigitInfo( AppConstants.AUTH_BEARER
                                        + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndWVzdCIsInNjb3BlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9XSwiaXNzIjoiaHR0cDovL2RldmdsYW4uY29tIiwiaWF0IjoxNTU5ODk5MTY1LCJleHAiOjE1NTk5MTcxNjV9.HnyTQF8mG3m3oPlDWL1-SwZ2_gyDx8YYdD_CWWc8dv4",
                                mBall.getBallNumber() );
                Response< ApiResponse< DigitInfo> > resultResponse = resultCall.execute();
                if ( resultResponse.body() != null ) {
                    if ( resultResponse.body().getStatus() == 200 ) {
                        mDigitInfo = resultResponse.body().getResult();
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
            populateDigitInfo();
        }
    }
}
