package ru.sff.statistic.fragment;


import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.activity.RouteActivity;
import ru.sff.statistic.component.DiscretSlider;
import ru.sff.statistic.component.ThreeCellStat;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.model.CachedResponseData;
import ru.sff.statistic.model.HeaderModel;
import ru.sff.statistic.model.RequestByDraw;
import ru.sff.statistic.model.RequestType;
import ru.sff.statistic.utils.AppUtils;
import ru.sff.statistic.utils.CustomAnimation;

import static ru.sff.statistic.manager.GlobalManager.getCachedResponseData;


public class StatByDrawFragment extends TabbedFragment {

    private static final int REQUEST_CONTAINER_COLLAPSE_HEIGHT = -( int ) AppUtils.convertDpToPixel( 232 );

    private static final String TAG = "StatByDrawFragment";

    private LinearLayout mStatByDrawRequestContainer;

    private DiscretSlider mDrawCountSlider;

    private ThreeCellStat mRequestFromTo;
    private LinearLayout mRequestFromToContainer;
    private AppCompatEditText mFromDrawValue;
    private AppCompatEditText mToDrawValue;

    private ImageView mShowRequestForm;
    private Button mRequestButton;

    private int mLastDrawCount;
    private int mFromDraw;
    private int mToDraw;

    private boolean mLastDrawCountSelect;
    private boolean mRequestContainerShown;
    private boolean mFirstRequest;

    public StatByDrawFragment() {
    }

    public static StatByDrawFragment newInstance() {
        return new StatByDrawFragment();
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        return inflater.inflate( R.layout.fragment_stat_by_draw, container, false );
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        mLastDrawCount = 10;
        mToDraw = GlobalManager.getPlayedDraws();
        mFromDraw = GlobalManager.getPlayedDraws() - 9;

        mLastDrawCountSelect = true;
        mRequestContainerShown = true;
        mFirstRequest = true;

        mShowRequestForm = getView().findViewById( R.id.statByDrawShowFormId );
        mShowRequestForm.setVisibility( View.GONE );
        mRequestButton = getView().findViewById( R.id.statByDrawRequestFormId );
        mRequestButton.setVisibility( View.VISIBLE );

        mStatByDrawRequestContainer = getView().findViewById( R.id.statByDrawRequestContainerId );
        mDrawCountSlider = getView().findViewById( R.id.drawCountSliderId );
        mDrawCountSlider.initSlider( 1, 45, mLastDrawCount, getView().getResources()
                .getString( R.string.show_result_by_draw ), DiscretSlider.DISCRET_SLIDER_DRAW );

        mRequestFromTo = getView().findViewById( R.id.statByDrawRequestFromToLabelId );
        mRequestFromToContainer = getView().findViewById( R.id.statByDrawRequestFromToId );
        mRequestFromTo.setLeftCell( "", "Тираж с " );
        mRequestFromTo.setMiddleCell( mFromDraw + "", " по " );
        mRequestFromTo.setRightCell( mToDraw + "", "" );
        mFromDrawValue = initEditText( R.id.fromDrawValueId, AppConstants.ROBOTO_CONDENCED );
        mToDrawValue = initEditText( R.id.toDrawValueId, AppConstants.ROBOTO_CONDENCED );
        if ( getCachedResponseData() != null
                && RequestType.DRAW_BETWEEN.equals( getCachedResponseData().getLastRequest() ) ) {
            animateRequestContainer( 0, REQUEST_CONTAINER_COLLAPSE_HEIGHT );
            mRequestContainerShown = false;
            mFromDraw = getCachedResponseData().getRequestByDraw().getStartDraw();
            mToDraw = getCachedResponseData().getRequestByDraw().getEndDraw();
            mLastDrawCount = mToDraw - mFromDraw + 1;
            mFirstRequest = false;
            initTabs();
        } else {
            mLastDrawCountSelect = true;
            setEnableRequestFromTo( false );
            mDrawCountSlider.setEnableSlider( true );
        }

        setEnableRequestFromTo( false );

        setThisOnClickListener( R.id.drawCountSliderId, R.id.statByDrawRequestFromToId,
                R.id.statByDrawRequestFormId, R.id.statByDrawShowFormId );
    }


    private void setEnableRequestFromTo( boolean enabled ) {
        if ( enabled ) {
            mRequestFromTo.setBackground( getActivity().getResources().getDrawable( R.drawable.border_left_yellow ) );
            mRequestFromTo.enableComponent();
            mFromDrawValue.setText( mFromDraw + "" );
            mToDrawValue.setText( mToDraw + "" );
        } else {
            AppUtils.hideKeyboardFrom( getActivity(), mRequestFromTo );
            mRequestFromTo.setBackground( null );
            mRequestFromTo.disableComponent();
            mFromDrawValue.setText( null );
            mFromDrawValue.clearFocus();
            mToDrawValue.setText( null );
            mToDrawValue.clearFocus();
        }
        mFromDrawValue.setEnabled( enabled );
        mToDrawValue.setEnabled( enabled );
    }


    private void animateRequestContainer( int start, int end ) {
        LinearLayout.LayoutParams layoutParams = ( LinearLayout.LayoutParams ) mStatByDrawRequestContainer.getLayoutParams();
        if ( start > end ) {
            CustomAnimation.transitionAnimation( mRequestButton, mShowRequestForm );
        } else {
            CustomAnimation.transitionAnimation( mShowRequestForm, mRequestButton );
        }
        ValueAnimator valAnimator = ValueAnimator.ofObject( new IntEvaluator(), start, end );
        valAnimator.addUpdateListener( ( ValueAnimator animator ) -> {
            int val = ( Integer ) animator.getAnimatedValue();
            layoutParams.topMargin = val;
            mStatByDrawRequestContainer.setLayoutParams( layoutParams );
        } );
        valAnimator.setDuration( 300 );
        valAnimator.start();
    }

    private void postByDrawRequest() {
        if ( mLastDrawCountSelect ) {
            mLastDrawCount = mDrawCountSlider.getSliderValue();
            mToDraw = GlobalManager.getPlayedDraws();
            mFromDraw = GlobalManager.getPlayedDraws() - ( mLastDrawCount - 1 );
        } else {
            String errorMsg = null;
            mFromDraw = Integer.valueOf( mFromDrawValue.getText().toString() );
            mToDraw = Integer.valueOf( mToDrawValue.getText().toString() );
            if ( mToDraw > GlobalManager.getPlayedDraws() ) {
                errorMsg = "Тираж " + mToDraw + " еше не состоялся !";
            }
            if ( errorMsg == null && mFromDraw > mToDraw ) {
                errorMsg = "Тираж " + mFromDraw + " , не может быть больше тиража " + mToDraw;
            }
            if ( errorMsg != null ) {
                final TextView error = initTextView( R.id.drawErrorFieldId );
                error.setText( errorMsg );
                error.setVisibility( View.VISIBLE );
                ( new Handler() ).postDelayed( () -> {
                    error.setVisibility( View.GONE );
                }, 1500 );
                return;
            }

        }
        animateRequestContainer( 0, REQUEST_CONTAINER_COLLAPSE_HEIGHT );
        if ( GlobalManager.getCachedResponseData() == null ) {
            GlobalManager.setCachedResponseData( new CachedResponseData() );
        }
        AppUtils.hideKeyboardFrom( getActivity(), mRequestFromTo );
        mRequestByDate = null;
        RequestByDraw requestByDraw = new RequestByDraw();
        requestByDraw.setRequestDrawType( RequestType.DRAW_BETWEEN );
        requestByDraw.setStartDraw( mFromDraw );
        requestByDraw.setEndDraw( mToDraw );
        GlobalManager.getCachedResponseData().setLastRequest( RequestType.DRAW_BETWEEN );
        GlobalManager.getCachedResponseData().setRequestByDate( null );
        GlobalManager.getCachedResponseData().setRequestByDraw( requestByDraw );
        GlobalManager.getCachedResponseData().setBallsInfo( null );
        GlobalManager.getCachedResponseData().setLotoModelDraws( null );
        fetchDrawData( requestByDraw );
        mRequestContainerShown = false;
        int byDraw = 1 + ( mToDraw - mFromDraw );
        ( ( RouteActivity ) getActivity() ).getAppHeader().setHeader( new HeaderModel( R.drawable.emoji_look, "по " + byDraw + " тиражам" ) );
    }

    @Override
    public void onClick( View view ) {
        switch ( view.getId() ) {
            case R.id.drawCountSliderId:
                if ( !mLastDrawCountSelect ) {
                    mLastDrawCountSelect = true;
                    mDrawCountSlider.setEnableSlider( true );
                    setEnableRequestFromTo( false );
                }
                break;
            case R.id.statByDrawRequestFromToId:
                if ( mLastDrawCountSelect ) {
                    mLastDrawCountSelect = false;
                    setEnableRequestFromTo( true );
                    mDrawCountSlider.setEnableSlider( false );
                }
                break;
            case R.id.statByDrawRequestFormId:
                if ( mRequestContainerShown ) {
                    postByDrawRequest();
                }
                break;
            case R.id.statByDrawShowFormId:
                if ( !mRequestContainerShown ) {
                    animateRequestContainer( REQUEST_CONTAINER_COLLAPSE_HEIGHT, 0 );
                    mRequestContainerShown = true;
                }
                break;
        }
    }

}
