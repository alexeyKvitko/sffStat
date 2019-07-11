package ru.sff.statistic.recycleview;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.utils.AppUtils;

public class StickyRecyclerView extends RecyclerView {

    private final String TAG = "StickyRecyclerView";

    private StickyLayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mHeaderAction;
    private float mOpenMarginTop;
    private float mCloseMarginTop;
    private boolean mAnimateHeader;

    public StickyRecyclerView( Context context ) {
        super( context );
    }

    private OnActionHeaderListener mListener;

    public StickyRecyclerView( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
    }

    public StickyRecyclerView( Context context, @Nullable AttributeSet attrs, int defStyle ) {
        super( context, attrs, defStyle );
    }

    public void initialize(CommonBaseAdapter adapter,SwipeRefreshLayout layout,  int cardHeight, int open, int close){
        mSwipeRefreshLayout = layout;
        this.setOnFlingListener( null );
        mLayoutManager = new StickyLayoutManager( this.getContext(), this, cardHeight );
        mOpenMarginTop = AppUtils.convertDpToPixel( open );
        mCloseMarginTop = AppUtils.convertDpToPixel( close );
        this.setLayoutManager( mLayoutManager );
        this.setAdapter( adapter );
        this.setHasFixedSize( false );
        mHeaderAction = AppConstants.HEADER_ACTION_RESTORE;

    }

    public void removeHeaderAction(){
        mLayoutManager.setScroll( -1 );
        if( AppConstants.HEADER_ACTION_REMOVE == mHeaderAction ){
            return;
        }
        animateLayout( mOpenMarginTop, mCloseMarginTop);
        mHeaderAction = AppConstants.HEADER_ACTION_REMOVE;
        mListener.onRemoveHeaderAction();
    }

    private void animateLayout( Float start, Float end ){
        final FrameLayout.LayoutParams layoutParams = ( FrameLayout.LayoutParams ) mSwipeRefreshLayout.getLayoutParams();
        ValueAnimator valAnimator = ValueAnimator.ofObject( new IntEvaluator(), start.intValue(), end.intValue() );
        valAnimator.addUpdateListener( ( ValueAnimator animator ) ->  {
                int val = ( Integer ) animator.getAnimatedValue();
                layoutParams.topMargin = val;
            mSwipeRefreshLayout.setLayoutParams( layoutParams );
        } );
        valAnimator.setDuration( 300 );
        valAnimator.start();
    }

    public void restoreHeaderAction(){
        if( AppConstants.HEADER_ACTION_RESTORE == mHeaderAction ){
            return;
        }
        animateLayout( mCloseMarginTop, mOpenMarginTop );
        mListener.onRestoreHeaderAction();
        mHeaderAction = AppConstants.HEADER_ACTION_RESTORE;
        mLayoutManager.setScroll( 0 );
    }


    public void scrollToTop( final int position ){
        if ( AppConstants.HEADER_ACTION_RESTORE == mHeaderAction ){
            removeHeaderAction();
        }
        mLayoutManager.scrollToPosition( position );
    }

    public void setOnActionHeaderListener(OnActionHeaderListener listener){
        mListener = listener;
    }

    public interface OnActionHeaderListener{
        void onRemoveHeaderAction();
        void onRestoreHeaderAction();
    }


    public boolean isAnimateHeader() {
        return mAnimateHeader;
    }

    public void setAnimateHeader( boolean animateHeader ) {
        this.mAnimateHeader = animateHeader;
    }
}
