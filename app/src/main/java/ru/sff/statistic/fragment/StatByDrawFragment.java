package ru.sff.statistic.fragment;


import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.github.stack07142.discreteseekbar.DiscreteSeekBar;
import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.component.ThreeCellStat;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.model.CachedRequestByDraw;
import ru.sff.statistic.utils.AppUtils;
import ru.sff.statistic.utils.CustomAnimation;

import static ru.sff.statistic.manager.GlobalManager.getCachedRequestByDraw;


public class StatByDrawFragment extends TabbedFragment {

    private static final int REQUEST_CONTAINER = - ( int ) AppUtils.convertDpToPixel( 232 );

    private static final String TAG = "StatByDrawFragment";

    private LinearLayout mStatByDrawRequestContainer;

    private ThreeCellStat mLastDrawRequest;
    private LinearLayout mRequestCountContainer;
    private DiscreteSeekBar mSelectDrawSlider;

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

        if ( getCachedRequestByDraw() != null ){
            animateRequestContainer( 0, REQUEST_CONTAINER );
            mRequestContainerShown = false;
            if ( getCachedRequestByDraw().isRequestByCount() ){
                mLastDrawCount = getCachedRequestByDraw().getEndDraw() - getCachedRequestByDraw().getStartDraw() + 1;
            } else {
                mLastDrawCountSelect = false;
                setEnableRequestFromTo( true );
                setEnableDrawCount( false );
            }
            mFromDraw = getCachedRequestByDraw().getStartDraw();
            mToDraw = getCachedRequestByDraw().getEndDraw();
            mFirstRequest = false;
            initTabs( mFromDraw, mToDraw );
        }

        mStatByDrawRequestContainer = getView().findViewById( R.id.statByDrawRequestContainerId );
        initTextView( R.id.statByDrawRequestTitleId, AppConstants.ROTONDA_BOLD );
        mLastDrawRequest = getView().findViewById( R.id.statByDrawRequestLabelId );
        mRequestCountContainer = getView().findViewById( R.id.statByDrawRequestCountId );

        setDrawCountRequestLabel();
        mSelectDrawSlider = getView().findViewById( R.id.statByDrawSliderId );
        mSelectDrawSlider.setOnTouchListener( ( View view, MotionEvent motionEvent ) -> {
            if ( MotionEvent.ACTION_MOVE == motionEvent.getAction()
                    || MotionEvent.ACTION_UP == motionEvent.getAction() ) {
                mLastDrawCount = 1 + mSelectDrawSlider.getConfigBuilder().getValue() / 4;
                setDrawCountRequestLabel();
            }
            return false;
        } );

        mRequestFromTo = getView().findViewById( R.id.statByDrawRequestFromToLabelId );
        mRequestFromToContainer = getView().findViewById( R.id.statByDrawRequestFromToId );
        mRequestFromTo.setLeftCell( "", "Тираж с " );
        mRequestFromTo.setMiddleCell( mFromDraw + "", " по " );
        mRequestFromTo.setRightCell( mToDraw + "", "" );

        mFromDrawValue = initEditText( R.id.fromDrawValueId,AppConstants.ROBOTO_CONDENCED );
        mToDrawValue = initEditText( R.id.toDrawValueId,AppConstants.ROBOTO_CONDENCED );

        setEnableRequestFromTo( false );

        setThisOnClickListener( R.id.statByDrawRequestLabelId, R.id.statByDrawRequestFromToLabelId,
                R.id.statByDrawRequestFormId, R.id.statByDrawShowFormId);
    }

    private void setDrawCountRequestLabel() {
        String drawLabel = " тиражей";
        String lastLabel = "Последних ";
        int mod = mLastDrawCount % 10;
        if ( mLastDrawCount == 1 || mLastDrawCount == 21 || mLastDrawCount == 31 || mLastDrawCount == 41 ) {
            lastLabel = "Последний ";
            drawLabel = " тираж";
        } else if ( ( mLastDrawCount < 11 || mLastDrawCount > 15 ) &&
                ( mod > 1 && mod < 5 ) ) {
            drawLabel = " тиража";
        }
        mLastDrawRequest.setLeftCell( "", lastLabel );
        mLastDrawRequest.setMiddleCell( mLastDrawCount + "", drawLabel );
    }

    private void setEnableRequestFromTo( boolean enabled ){
        if( enabled ){
            mRequestFromTo.setBackground( getActivity().getResources().getDrawable( R.drawable.border_left_yellow ) );
            mRequestFromTo.enableComponent();
            mFromDrawValue.setText( mFromDraw+"" );
            mToDrawValue.setText( mToDraw+"" );
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

    private void setEnableDrawCount( boolean enabled ){
        if( enabled ){
            mLastDrawRequest.setBackground( getActivity().getResources().getDrawable( R.drawable.border_left_yellow ) );
            mLastDrawRequest.enableComponent();
        } else {
            mLastDrawRequest.setBackground( null );
            mLastDrawRequest.disableComponent();
        }
        mSelectDrawSlider.setEnabled( enabled );
    }

    private void animateRequestContainer(int start, int end){
        LinearLayout.LayoutParams layoutParams = ( LinearLayout.LayoutParams ) mStatByDrawRequestContainer.getLayoutParams();
        if ( start > end ){
            CustomAnimation.transitionAnimation( mRequestButton, mShowRequestForm );
        } else {
            CustomAnimation.transitionAnimation( mShowRequestForm, mRequestButton );
        }
        ValueAnimator valAnimator = ValueAnimator.ofObject( new IntEvaluator(), start, end );
        valAnimator.addUpdateListener( ( ValueAnimator animator ) ->  {
            int val = ( Integer ) animator.getAnimatedValue();
            layoutParams.topMargin = val;
            mStatByDrawRequestContainer.setLayoutParams( layoutParams );
        } );
        valAnimator.setDuration( 300 );
        valAnimator.start();
    }

    private void postByDrawRequest(){
        if ( mLastDrawCountSelect ){
            mToDraw = GlobalManager.getPlayedDraws();
            mFromDraw = GlobalManager.getPlayedDraws()-( mLastDrawCount-1 );
        } else {
            String errorMsg = null;
            mFromDraw = Integer.valueOf( mFromDrawValue.getText().toString() );
            mToDraw = Integer.valueOf( mToDrawValue.getText().toString() );
            if ( mToDraw > GlobalManager.getPlayedDraws() ){
                errorMsg = "Тираж "+ mToDraw + " еше не состоялся !";
            }
            if( errorMsg == null && mFromDraw > mToDraw ){
                errorMsg = "Тираж "+mFromDraw+" , не может быть больше тиража "+ mToDraw;
            }
            if ( errorMsg != null ){
                final TextView error = initTextView( R.id.drawErrorFieldId );
                error.setText( errorMsg );
                error.setVisibility( View.VISIBLE );
                ( new Handler() ).postDelayed( () ->{
                    error.setVisibility( View.GONE );
                }, 1500 );
                return;
            }

        }
        animateRequestContainer( 0, REQUEST_CONTAINER );
        if ( GlobalManager.getCachedRequestByDraw() == null ){
            GlobalManager.setCachedRequestByDraw( new CachedRequestByDraw() );
        }
        GlobalManager.getCachedRequestByDraw().setRequestByCount( mLastDrawCountSelect );
        GlobalManager.getCachedRequestByDraw().setStartDraw( mFromDraw );
        GlobalManager.getCachedRequestByDraw().setEndDraw( mToDraw );
        GlobalManager.getCachedRequestByDraw().setBallsInfo( null );
        GlobalManager.getCachedRequestByDraw().setLotoModelDraws( null );
        if ( mFirstRequest ){
            initTabs( mFromDraw, mToDraw );
            mFirstRequest = false;
        } else {
            mDrawsPlaneFragment.getDrawsPlaneInfo().feetchDrawResults( mFromDraw, mToDraw );
            mLotoDrawsFragment.feetchLotoDraw( mFromDraw, mToDraw );
        }
        mRequestContainerShown = false;
    }

    @Override
    public void onClick( View view ) {
        switch ( view.getId() ) {
            case R.id.statByDrawRequestLabelId:
                if ( !mLastDrawCountSelect ){
                    mLastDrawCountSelect = true;
                    setEnableDrawCount( true );
                    setEnableRequestFromTo( false );
                }
                break;
            case R.id.statByDrawRequestFromToLabelId:
                if ( mLastDrawCountSelect ){
                    mLastDrawCountSelect = false;
                    setEnableRequestFromTo( true );
                    setEnableDrawCount( false );
                }
                break;
            case R.id.statByDrawRequestFormId:
                if ( mRequestContainerShown ){
                    postByDrawRequest();
                }
                break;
            case R.id.statByDrawShowFormId:
                if( !mRequestContainerShown ){
                    animateRequestContainer( REQUEST_CONTAINER , 0 );
                    mRequestContainerShown = true;
                }
                break;
        }
    }
}
