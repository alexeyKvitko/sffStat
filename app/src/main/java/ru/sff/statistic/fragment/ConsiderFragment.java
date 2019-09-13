package ru.sff.statistic.fragment;


import android.graphics.drawable.Drawable;
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

import com.google.gson.internal.LinkedHashTreeMap;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Response;
import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;
import ru.sff.statistic.component.BallConsider;
import ru.sff.statistic.component.BallSetItem;
import ru.sff.statistic.component.BaseComponent;
import ru.sff.statistic.component.FieldOrientation;
import ru.sff.statistic.component.FiveNineTable;
import ru.sff.statistic.component.SixBallSet;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.modal.ModalDialog;
import ru.sff.statistic.modal.ModalMessage;
import ru.sff.statistic.model.ApiResponse;
import ru.sff.statistic.model.Ball;
import ru.sff.statistic.model.BallSetType;
import ru.sff.statistic.model.BallsInfo;
import ru.sff.statistic.model.CachedResponseData;
import ru.sff.statistic.model.ConsiderInfo;
import ru.sff.statistic.model.ConsiderRequest;
import ru.sff.statistic.model.ConsiderResponse;
import ru.sff.statistic.model.DigitInfo;
import ru.sff.statistic.model.RequestByDate;
import ru.sff.statistic.model.RequestByDraw;
import ru.sff.statistic.model.RequestType;
import ru.sff.statistic.model.StoredBallSet;
import ru.sff.statistic.rest.RestController;
import ru.sff.statistic.utils.AppPreferences;
import ru.sff.statistic.utils.AppUtils;
import ru.sff.statistic.utils.CustomAnimation;


public class ConsiderFragment extends BaseFragment implements BallSetItem.OnBallSetItemClickListener {

    private static final String TAG = "ConsiderFragment";

    private FiveNineTable mBallTable;

    private BallsInfo mBallsInfo;

    private Map< Integer, Ball > mAllBalls;


    private ConsiderResponse mConsiderResponse;
    private ConsiderRequest mConsiderRequest;

    private LinearLayout mConsiderResultBody;
    private boolean mRequestProcessed;

    private TextView mLastFallInBallSet;
    private boolean mShowLastFall;

    public ConsiderFragment() {
    }


    public static ConsiderFragment newInstance() {
        ConsiderFragment fragment = new ConsiderFragment();
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        return inflater.inflate( R.layout.fragment_consider, container, false );
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );

        GlobalManager.setAllBallSetTypes();
        if( GlobalManager.getCachedResponseData() != null ){
            GlobalManager.getCachedResponseData().clearAllRequests();
        } else {
            GlobalManager.setCachedResponseData( new CachedResponseData() );
        }

        RequestByDraw requestByDraw = new RequestByDraw();
        requestByDraw.setRequestDrawType( RequestType.ALL_DRAW );
        GlobalManager.getCachedResponseData().setLastRequest( RequestType.ALL_DRAW );
        GlobalManager.getCachedResponseData().setRequestByDraw( requestByDraw );
        GlobalManager.getCachedResponseData().setTotalDraw( GlobalManager.getPlayedDraws() );

        initTextView( R.id.considerTableOrientationLabelId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.considerResultOnTableId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.considerBallSetLabelId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.considerCombinationLabelId, AppConstants.ROTONDA_BOLD );

        initTextView( R.id.considerRareLabelId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.considerRareDescId, AppConstants.ROBOTO_CONDENCED );

        SixBallSet rareBallSet = getView().findViewById( R.id.considerRareBallSetId );
        rareBallSet.setBallSet( getRareBalls(), BallSetType.RARE );

        mLastFallInBallSet = initTextView( R.id.showLastFallInBallSetId, AppConstants.ROTONDA_BOLD );
        mConsiderResultBody = getView().findViewById( R.id.considerBodyId );
        mRequestProcessed = false;
        mShowLastFall = false;
        mAllBalls = new LinkedHashMap<>();
        for ( int i = 1; i < 46; i++ ) {
            mAllBalls.put( i, new Ball( i, 0, BallSetType.ALL ) );
        }
        addStoredBallSets( mAllBalls );
        mBallsInfo = new BallsInfo();
        mBallsInfo.setDrawBalls( new LinkedList<>() );
        mBallsInfo.getDrawBalls().addAll( mAllBalls.values() );
        mBallTable = getView().findViewById( R.id.considerFiveNineTableId );
        mBallTable.setBallOrientation( AppConstants.HORIZONTAL_ORIENTATION );
        mBallTable.clearTable();
        mBallTable.setBallsInfo( mBallsInfo );
        FieldOrientation fieldOrientation = getView().findViewById( R.id.considerFieldOrientationId );
        fieldOrientation.setOnFieldOrientationListener( () -> {
            mBallTable.redrawTable();
        } );
        setThisOnClickListener( R.id.considerBallSetLabelId, R.id.considerAddToStoredId, R.id.considerGetInfoId,
                R.id.showLastFallInBallSetId );
    }

    private void addStoredBallSets( Map< Integer, Ball > allBalls ) {
        if ( GlobalManager.getStoredBallSet() != null
                && GlobalManager.getStoredBallSet().size() > 0 ) {
            LinearLayout ballSetContainer = getView().findViewById( R.id.considerBallSetContainerId );
            List<StoredBallSet> sorted = GlobalManager.getSortedStoredBallSet();
            for ( int i = sorted.size()-1; i >= 0 ; i-- ) {
                StoredBallSet ballSet = sorted.get(i);
                for ( Ball ball : ballSet.getBallSets() ) {
                    Ball selectedBall = allBalls.get( ball.getBallNumber() );
                    selectedBall.setBallRepeat( selectedBall.getBallRepeat() + 1 );
                    setBallSetType( selectedBall,  ball.getBallType() );
                }
                BallSetItem ballSetItem = new BallSetItem( getContext() );
                ballSetItem.setOnBallSetItemClickListener( this );
                ballSetItem.setSelectedBallSet( ballSet );
                ballSetContainer.addView( ballSetItem );
            }
        } else {
            initTextView( R.id.considerBallSetLabelId ).setVisibility( View.GONE );
        }
    }

    private void setBallSetType( Ball ball, BallSetType newType ){
        if( BallSetType.ALL.equals( ball.getBallType() ) || newType.equals( ball.getBallType() ) ){
            ball.setBallType( newType );
            return;
        }
        if( !ball.getBallType().equals( newType ) ){
            if ( ball.getComby() == null ){
                ball.setComby( new HashSet<>() );
                ball.getComby().add( ball.getBallType() );
            }
            if( !BallSetType.COMBY.equals( newType ) ){
                ball.getComby().add( newType );
            }
            ball.setBallType( BallSetType.COMBY );
        }
    }

    private void clearStoredBallSets() {
        LinearLayout ballSetContainer = getView().findViewById( R.id.considerBallSetContainerId );
        ballSetContainer.removeAllViews();
        for ( Ball ball : mAllBalls.values() ) {
            ball.setBallRepeat( 0 );
        }
    }

    private void addToStoredBallSet() {
        List< Ball > balls = mBallTable.getSelectedBall();
        if ( balls.size() == 6 ) {
            ModalDialog.DialogParams params = ModalDialog.getDialogParms();
            params.setTitle( "Введите имя набора" )
                    .setMessage( "У Вас остался незавершенный заказ !" )
                    .setBlueButtonText( getResources().getString( R.string.button_basket ) )
                    .setBlueButtonId( R.drawable.ic_basket_shoko_18dp )
                    .setWhiteButtonText( getResources().getString( R.string.button_cancel ) )
                    .setWhiteButtonId( R.drawable.ic_close_gray_18dp );
            ModalDialog.execute( getActivity(), params ).setOnModalBtnClickListener( new ModalDialog.OnModalBtnClickListener() {
                @Override
                public void onBlueButtonClick( String textValue ) {
                    Ball[] ballSet = new Ball[ balls.size() ];
                    for( int i = 0; i < balls.size(); i++ ){
                        Ball ball =  new Ball( balls.get(i).getBallNumber(),0 , BallSetType.CUSTOM );
                        ballSet[i] = ball;
                    }
                    addCustomBallSet( textValue.toUpperCase(), ballSet );
                }
                @Override
                public void onWhiteBtnClick() {

                }
            } );
        } else {
            ModalMessage.show( getActivity(), "Сообщение", new String[]{ "Добавить в корзину можно только 6 шаров !" } );
        }
    }

    private void addCustomBallSet( String setName, Ball[] ballSet ) {
        addBallSetToBasket( setName, ballSet, BallSetType.CUSTOM );
        clearStoredBallSets();
        addStoredBallSets( mAllBalls );
        mBallsInfo.getDrawBalls().clear();
        mBallsInfo.getDrawBalls().addAll( mAllBalls.values() );
        mBallTable.redrawTable( mBallsInfo );
    }


    private void addBallSetToBasket( String basketName, Ball[] ballSet, BallSetType ballSetType ) {
        if ( GlobalManager.getStoredBallSet().get( basketName ) == null ) {
            StoredBallSet storedBallSet = new StoredBallSet();
            storedBallSet.setBallSets( ballSet );
            storedBallSet.setBallSetType( ballSetType );
            storedBallSet.setStoredDate( AppUtils.formatDate( AppConstants.FULL_DATE_FORMAT, new Date() ) );
            storedBallSet.setBallSetName( basketName );
            storedBallSet.setDrawCount( AppConstants.ZERO_DIGIT );
            GlobalManager.getStoredBallSet().put( basketName, storedBallSet );
        } else {
            ModalMessage.show( getActivity(), "Сообщение", new String[]{ "Набор с таким именем существует" } );
        }
    }

    private void getConsiderInfo() {
        List< Ball > balls = mBallTable.getSelectedBall();
        if ( balls.size() < 6 || balls.size() > 13 ) {
            ModalMessage.show( getActivity(), "Сообщение", new String[]{ "Выберите от 6 до 13 шаров." } );
            return;
        }
        mConsiderRequest = new ConsiderRequest();
        for ( Ball ball : balls ) {
            mConsiderRequest.getBalls().add( ball.getBallNumber() );
        }
        CustomAnimation.transitionAnimation( getView().findViewById( R.id.considerMainLayoutId ),
                getView().findViewById( R.id.pleaseWaitContainerId ) );

        new FetchConsiderInfo().execute();
    }

    private void clearConsiderInfo() {
        mRequestProcessed = false;
        changeConsiderUIText( R.string.select_combination_label, R.string.button_consider );
    }

    private void populateConsiderResponse() {
        mRequestProcessed = true;
        changeConsiderUIText( R.string.consider_result, R.string.button_clear );
        for ( ConsiderInfo considerInfo : mConsiderResponse.getConsiderInfos() ) {
            BallConsider ballConsider = new BallConsider( getContext() );
            ballConsider.setBallConsider( considerInfo, mConsiderRequest.getBalls() );
            mConsiderResultBody.addView( ballConsider );
        }
        CustomAnimation.transitionAnimation( getView().findViewById( R.id.pleaseWaitContainerId ),
                getView().findViewById( R.id.considerMainLayoutId ) );
    }

    private void changeConsiderUIText( int textId, int btnId ) {
        getView().findViewById( R.id.considerResultContainerId ).setVisibility( mRequestProcessed ? View.VISIBLE : View.GONE );
        mConsiderResultBody.removeAllViews();
        initTextView( R.id.considerCombinationLabelId, AppConstants.ROTONDA_BOLD,
                SFFSApplication.getAppContext().getResources().getString( textId ) );
        initButton( R.id.considerGetInfoId, AppConstants.ROBOTO_CONDENCED )
                .setText( SFFSApplication.getAppContext().getResources().getString( btnId ) );
    }

    private void showLastFallInBallSet() {
        mShowLastFall = !mShowLastFall;
        Drawable drawable = mShowLastFall ? getActivity().getResources().getDrawable( R.drawable.ic_check_box_outline_black_18dp ) :
                getActivity().getResources().getDrawable( R.drawable.ic_checkbox_blank_outline_black_18dp );
        mLastFallInBallSet.setCompoundDrawablesWithIntrinsicBounds( drawable, null, null, null );
        GlobalManager.setShowLastFallBallSet( mShowLastFall );
        clearStoredBallSets();
        addStoredBallSets( mAllBalls );
    }

    private Ball[] getRareBalls(){
        Ball[] rareBalls = new Ball[6];
        Map<Integer,Ball> ballMap =  new TreeMap<>( Collections.reverseOrder()) ;
        for( Ball ball: GlobalManager.getBootstrapModel().getPlayedBalls() ){
            ballMap.put( ball.getDrawRange()-ball.getAvgRange(), ball );
        }
        int idx = 0;
        for( Ball ball : ballMap.values() ){
            Ball newBall = new Ball( ball.getBallNumber(), ball.getBallRepeat(), BallSetType.RARE );
            newBall.setDrawRange( ball.getDrawRange() );
            newBall.setAvgRange( ball.getAvgRange() );
            rareBalls[ idx ] = newBall;
            idx++;
            if( idx == 6 ){
                break;
            }
        }
        return rareBalls;
    }

    @Override
    public void onClick( View view ) {
        switch ( view.getId() ) {
            case R.id.considerAddToStoredId:
                addToStoredBallSet();
                break;
            case R.id.considerGetInfoId:
                if ( mRequestProcessed ) {
                    clearConsiderInfo();
                } else {
                    getConsiderInfo();
                }
                break;
            case R.id.showLastFallInBallSetId:
                showLastFallInBallSet();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBallSetItemClick( StoredBallSet ballSet, boolean addToSet ) {
        for ( Ball ball : ballSet.getBallSets() ) {
            Ball existBall = mAllBalls.get( ball.getBallNumber() );
            int existRepeat = existBall.getBallRepeat();
            if ( addToSet ) {
                existBall.setBallRepeat( existRepeat + 1 );
                if ( BallSetType.ALL.equals( existBall.getBallType() ) ) {
                    existBall.setBallType( ball.getBallType() );
                }
            } else {
                existBall.setBallRepeat( existRepeat - 1 );
                if ( existBall.getBallRepeat() == 0 ) {
                    existBall.setBallType( BallSetType.ALL );
                }
            }
        }
        mBallsInfo.getDrawBalls().clear();
        mBallsInfo.getDrawBalls().addAll( mAllBalls.values() );
        mBallTable.redrawTable( mBallsInfo );
    }

    private class FetchConsiderInfo extends AsyncTask< Void, Void, String > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground( Void... args ) {
            String result = null;
            try {
                Call< ApiResponse< ConsiderResponse > > resultCall = RestController.getApi()
                        .getConsiderInfo( AppPreferences.getUniqueId(), mConsiderRequest );
                Response< ApiResponse< ConsiderResponse > > resultResponse = resultCall.execute();
                if ( resultResponse.body() != null ) {
                    if ( resultResponse.body().getStatus() == 200 ) {
                        mConsiderResponse = resultResponse.body().getResult();
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
            populateConsiderResponse();
        }
    }
}
