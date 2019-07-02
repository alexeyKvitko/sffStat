package ru.sff.statistic.fragment;


import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.utils.AppUtils;

import static ru.sff.statistic.manager.GlobalManager.getStoredBallSet;


public class BallSetBasketFragment extends BaseFragment {

    public static final int BASKET_IMAGE_WIDTH = ( int ) AppUtils.convertDpToPixel( 66 );
    public static final int BASKET_IMAGE_HEIGHT = ( int ) AppUtils.convertDpToPixel( 66 );

    public static final int BASKET_HEADER_HEIGHT = ( int ) AppUtils.convertDpToPixel( 56 );
    public static final int BASKET_ROW_HEIGHT = ( int ) AppUtils.convertDpToPixel( 112 );
    public static final int ANIMATION_DUARATION = 300;

    private int mBasketHeight;

    public BallSetBasketFragment() {}

    public static BallSetBasketFragment newInstance( ) {
        BallSetBasketFragment fragment = new BallSetBasketFragment();
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_ball_set_basket, container, false );
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
//        getView().findViewById( R.id.basketFragmentId ).setBackground( Drawable.createFromPath( AppUtils.getSnapshotPath() ) );
        int basketHeight = getStoredBallSet().size() > 3 ? 3 : getStoredBallSet().size();
        mBasketHeight = BASKET_HEADER_HEIGHT + BASKET_ROW_HEIGHT * basketHeight;
        animateBasketBody( 0, mBasketHeight );
        initTextView( R.id.basketTitleId, AppConstants.ROTONDA_BOLD );
    }

    private void animateBasketBody( int start, int end ) {
        Animation fade = AnimationUtils.loadAnimation( getActivity(), R.anim.fade_in );
        if ( mBasketHeight == start ) {
            fade = AnimationUtils.loadAnimation( getActivity(), R.anim.fade_out );
        }
        getView().findViewById( R.id.shadowBasketLayoutId ).startAnimation( fade );
        final View basketBody = getView().findViewById( R.id.basketBodyId );
        final FrameLayout.LayoutParams layoutParams = ( FrameLayout.LayoutParams ) basketBody.getLayoutParams();
        ValueAnimator valAnimator = ValueAnimator.ofObject( new IntEvaluator(), start, end );
        valAnimator.addUpdateListener( ( ValueAnimator animator ) -> {
            int val = ( ( Integer ) animator.getAnimatedValue() );
            layoutParams.height = val;
            basketBody.setLayoutParams( layoutParams );
        } );
        valAnimator.setDuration( ANIMATION_DUARATION );
        valAnimator.start();
    }

    @Override
    public void onClick( View view ) {

    }
}
