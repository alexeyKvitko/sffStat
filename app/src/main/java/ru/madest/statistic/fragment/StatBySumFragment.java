package ru.madest.statistic.fragment;


import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import ru.madest.statistic.R;
import ru.madest.statistic.activity.RouteActivity;
import ru.madest.statistic.component.RangeSlider;
import ru.madest.statistic.manager.GlobalManager;
import ru.madest.statistic.model.CachedResponseData;
import ru.madest.statistic.model.HeaderModel;
import ru.madest.statistic.model.RequestBySumOrBall;
import ru.madest.statistic.model.RequestType;
import ru.madest.statistic.utils.AppUtils;
import ru.madest.statistic.utils.CustomAnimation;

import static ru.madest.statistic.manager.GlobalManager.getCachedResponseData;


public class StatBySumFragment extends TabbedFragment {


    private RangeSlider mSumSlider;
    private RangeSlider mBallSlider;

    private LinearLayout mRequestContainer;

    private ImageView mShowRequestForm;
    private Button mRequestButton;

    private RequestType mSelectedRequest;

    private boolean mRequestContainerShown;

    public StatBySumFragment() {}

    public static StatBySumFragment newInstance() {
        StatBySumFragment fragment = new StatBySumFragment();
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
        return inflater.inflate( R.layout.fragment_stat_by_sum, container, false );
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        mSelectedRequest = RequestType.BY_SUM;
        mRequestContainerShown = true;

        mShowRequestForm = getView().findViewById( R.id.statBySumShowFormId );
        mShowRequestForm.setVisibility( View.GONE );
        mRequestButton = getView().findViewById( R.id.statBySumRequestFormId );
        mRequestButton.setVisibility( View.VISIBLE );
        mRequestContainer = getView().findViewById( R.id.statBySumRequestContainerId );

        mSumSlider = getView().findViewById( R.id.sumSliderId );
        mBallSlider = getView().findViewById( R.id.ballSliderId );
        boolean sumEnabled = true;
        boolean ballEnabled = false;

        int beginSum = 21;
        int endSum = 255;
        int beginBall = 1;
        int endBall = 45;
        if ( getCachedResponseData() != null
                && RequestType.isSumRequest( getCachedResponseData().getLastRequest() ) ){
            mSelectedRequest = getCachedResponseData().getLastRequest();
            String header = "";
            int begin = getCachedResponseData().getRequestBySOB().getBegin();
            int end = getCachedResponseData().getRequestBySOB().getEnd();
            GlobalManager.getInstance().getLastMenuHeight();
            animateRequestContainer( 0, mMenuHeight );
            if ( RequestType.BY_SUM.equals( mSelectedRequest ) ){
                header = "Сумма номеров с "+begin+" по "+ end;
                beginSum = begin;
                endSum = end;
                sumEnabled = true;
                ballEnabled = false;
            } else {
                header = "Номера с "+begin+" по "+ end;
                mBallSlider.setEnableSlider( true );
                mSumSlider.setEnableSlider( false );
                beginBall = begin;
                endBall = end;
                sumEnabled = false;
                ballEnabled = true;
            }
            ( ( RouteActivity ) getActivity() ).getAppHeader().setHeader( new HeaderModel( R.drawable.emoji_look, header ) );
            initTabs();
        }
        mSumSlider.initSlider( beginSum, endSum, 21, 1, getActivity().getResources().getString( R.string.by_sum_label ),
                RangeSlider.RANGE_SLIDER_SUM );


        mBallSlider.initSlider( beginBall, endBall, 1, 5, getActivity().getResources().getString( R.string.by_ball_label ),
                RangeSlider.RANGE_SLIDER_BALL );
        mSumSlider.setEnableSlider( sumEnabled );
        mBallSlider.setEnableSlider( ballEnabled );
        this.setThisOnClickListener( R.id.sumSliderId, R.id.ballSliderId,
                                        R.id.statBySumShowFormId, R.id.statBySumRequestFormId );
    }

    private void animateRequestContainer( int start, int end ) {
        LinearLayout.LayoutParams layoutParams = ( LinearLayout.LayoutParams ) mRequestContainer.getLayoutParams();
        if ( start > end ) {
            CustomAnimation.transitionAnimation( mRequestButton, mShowRequestForm );
        } else {
            CustomAnimation.transitionAnimation( mShowRequestForm, mRequestButton );
        }
        ValueAnimator valAnimator = ValueAnimator.ofObject( new IntEvaluator(), start, end );
        valAnimator.addUpdateListener( ( ValueAnimator animator ) -> {
            int val = ( Integer ) animator.getAnimatedValue();
            layoutParams.topMargin = val;
            mRequestContainer.setLayoutParams( layoutParams );
        } );
        valAnimator.setDuration( 300 );
        valAnimator.start();
    }

    private void postBySumRequest(){
        mMenuHeight = -(mRequestContainer.getMeasuredHeight()- (int) AppUtils.convertDpToPixel( 38 ));
        GlobalManager.getInstance().setLastMenuHeight( mMenuHeight );
        animateRequestContainer( 0, mMenuHeight );
        if ( getCachedResponseData() == null ) {
            GlobalManager.setCachedResponseData( new CachedResponseData() );
        }
        RequestBySumOrBall request = new RequestBySumOrBall();
        request.setRequestType( mSelectedRequest );
        request.setBegin( RequestType.BY_SUM.equals( mSelectedRequest ) ? mSumSlider.getSliderMinValue() : mBallSlider.getSliderMinValue() );
        request.setEnd( RequestType.BY_SUM.equals( mSelectedRequest ) ? mSumSlider.getSliderMaxValue() : mBallSlider.getSliderMaxValue() );
        getCachedResponseData().setLastRequest( mSelectedRequest );
        getCachedResponseData().setRequestByDate( null );
        getCachedResponseData().setRequestByDraw( null );
        getCachedResponseData().setRequestBySOB( request );
        getCachedResponseData().setLotoModelDraws( null );
        fetchSOBData( request );
        String header = RequestType.BY_SUM.equals( mSelectedRequest ) ? "Сумма номеров с "+request.getBegin()+" по "+ request.getEnd() :
                                                                            "Номера с "+request.getBegin()+" по "+ request.getEnd();
        ( ( RouteActivity ) getActivity() ).getAppHeader().setHeader( new HeaderModel( R.drawable.emoji_look, header ) );
    }

    @Override
    public void onClick( View view ) {
        switch ( view.getId() ) {
            case R.id.sumSliderId:
                    if( !RequestType.BY_SUM.equals(  mSelectedRequest ) ){
                        mSelectedRequest = RequestType.BY_SUM;
                        mBallSlider.setEnableSlider( false );
                        mSumSlider.setEnableSlider( true );
                    }
                break;
            case R.id.ballSliderId:
                if( !RequestType.BALL_BETWEEN.equals(  mSelectedRequest ) ){
                    mSelectedRequest = RequestType.BALL_BETWEEN;
                    mBallSlider.setEnableSlider( true );
                    mSumSlider.setEnableSlider( false );
                }
                break;
            case R.id.statBySumRequestFormId:
                if ( mRequestContainerShown ) {
                    postBySumRequest();
                    mRequestContainerShown = false;
                }
                break;
            case R.id.statBySumShowFormId:
                if ( !mRequestContainerShown ) {
                    animateRequestContainer( mMenuHeight, 0 );
                    mRequestContainerShown = true;
                }
                break;
        }
    }
}
