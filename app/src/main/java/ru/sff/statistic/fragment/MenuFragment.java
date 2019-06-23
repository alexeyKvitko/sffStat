package ru.sff.statistic.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.component.BallField;
import ru.sff.statistic.component.ColorBall;
import ru.sff.statistic.component.FiveNineTable;
import ru.sff.statistic.model.Ball;


public class MenuFragment extends BaseFragment {

    private OnMenuOptionSelectListener mListener;

    public MenuFragment() {}

    public static MenuFragment newInstance( ) {
        MenuFragment fragment = new MenuFragment();
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
          return inflater.inflate( R.layout.fragment_menu, container, false );
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        List<Ball> ballList = new LinkedList<>();
        for ( int i=1; i<46;i++){
            Ball ball = new Ball(i+"",(967-i)+"");
            if ( i == 1 || i ==6 || i == 17 || i == 28 || i == 37 || i== 45 ){
                ball.setBigger( true );
            }
            ballList.add( ball );
        }
        FiveNineTable table = getView().findViewById( R.id.fiveNineTableId );
        table.fillFiveNineTable( ballList );
        ((ColorBall) getView().findViewById( R.id.colorBallOneId ))
                .setColorBall( ballList.get(0), AppConstants.CYAN_BALL );
        ((ColorBall) getView().findViewById( R.id.colorBallTwoId ))
                .setColorBall( ballList.get(5), AppConstants.RED_BALL );
        ((ColorBall) getView().findViewById( R.id.colorBallThreeId ))
                .setColorBall( ballList.get(16), AppConstants.GREEN_BALL );
        ((ColorBall) getView().findViewById( R.id.colorBallFourId ))
                .setColorBall( ballList.get(27), AppConstants.ORANGE_BALL );
        ((ColorBall) getView().findViewById( R.id.colorBallFiveId ))
                .setColorBall( ballList.get(36), AppConstants.BLUE_BALL );
        ((ColorBall) getView().findViewById( R.id.colorBallSixId ))
                .setColorBall( ballList.get(44), AppConstants.VIALET_BALL );



    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed( ) {
        if ( mListener != null ) {
            mListener.onMainStatisticSelect(  );
        }
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
}
