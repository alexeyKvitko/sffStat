package ru.sff.statistic.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.component.BallSetItem;
import ru.sff.statistic.component.FieldOrientation;
import ru.sff.statistic.component.FiveNineTable;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.model.Ball;
import ru.sff.statistic.model.BallSetType;
import ru.sff.statistic.model.BallsInfo;
import ru.sff.statistic.model.StoredBallSet;


public class ConsiderFragment extends BaseFragment implements BallSetItem.OnBallSetItemClickListener{

    private FiveNineTable mBallTable;

    private BallsInfo mBallsInfo;

    private Map< Integer, Ball > mAllBalls;

    public ConsiderFragment() {}


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
        initTextView( R.id.considerTableOrientationLabelId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.considerResultOnTableId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.considerBallSetLabelId, AppConstants.ROTONDA_BOLD );
        mAllBalls =  new LinkedHashMap<>( );
        for( int i = 1; i< 46; i++){
            mAllBalls.put( i, new Ball( i, 0, BallSetType.ALL ) );
        }
        addStoredBallSets( mAllBalls );
        mBallsInfo = new BallsInfo();
        mBallsInfo.setDrawBalls(  new LinkedList<>() );
        mBallsInfo.getDrawBalls().addAll( mAllBalls.values() );
        mBallTable = getView().findViewById( R.id.considerFiveNineTableId );
        mBallTable.setBallOrientation( AppConstants.HORIZONTAL_ORIENTATION );
        mBallTable.clearTable();
        mBallTable.setBallsInfo( mBallsInfo );
        FieldOrientation fieldOrientation = getView().findViewById( R.id.considerFieldOrientationId );
        fieldOrientation.setOnFieldOrientationListener( () -> {
            mBallTable.redrawTable();
        } );
        setThisOnClickListener( R.id.considerBallSetLabelId );
    }

    private void addStoredBallSets(Map< Integer, Ball > allBalls){
        if ( GlobalManager.getStoredBallSet() != null
                && GlobalManager.getStoredBallSet().size() > 0 ){
            LinearLayout ballSetContainer = getView().findViewById( R.id.considerBallSetContainerId );
            for( StoredBallSet ballSet : GlobalManager.getSortedStoredBallSet() ){
                for( Ball ball : ballSet.getBallSets() ){
                    Ball selectedBall = allBalls.get( ball.getBallNumber() );
                    selectedBall.setBallRepeat( selectedBall.getBallRepeat() + 1 );
                    selectedBall.setBallType( ball.getBallType() );
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

    @Override
    public void onClick( View view ) {
        mBallTable.getSelectedBall();
    }

    @Override
    public void onBallSetItemClick( StoredBallSet ballSet, boolean addToSet ) {
        for(Ball ball : ballSet.getBallSets() ){
            Ball existBall = mAllBalls.get( ball.getBallNumber() );
            int existRepeat = existBall.getBallRepeat();
            if( addToSet ){
                existBall.setBallRepeat(  existRepeat + 1 );
                if ( BallSetType.ALL.equals( existBall.getBallType() ) ){
                    existBall.setBallType( ball.getBallType() );
                }
            } else {
                existBall.setBallRepeat(  existRepeat - 1 );
                if( existBall.getBallRepeat() == 0 ){
                    existBall.setBallType( BallSetType.ALL );
                }
            }
        }
        mBallsInfo.getDrawBalls().clear();
        mBallsInfo.getDrawBalls().addAll( mAllBalls.values() );
        mBallTable.redrawTable( mBallsInfo );
    }
}
