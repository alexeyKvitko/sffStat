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

    private static final String TAG = "StatByDrawFragment";

    private static final String START_DRAW = "start_draw";
    private static final String END_DRAW = "end_draw";

    private LinearLayout mStatByDrawRequestContainer;

    private DiscretSlider mDrawCountSlider;

    private ThreeCellStat mRequestFromTo;
    private LinearLayout mRequestFromToContainer;
    private AppCompatEditText mFromDrawValue;
    private AppCompatEditText mToDrawValue;
    private TextView mFromToTitle;

    private ImageView mShowRequestForm;
    private Button mRequestButton;

    private int mLastDrawCount;
    private int mFromDraw;
    private int mToDraw;

    private boolean mBySliderRequest;
    private boolean mRequestContainerShown;
    private boolean mInitFromArg;

    public StatByDrawFragment() {
    }

    public static StatByDrawFragment newInstance( Integer startDraw, Integer endDraw ) {
        StatByDrawFragment fragment = new StatByDrawFragment();
        Bundle args = new Bundle();
        args.putInt( START_DRAW, startDraw );
        args.putInt( END_DRAW, endDraw );
        fragment.setArguments( args );
        return fragment;
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
        mInitFromArg = false;
        mLastDrawCount = 10;
        mToDraw = GlobalManager.getPlayedDraws();
        mFromDraw = GlobalManager.getPlayedDraws() - 9;
        mBySliderRequest = true;
        mRequestContainerShown = true;

        mShowRequestForm = getView().findViewById( R.id.statByDrawShowFormId );
        mShowRequestForm.setVisibility( View.GONE );
        mRequestButton = getView().findViewById( R.id.statByDrawRequestFormId );
        mRequestButton.setVisibility( View.VISIBLE );

        mStatByDrawRequestContainer = getView().findViewById( R.id.statByDrawRequestContainerId );
        mDrawCountSlider = getView().findViewById( R.id.drawCountSliderId );
        mDrawCountSlider.initSlider( 1, 45, mLastDrawCount, getView().getResources()
                .getString( R.string.show_result_by_draw ), DiscretSlider.DISCRET_SLIDER_DRAW );

        mFromToTitle = initTextView( R.id.fromToDrawTitleId, AppConstants.ROTONDA_BOLD );
        mRequestFromTo = getView().findViewById( R.id.statByDrawRequestFromToLabelId );
        mRequestFromToContainer = getView().findViewById( R.id.statByDrawRequestFromToId );
        mRequestFromTo.setLeftCell( "", "Тираж с " );
        mRequestFromTo.setMiddleCell( mFromDraw + "", " по " );
        mRequestFromTo.setRightCell( mToDraw + "", "" );
        mFromDrawValue = initEditText( R.id.fromDrawValueId, AppConstants.ROBOTO_CONDENCED );
        mToDrawValue = initEditText( R.id.toDrawValueId, AppConstants.ROBOTO_CONDENCED );
        setEnableRequestFromTo( false );
        if ( getCachedResponseData() != null
                && RequestType.isDrawRequest( getCachedResponseData().getLastRequest() )
                && RequestType.DRAW_BETWEEN.equals( getCachedResponseData().getLastRequest() ) ) {
            mMenuHeight = GlobalManager.getInstance().getLastMenuHeight();
            if ( getCachedResponseData().getRequestByDraw().isBySlider()  ) {
                mBySliderRequest = true;
                setEnableRequestFromTo( false );
                mDrawCountSlider.setEnableSlider( true );
            } else {
                mBySliderRequest = false;
//                mMenuHeight = mMenuHeight + ( int ) AppUtils.convertDpToPixel( 72 );
                setEnableRequestFromTo( true );
                mDrawCountSlider.setEnableSlider( false );
            }
            mRequestContainerShown = false;
            mFromDraw = getCachedResponseData().getRequestByDraw().getStartDraw();
            mToDraw = getCachedResponseData().getRequestByDraw().getEndDraw();
            mLastDrawCount = mToDraw - mFromDraw + 1;
            animateRequestContainer( 0, mMenuHeight );
            initTabs();
        }
        if ( getArguments() != null && AppConstants.FAKE_ID != getArguments().getInt( START_DRAW ) ) {
            mInitFromArg = true;
            setEnableRequestFromTo( true );
            mDrawCountSlider.setEnableSlider( false );
            mBySliderRequest = false;
            int startDraw = getArguments().getInt( START_DRAW );
            int endDraw = getArguments().getInt( END_DRAW );
            mFromDrawValue.setText( startDraw + "" );
            mToDrawValue.setText( endDraw + "" );
            if ( GlobalManager.getCachedResponseData() != null &&
                    RequestType.DRAW_BETWEEN.equals( GlobalManager.getCachedResponseData().getLastRequest() ) &&
                    GlobalManager.getCachedResponseData().getRequestByDraw().getStartDraw() == startDraw &&
                    GlobalManager.getCachedResponseData().getRequestByDraw().getEndDraw() == endDraw ) {
                initTabs();
            } else {
                postByDrawRequest();
            }
        }
        setThisOnClickListener( R.id.drawCountSliderId, R.id.fromToDrawTitleId,
                R.id.statByDrawRequestFormId, R.id.statByDrawShowFormId );
    }


    private void setEnableRequestFromTo( boolean enabled ) {
        int visible = enabled ? View.VISIBLE : View.GONE;
        getView().findViewById( R.id.statByDrawRequestFromToId ).setVisibility( visible );
        if ( enabled ) {
            mFromToTitle.setBackground( getActivity().getResources().getDrawable( R.drawable.border_left_yellow ) );
            mFromToTitle.setTextColor( getActivity().getResources().getColor( R.color.shokoColor ) );
            mRequestFromTo.enableComponent();
            mFromDrawValue.setText( mFromDraw + "" );
            mToDrawValue.setText( mToDraw + "" );
        } else {
            AppUtils.hideKeyboardFrom( getActivity(), mRequestFromTo );
            mRequestFromTo.disableComponent();
            mFromDrawValue.setText( null );
            mFromDrawValue.clearFocus();
            mToDrawValue.setText( null );
            mToDrawValue.clearFocus();
            mFromToTitle.setBackground( null );
            mFromToTitle.setTextColor( getActivity().getResources().getColor( R.color.transpGrayTextColor ) );
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
        boolean bySlider;
        if ( mBySliderRequest ) {
            mLastDrawCount = mDrawCountSlider.getSliderValue();
            mToDraw = GlobalManager.getPlayedDraws();
            mFromDraw = GlobalManager.getPlayedDraws() - ( mLastDrawCount - 1 );
            bySlider = true;
        } else {
            String errorMsg = null;
            mFromDraw = Integer.valueOf( mFromDrawValue.getText().toString() );
            mToDraw = Integer.valueOf( mToDrawValue.getText().toString() );
            bySlider = false;
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
        mMenuHeight = mStatByDrawRequestContainer.getMeasuredHeight() == 0 ? -( int ) AppUtils.convertDpToPixel( 180 ) :
                -( mStatByDrawRequestContainer.getMeasuredHeight() - ( int ) AppUtils.convertDpToPixel( 38 ) );
        GlobalManager.getInstance().setLastMenuHeight( mMenuHeight );
        animateRequestContainer( 0, mMenuHeight );
        if ( GlobalManager.getCachedResponseData() == null ) {
            GlobalManager.setCachedResponseData( new CachedResponseData() );
        }
        AppUtils.hideKeyboardFrom( getActivity(), mRequestFromTo );
        mRequestByDate = null;
        RequestByDraw requestByDraw = new RequestByDraw();
        requestByDraw.setRequestDrawType( RequestType.DRAW_BETWEEN );
        requestByDraw.setStartDraw( mFromDraw );
        requestByDraw.setEndDraw( mToDraw );
        requestByDraw.setBySlider( bySlider );
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
                if ( !mBySliderRequest ) {
                    mBySliderRequest = true;
                    mDrawCountSlider.setEnableSlider( true );
                    setEnableRequestFromTo( false );
                }
                break;
            case R.id.fromToDrawTitleId:
                if ( mBySliderRequest ) {
                    mBySliderRequest = false;
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
                    animateRequestContainer( mMenuHeight, 0 );
                    mRequestContainerShown = true;
                }
                break;
        }
    }

}
