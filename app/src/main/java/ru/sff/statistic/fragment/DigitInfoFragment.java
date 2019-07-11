package ru.sff.statistic.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.activity.RouteActivity;
import ru.sff.statistic.component.TwoCellStat;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.model.Ball;
import ru.sff.statistic.utils.AppUtils;
import ru.sff.statistic.utils.CustomAnimation;


public class DigitInfoFragment extends BaseFragment {

    private static final String BALL_DIGIT = "ball_digit";

    private Ball mBall;

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
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_digit_info, container, false );
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
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

    @Override
    public void onClick( View view ) {

    }
}
