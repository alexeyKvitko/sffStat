package ru.sff.statistic.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.activity.RouteActivity;
import ru.sff.statistic.component.FieldOrientation;
import ru.sff.statistic.component.FiveNineTable;
import ru.sff.statistic.component.SixBallSet;
import ru.sff.statistic.component.TwoCellStat;
import ru.sff.statistic.component.WinTable;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.modal.ModalMessage;
import ru.sff.statistic.model.ApiResponse;
import ru.sff.statistic.model.Ball;
import ru.sff.statistic.model.BallSetType;
import ru.sff.statistic.model.BallsInfo;
import ru.sff.statistic.model.StoredBallSet;
import ru.sff.statistic.rest.RestController;
import ru.sff.statistic.utils.AppUtils;
import ru.sff.statistic.utils.CustomAnimation;


public class AllResultsFragment extends BaseFragment {

    private static final String TAG = "AllResultsFragment";

    private OnMenuOptionSelectListener mListener;

    private BallsInfo mBallsInfo;
    private ImageView mBackButton;

    private TwoCellStat mDrawRange;
    private TwoCellStat mTotalTicket;
    private TwoCellStat mTotalPaidAmount;
    private TwoCellStat mEvenOdd;

    private SixBallSet mBiggerBallSet;
    private SixBallSet mLessBallSet;
    private SixBallSet mMiddleBallSet;

    private boolean mBiggerShow;
    private boolean mLessShow;
    private boolean mMiddleShow;

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
        CustomAnimation.transitionAnimation( getView().findViewById( R.id.scrollContainerId ),
                getView().findViewById( R.id.pleaseWaitContainerId ) );
        mBackButton = ( ( RouteActivity ) getActivity() ).getBackBtn();
        mBackButton.setOnClickListener( ( View view ) -> {
            getActivity().onBackPressed();
        } );

        mBiggerShow = true;
        mLessShow = true;
        mMiddleShow = true;
        mDrawRange = getView().findViewById( R.id.twoCellDrawRangeId );
        mTotalTicket = getView().findViewById( R.id.twoCellTotalTicketId );
        mTotalPaidAmount = getView().findViewById( R.id.twoCellPaidAmountId );
        mEvenOdd = getView().findViewById( R.id.twoCellEvenOddId );

        initTextView( R.id.drawTitleId ).setTypeface( AppConstants.ROTONDA_BOLD );
        initTextView( R.id.oftenDigitId ).setTypeface( AppConstants.ROTONDA_BOLD );
        initTextView( R.id.lessDigitId ).setTypeface( AppConstants.ROTONDA_BOLD );
        initTextView( R.id.middleDigitId ).setTypeface( AppConstants.ROTONDA_BOLD );
        initTextView( R.id.resultOnTableId ).setTypeface( AppConstants.ROTONDA_BOLD );
        initTextView( R.id.totalTicketLabelId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.totalPaidAmountLabelId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.drawResultsLabelId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.evenOddLabelId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.tableOrientationId, AppConstants.ROBOTO_CONDENCED );
        initTextView( R.id.fallingNumbersLabelId, AppConstants.ROBOTO_CONDENCED );

        initTextView( R.id.biggerButtonId, AppConstants.ROBOTO_CONDENCED );
        initTextView( R.id.lessButtonId, AppConstants.ROBOTO_CONDENCED );
        initTextView( R.id.middleButtonId, AppConstants.ROBOTO_CONDENCED );

        FieldOrientation fieldOrientation = getView().findViewById( R.id.fieldOrientationId );
        fieldOrientation.setOnFieldOrientationListener( () -> {
            mBallTable.redrawTable();
        } );

        setThisOnClickListener( R.id.biggerButtonId, R.id.lessButtonId, R.id.middleButtonId,
                R.id.addToBasketBiggerAllId,R.id.addToBasketLessAllId,R.id.addToBasketMiddleAllId );
        initBasketImage();
    }

    private void initBasketImage(){
        if ( GlobalManager.getStoredBallSet().size() > 0 ){
            Drawable basketSelect = getActivity().getResources().getDrawable( R.drawable.ic_basket_shoko_18dp );
            for(String key : GlobalManager.getStoredBallSet().keySet() ){
                int imageId = AppConstants.FAKE_ID;
                if ( AppConstants.BALL_SET_TOTAL_BIGGER.equals( key ) ){
                    imageId = R.id.basketBiggerAllImageId;
                } else if ( AppConstants.BALL_SET_TOTAL_LESS.equals( key ) ){
                    imageId = R.id.basketLessAllImageId;
                } else if ( AppConstants.BALL_SET_TOTAL_MIDDLE.equals( key ) ){
                    imageId = R.id.basketMiddleAllImageId;
                }
                ((ImageView) getView().findViewById( imageId )).setImageDrawable( basketSelect );
            }
        }
    }

    public void showBallSetBasket() {
        if ( mListener != null ) {
            mListener.onBallSetBasketShow();
        }
    }

    public void changeViewType(){
        mBiggerBallSet.redrawBalls();
        mLessBallSet.redrawBalls();
        mMiddleBallSet.redrawBalls();
        mBallTable.redrawTable();
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
        CustomAnimation.bounceAnimation( view );
        switch ( view.getId() ) {
            case R.id.biggerButtonId:
                mBiggerShow = !mBiggerShow;
                setBallSetButton( view.getId(), mBiggerShow );
                break;
            case R.id.lessButtonId:
                mLessShow = !mLessShow;
                setBallSetButton( view.getId(), mLessShow );
                break;
            case R.id.middleButtonId:
                mMiddleShow = !mMiddleShow;
                setBallSetButton( view.getId(), mMiddleShow );
                break;
            case R.id.addToBasketBiggerAllId:
                CustomAnimation.clickAnimation( view );
                addBallSetToBasket( R.id.addToBasketBiggerAllId, AppConstants.BALL_SET_TOTAL_BIGGER,
                            mBallsInfo.getMoreOften(), BallSetType.BIGGER );
                break;
            case R.id.addToBasketLessAllId:
                CustomAnimation.clickAnimation( view );
                addBallSetToBasket( R.id.addToBasketLessAllId,
                        AppConstants.BALL_SET_TOTAL_LESS, mBallsInfo.getLessOfter(), BallSetType.LESS );
                break;
            case R.id.addToBasketMiddleAllId:
                CustomAnimation.clickAnimation( view );
                addBallSetToBasket( R.id.addToBasketMiddleAllId,
                        AppConstants.BALL_SET_TOTAL_MIDDLE, mBallsInfo.getMiddleOften(), BallSetType.MIDDLE );
                break;
        }
        List<BallSetType> ballSetTypeList = new LinkedList<>(  );
        if( mBiggerShow ){
            ballSetTypeList.add( BallSetType.BIGGER );
        }
        if( mLessShow ){
            ballSetTypeList.add( BallSetType.LESS );
        }
        if( mMiddleShow ){
            ballSetTypeList.add( BallSetType.MIDDLE );
        }
        GlobalManager.getInstance().setBallSetTypes( ballSetTypeList.toArray( new BallSetType[ ballSetTypeList.size() ] ) );
        mBallTable.redrawTable();
    }

    private void addBallSetToBasket( int basketId, String basketType, Ball[] ballSet, BallSetType ballSetType ){
        ImageView basketImage = ( ImageView ) (( LinearLayout) getView().findViewById( basketId ) ).getChildAt( 0 );
        if ( GlobalManager.getStoredBallSet().get( basketType ) == null ){
            StoredBallSet storedBallSet = new StoredBallSet();
            storedBallSet.setBallSets( ballSet );
            storedBallSet.setBallSetType( ballSetType );
            storedBallSet.setStoredDate( AppUtils.formatDate( AppConstants.FULL_DATE_FORMAT, new Date( ) ) );
            storedBallSet.setBallSetName( basketType );
            GlobalManager.getStoredBallSet().put( basketType, storedBallSet );
            basketImage.setImageDrawable( getActivity().getResources().getDrawable( R.drawable.ic_basket_shoko_18dp ) );
        } else {
            GlobalManager.getStoredBallSet().remove( basketType );
            basketImage.setImageDrawable( getActivity().getResources().getDrawable( R.drawable.ic_basket_grey600_18dp ) );
        }
    }

    private void setBallSetButton(int buttonId, boolean btnEnable ){
        TextView btn = getView().findViewById( buttonId );
        if ( btnEnable ){
            btn.setBackground( getActivity().getResources().getDrawable( R.drawable.border_btn_orange ) );
            btn.setTextColor( getActivity().getResources().getColor( R.color.ballOrange ) );
            btn.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_orange_18dp, 0, 0, 0 );
        } else {
            btn.setBackground( getActivity().getResources().getDrawable( R.drawable.border_btn_gray ) );
            btn.setTextColor( getActivity().getResources().getColor( R.color.ballGray) );
            btn.setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0 );
        }
    }

    public interface OnMenuOptionSelectListener {
        // TODO: Update argument type and name
        void onBallSetBasketShow();

    }

    private void populateFragment() {
        mBallTable = getView().findViewById( R.id.fiveNineTableId );
        mBallTable.setBallsInfo( mBallsInfo );

        mBiggerBallSet = getView().findViewById( R.id.biggerBallSetId );
        mBiggerBallSet.setBallSet( mBallsInfo.getMoreOften(), BallSetType.BIGGER );

        mLessBallSet = getView().findViewById( R.id.lessBallSetId );
        mLessBallSet.setBallSet( mBallsInfo.getLessOfter(), BallSetType.LESS );

        mMiddleBallSet = getView().findViewById( R.id.middleBallSetId );
        mMiddleBallSet.setBallSet( mBallsInfo.getMiddleOften(), BallSetType.MIDDLE );

        mDrawRange.setTwoCellValue( "с № " + mBallsInfo.getFirstDraw(), mBallsInfo.getFirstDrawDate(),
                "по № " + mBallsInfo.getLastDraw(), mBallsInfo.getLastDrawDate() );

        mEvenOdd.setTwoCellValue(  AppUtils.getFormatedString( Long.valueOf( mBallsInfo.getEvenOdd().getEvenCount() ) ),
                getActivity().getString( R.string.even_label ),
                AppUtils.getFormatedString( Long.valueOf( mBallsInfo.getEvenOdd().getOddCount() ) ),
                getActivity().getString( R.string.odd_label ) );

        mTotalTicket.setTwoCellValue(  AppUtils.getFormatedString( mBallsInfo.getTotalDrawInfo().getPersonCount() ),
                getActivity().getString( R.string.all_draws_label ),
                AppUtils.getFormatedString(
                        mBallsInfo.getTotalDrawInfo().getPersonCount() / Integer.valueOf( mBallsInfo.getLastDraw() ) ),
                getActivity().getString( R.string.per_draw_label ) );

        mTotalPaidAmount.setTwoCellValue(  AppUtils.getFormatedString( mBallsInfo.getTotalDrawInfo().getPaidAmount() ),
                getActivity().getString( R.string.all_draws_label ),
                AppUtils.getFormatedString(
                        mBallsInfo.getTotalDrawInfo().getPaidAmount() / Integer.valueOf( mBallsInfo.getLastDraw() ) ),
                getActivity().getString( R.string.per_draw_label ) );

        WinTable winTable = getView().findViewById( R.id.winTableAllResultsId );
        winTable.setWinBalls( mBallsInfo.getTotalDrawInfo().getSixGuessedWin(),
                                mBallsInfo.getTotalDrawInfo().getFiveGuessedWin(),
                                mBallsInfo.getTotalDrawInfo().getFourGuessedWin(),
                                mBallsInfo.getTotalDrawInfo().getThreeGuessedWin(),
                                mBallsInfo.getTotalDrawInfo().getTwoGuessedWin() );

        CustomAnimation.transitionAnimation( getView().findViewById( R.id.pleaseWaitContainerId ),
                getView().findViewById( R.id.scrollContainerId ) );
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
            if ( result != null ) {
                ModalMessage.show( getActivity(), "Сообщение", new String[]{ result } );
                ( new Handler() ).postDelayed( () -> {
                    getActivity().onBackPressed();
                }, 2000 );
                return;
            }
            populateFragment();
        }
    }
}
