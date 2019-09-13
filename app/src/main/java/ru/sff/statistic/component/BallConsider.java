package ru.sff.statistic.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.Collections;
import java.util.List;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.model.ConsiderInfo;
import ru.sff.statistic.model.FourBallMagnet;
import ru.sff.statistic.model.ThreeBallMagnet;
import ru.sff.statistic.model.TwoBallMagnet;
import ru.sff.statistic.utils.AppUtils;
import ru.sff.statistic.utils.CustomAnimation;

public class BallConsider extends BaseComponent {

    private ConsiderInfo mConsiderInfo;
    private Context mContext;
    private LinearLayout mTwoBallContainer;
    private LinearLayout mThreeBallContainer;
    private LinearLayout mFourBallContainer;
    
    private boolean mTwoContainerExpand;
    private boolean mThreeContainerExpand;
    private boolean mFourContainerExpand;

    public BallConsider( Context context ) {
        super( context );
        inflate( context, R.layout.ball_consider, this );
        init( context );
    }

    public BallConsider( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.ball_consider, this );
        init( context );
    }

    private void init( Context context ) {
        initBaseComponent( this );
        mContext = context;
        mTwoBallContainer = findViewById( R.id.considerTwoBodyId );
        mThreeBallContainer = findViewById( R.id.considerThreeBodyId );
        mFourBallContainer = findViewById( R.id.considerFourBodyId );

        initTextView( R.id.considerTwoLabelId, AppConstants.ROBOTO_CONDENCED );
        initTextView( R.id.considerThreeLabelId, AppConstants.ROBOTO_CONDENCED );
        initTextView( R.id.considerFourLabelId, AppConstants.ROBOTO_CONDENCED );

        mTwoContainerExpand = false;
        mThreeContainerExpand = false;
        mFourContainerExpand = false;
        setThisOnClickListener( R.id.considerTwoShowBtnId, R.id.considerThreeShowBtnId, R.id.considerFourShowBtnId );
    }

    public void setBallConsider( ConsiderInfo digitInfo, List< Integer > ballNumbers ) {
        mConsiderInfo = digitInfo;
        initTextView( R.id.ballConsiderNumberId, AppConstants.ROTONDA_BOLD ).setText( mConsiderInfo.getBallDigit().toString() );

        TwoCellStat repeatCount = findViewById( R.id.ballConsiderRepeatId );
        String percentRepeat = ( mConsiderInfo.getRepeatCount() * 100 / GlobalManager.getPlayedDraws() ) + "";
        repeatCount.setTwoCellValue( mConsiderInfo.getRepeatCount() + "", AppUtils.getTimes( mConsiderInfo.getRepeatCount() ), percentRepeat, "%" );
        if ( mConsiderInfo.getLastFallDay() != null ) {
            initTextView( R.id.considerLastValueId, AppConstants.ROBOTO_CONDENCED ).setText( mConsiderInfo.getLastFallDay() + ", " );
        }
        initTextView( R.id.considerDateTimeValueId, AppConstants.ROBOTO_CONDENCED ).setText( mConsiderInfo.getLastFall().getDrawDate() );
//        PAIRS
        Collections.sort( mConsiderInfo.getTwoBalls() );
        BallSetWithDesc maxPairs = findViewById( R.id.considerTwoMaxId );
        maxPairs.setBallSetWithDigit( createBallSet(  mConsiderInfo.getTwoBalls().get(0).getCount(),
                mConsiderInfo.getTwoBalls().get(0).getOneBall(), mConsiderInfo.getTwoBalls().get(0).getTwoBall() ), mConsiderInfo.getBallDigit() );
        mTwoBallContainer.removeAllViews();
        for ( int i = 1; i < mConsiderInfo.getTwoBalls().size(); i++ ) {
            TwoBallMagnet twoBallMagnet = mConsiderInfo.getTwoBalls().get( i );
            if ( twoBallMagnet.getCount() > 0 ) {
                BallSetWithDesc pairs = new BallSetWithDesc( mContext );
                pairs.setBallSetWithDigit( createBallSet(  twoBallMagnet.getCount(), twoBallMagnet.getOneBall(), twoBallMagnet.getTwoBall() ), mConsiderInfo.getBallDigit() );
                mTwoBallContainer.addView( pairs );
            }
        }
        if( mTwoBallContainer.getChildCount() == 0 ){
            initTextView( R.id.considerTwoLabelId ).setVisibility( View.GONE );
        }
//        THREES
        Collections.sort( mConsiderInfo.getThreeBalls() );
        BallSetWithDesc maxThree = findViewById( R.id.considerThreeMaxId );
        maxThree.setBallSetWithDigit( createBallSet(  mConsiderInfo.getThreeBalls().get(0).getCount(),
                mConsiderInfo.getThreeBalls().get(0).getOneBall(),
                mConsiderInfo.getThreeBalls().get(0).getTwoBall(), mConsiderInfo.getThreeBalls().get(0).getThreeBall() ),
                                                                    mConsiderInfo.getBallDigit() );
        mThreeBallContainer.removeAllViews();
        for ( int i = 1; i < mConsiderInfo.getThreeBalls().size(); i++ ) {
            ThreeBallMagnet threeBallMagnet = mConsiderInfo.getThreeBalls().get(i);
            if ( threeBallMagnet.getCount() > 0 ) {
                BallSetWithDesc threes = new BallSetWithDesc( mContext );
                threes.setBallSetWithDigit( createBallSet(  threeBallMagnet.getCount(),
                        threeBallMagnet.getOneBall(), threeBallMagnet.getTwoBall(), threeBallMagnet.getThreeBall() ), mConsiderInfo.getBallDigit() );
                mThreeBallContainer.addView( threes );
            }
        }
        if( mThreeBallContainer.getChildCount() == 0 ){
            initTextView( R.id.considerThreeLabelId ).setVisibility( View.GONE );
        }
//        FOURS
        Collections.sort( mConsiderInfo.getFourBalls() );
        BallSetWithDesc maxFour = findViewById( R.id.considerFourMaxId );
        maxFour.setBallSetWithDigit( createBallSet(  mConsiderInfo.getFourBalls().get(0).getCount(),
                mConsiderInfo.getFourBalls().get(0).getOneBall(),
                mConsiderInfo.getFourBalls().get(0).getTwoBall(), mConsiderInfo.getFourBalls().get(0).getThreeBall()
                , mConsiderInfo.getFourBalls().get(0).getFourBall() ), mConsiderInfo.getBallDigit() );
        mFourBallContainer.removeAllViews();
        for ( int i = 1; i < mConsiderInfo.getFourBalls().size(); i++ ) {
            FourBallMagnet fourBallMagnet = mConsiderInfo.getFourBalls().get(i);
            if ( fourBallMagnet.getCount() > 0 ) {
                BallSetWithDesc fours = new BallSetWithDesc( mContext );
                fours.setBallSetWithDigit( createBallSet(  fourBallMagnet.getCount(),
                        fourBallMagnet.getOneBall(), fourBallMagnet.getTwoBall(), fourBallMagnet.getThreeBall(),
                        fourBallMagnet.getFourBall() ), mConsiderInfo.getBallDigit() );
                mFourBallContainer.addView( fours );
            }
        }
        if( mFourBallContainer.getChildCount() == 0 ){
            initTextView( R.id.considerFourLabelId ).setVisibility( View.GONE );
        }
    }

    private String[] createBallSet( int count, Integer... balls ){
        String[] ballSet = new String[ balls.length+1 ];
        int i;
        for( i = 0; i < balls.length; i++ ){
            ballSet[i] = balls[i]+"";
        }
        ballSet[i] = " - " + count + " " + AppUtils.getTimes( count );
        return ballSet;
    }

    @Override
    public void onClick( View view ) {
        CustomAnimation.clickAnimation( view );
        switch ( view.getId() ){
            case R.id.considerTwoShowBtnId:
                mTwoContainerExpand = !mTwoContainerExpand;
                mTwoBallContainer.setVisibility( mTwoContainerExpand ? View.VISIBLE : View.GONE );
                    break;
            case R.id.considerThreeShowBtnId:
                mThreeContainerExpand = !mThreeContainerExpand;
                mThreeBallContainer.setVisibility( mThreeContainerExpand ? View.VISIBLE : View.GONE );
                break;
            case R.id.considerFourShowBtnId:
                mFourContainerExpand = !mFourContainerExpand;
                mFourBallContainer.setVisibility( mFourContainerExpand ? View.VISIBLE : View.GONE );
                break;
        }
    }
}
