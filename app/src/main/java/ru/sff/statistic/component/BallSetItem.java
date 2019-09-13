package ru.sff.statistic.component;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;
import ru.sff.statistic.model.StoredBallSet;
import ru.sff.statistic.utils.AppUtils;
import ru.sff.statistic.utils.CustomAnimation;

public class BallSetItem extends BaseComponent {

    private static final int EXPAND_HEIGHT = (int) AppUtils.convertDpToPixel( 76 );

    public OnBallSetItemClickListener mListener;

    private boolean mItemSelected;
    private ImageView mBallSetImage;
    private SixBallSet mBallSetItem;
    private StoredBallSet mStoredBallSet;

    public BallSetItem( Context context ) {
        super( context );
        inflate( context, R.layout.ball_set_item, this );
        initialize();
    }

    public BallSetItem( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.ball_set_item, this );
        initialize();
    }


    private void initialize() {
        initBaseComponent( this );
        mItemSelected = true;
        mBallSetImage = findViewById( R.id.ballSetItemImageId );
    }

    public void setSelectedBallSet( StoredBallSet ballSet ) {
        mStoredBallSet = ballSet;
        mBallSetItem = findViewById( R.id.ballSetItemId );
        mBallSetItem.setBallSet( ballSet.getBallSets(), ballSet.getBallSetType() );
        mBallSetItem.setWinBall();
        initTextView( R.id.ballSetItemKeyId, AppConstants.ROTONDA_BOLD ).setText( ballSet.getBallSetName() );
        initTextView( R.id.ballSetItemDrawId, AppConstants.ROBOTO_CONDENCED ).setText( ballSet.getDrawCount() );
        setThisOnClickListener( R.id.ballSetItemLayoutId );
    }

    @Override
    public void onClick( View view ) {
        Resources resources = SFFSApplication.getAppContext().getResources();
        mItemSelected = !mItemSelected;
        Drawable drawable = mItemSelected ? resources.getDrawable( R.drawable.ic_check_orange_18dp ) : null;
        int keyColor = mItemSelected ? resources.getColor( R.color.shokoColor )
                : resources.getColor( R.color.transpGrayTextColor );
        int drawColor = mItemSelected ? resources.getColor( R.color.ballYellow )
                : resources.getColor( R.color.transpGrayTextColor );

        mBallSetImage.setImageDrawable( drawable );
        initTextView( R.id.ballSetItemKeyId ).setTextColor( keyColor );
        initTextView( R.id.ballSetItemDrawId ).setTextColor( drawColor );
        mListener.onBallSetItemClick( mStoredBallSet, mItemSelected );
        if( mItemSelected ){
            animateRequestContainer( 0, EXPAND_HEIGHT );
        } else {
            animateRequestContainer( EXPAND_HEIGHT, 0 );
        }
    }

    private void animateRequestContainer( int start, int end ) {
        LinearLayout.LayoutParams layoutParams = ( LinearLayout.LayoutParams ) mBallSetItem.getLayoutParams();
        ValueAnimator valAnimator = ValueAnimator.ofObject( new IntEvaluator(), start, end );
        valAnimator.addUpdateListener( ( ValueAnimator animator ) -> {
            int val = ( Integer ) animator.getAnimatedValue();
            layoutParams.height = val;
            mBallSetItem.setLayoutParams( layoutParams );
        } );
        valAnimator.setDuration( 300 );
        valAnimator.start();
    }

    public void setOnBallSetItemClickListener( OnBallSetItemClickListener listener ) {
        mListener = listener;
    }

    public interface OnBallSetItemClickListener {
        public void onBallSetItemClick( StoredBallSet ballSet, boolean addToSet );
    }
}
