package ru.sff.statistic.component;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.model.Ball;
import ru.sff.statistic.model.BallSetType;
import ru.sff.statistic.model.BallsInfo;
import ru.sff.statistic.model.RequestType;
import ru.sff.statistic.model.StoredBallSet;
import ru.sff.statistic.utils.AppUtils;
import ru.sff.statistic.utils.CustomAnimation;

public class DrawsPlaneInfo extends BaseComponent {

    private static final String TAG = "DrawsPlaneInfo";

    private BallsInfo mBallsInfo;


    private TwoCellStat mDrawRange;
    private TwoCellStat mTotalTicket;
    private TwoCellStat mTotalPaidAmount;
    private TwoCellStat mEvenOdd;

    private SixBallSet mBiggerBallSet;
    private SixBallSet mLessBallSet;
    private SixBallSet mMiddleBallSet;

    private boolean mBiggerShow;
    private boolean mLessShow;
    private boolean mMiddleShow;

    private Activity mActivity;
    private RelativeLayout mPleaseWait;
    private ScrollView mScrollView;

    private TextView mTitle;


    private FiveNineTable mBallTable;

    public DrawsPlaneInfo( Context context ) {
        super( context );
        inflate(context, R.layout.draws_plane_info, this);
        initialize();
    }

    public DrawsPlaneInfo( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate(context, R.layout.draws_plane_info, this);
        initialize();
    }

    public void setActivity( Activity activity, RelativeLayout layout, ScrollView scrollView ){
        mActivity = activity;
        mPleaseWait = layout;
        mScrollView = scrollView;
    }

    private void initialize() {
        initBaseComponent( this );

        mBiggerShow = true;
        mLessShow = true;
        mMiddleShow = true;
        mDrawRange = findViewById( R.id.twoCellDrawRangeId );
        mTotalTicket = findViewById( R.id.twoCellTotalTicketId );
        mTotalPaidAmount = findViewById( R.id.twoCellPaidAmountId );
        mEvenOdd = findViewById( R.id.twoCellEvenOddId );

        mTitle = initTextView( R.id.drawTitleId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.oftenDigitId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.lessDigitId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.middleDigitId,AppConstants.ROTONDA_BOLD );
        initTextView( R.id.resultOnTableId,AppConstants.ROTONDA_BOLD );
        initTextView( R.id.avgSumLabelId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.totalTicketLabelId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.totalPaidAmountLabelId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.drawResultsLabelId, AppConstants.ROTONDA_BOLD );
        initTextView( R.id.evenOddLabelId, AppConstants.ROTONDA_BOLD );

        initTextView( R.id.tableOrientationId, AppConstants.ROBOTO_CONDENCED );
        initTextView( R.id.fallingNumbersLabelId, AppConstants.ROBOTO_CONDENCED );
        initTextView( R.id.biggerButtonId, AppConstants.ROBOTO_CONDENCED );
        initTextView( R.id.lessButtonId, AppConstants.ROBOTO_CONDENCED );
        initTextView( R.id.middleButtonId, AppConstants.ROBOTO_CONDENCED );

        FieldOrientation fieldOrientation = findViewById( R.id.fieldOrientationId );
        fieldOrientation.setOnFieldOrientationListener( () -> {
            mBallTable.redrawTable();
        } );

        setThisOnClickListener( R.id.biggerButtonId, R.id.lessButtonId, R.id.middleButtonId,
                R.id.addToBasketBiggerAllId,R.id.addToBasketLessAllId,R.id.addToBasketMiddleAllId );
        initBasketImage();
    }

    private void initBasketImage(){
        restoreToDefaultBasketImage();
        if ( GlobalManager.getStoredBallSet().size() > 0 ){
            Drawable basketSelect = SFFSApplication.getAppContext().getResources()
                                    .getDrawable( R.drawable.ic_basket_shoko_18dp );
            for(String key : GlobalManager.getStoredBallSet().keySet() ){
                int imageId = AppConstants.FAKE_ID;
                if ( AppUtils.getStoredBallSetKey( AppConstants.BALL_SET_BIGGER ).equals( key ) ){
                    imageId = R.id.basketBiggerAllImageId;
                } else if ( AppUtils.getStoredBallSetKey( AppConstants.BALL_SET_LESS ).equals( key ) ){
                    imageId = R.id.basketLessAllImageId;
                } else if ( AppUtils.getStoredBallSetKey( AppConstants.BALL_SET_MIDDLE ).equals( key ) ){
                    imageId = R.id.basketMiddleAllImageId;
                }
                if ( AppConstants.FAKE_ID != imageId ){
                    ((ImageView) findViewById( imageId )).setImageDrawable( basketSelect );
                }
            }
        }
    }


    public void changeViewType(){
        mBiggerBallSet.redrawBalls();
        mLessBallSet.redrawBalls();
        mMiddleBallSet.redrawBalls();
        mBallTable.redrawTable();
    }

    @Override
    public void onClick( View view ) {
        CustomAnimation.bounceAnimation( view );

        switch ( view.getId() ) {
            case R.id.biggerButtonId:
                mBiggerShow = !mBiggerShow;
                setBallSetButton( view.getId(), mBiggerShow );
                break;
            case R.id.lessButtonId:
                mLessShow = !mLessShow;
                setBallSetButton( view.getId(), mLessShow );
                break;
            case R.id.middleButtonId:
                mMiddleShow = !mMiddleShow;
                setBallSetButton( view.getId(), mMiddleShow );
                break;
            case R.id.addToBasketBiggerAllId:
                CustomAnimation.clickAnimation( view );
                addBallSetToBasket( R.id.addToBasketBiggerAllId, AppUtils.getStoredBallSetKey( AppConstants.BALL_SET_BIGGER ),
                        mBallsInfo.getMoreOften(), BallSetType.BIGGER );
                break;
            case R.id.addToBasketLessAllId:
                CustomAnimation.clickAnimation( view );
                addBallSetToBasket( R.id.addToBasketLessAllId,AppUtils.getStoredBallSetKey( AppConstants.BALL_SET_LESS )
                                                , mBallsInfo.getLessOfter(), BallSetType.LESS );
                break;
            case R.id.addToBasketMiddleAllId:
                CustomAnimation.clickAnimation( view );
                addBallSetToBasket( R.id.addToBasketMiddleAllId,AppUtils.getStoredBallSetKey( AppConstants.BALL_SET_MIDDLE )
                                    , mBallsInfo.getMiddleOften(), BallSetType.MIDDLE );
                break;
        }
        List<BallSetType> ballSetTypeList = new LinkedList<>(  );
        if( mBiggerShow ){
            ballSetTypeList.add( BallSetType.BIGGER );
        }
        if( mLessShow ){
            ballSetTypeList.add( BallSetType.LESS );
        }
        if( mMiddleShow ){
            ballSetTypeList.add( BallSetType.MIDDLE );
        }
        GlobalManager.getInstance().setBallSetTypes( ballSetTypeList.toArray( new BallSetType[ ballSetTypeList.size() ] ) );
        mBallTable.redrawTable();
    }

    private void addBallSetToBasket( int basketId, String basketName, Ball[] ballSet, BallSetType ballSetType ){
        ImageView basketImage = ( ImageView ) (( LinearLayout) findViewById( basketId ) ).getChildAt( 0 );
        if ( GlobalManager.getStoredBallSet().get( basketName ) == null ){
            StoredBallSet storedBallSet = new StoredBallSet();
            storedBallSet.setBallSets( ballSet );
            storedBallSet.setBallSetType( ballSetType );
            storedBallSet.setStoredDate( AppUtils.formatDate( AppConstants.FULL_DATE_FORMAT, new Date( ) ) );
            storedBallSet.setBallSetName( basketName );
            storedBallSet.setDrawCount( AppUtils.getDraws( GlobalManager.getCachedResponseData().getTotalDraw() ) );
            GlobalManager.getStoredBallSet().put( basketName, storedBallSet );
            basketImage.setImageDrawable( SFFSApplication.getAppContext().getResources()
                                                .getDrawable( R.drawable.ic_basket_shoko_18dp ) );
        } else {
            GlobalManager.getStoredBallSet().remove( basketName );
            basketImage.setImageDrawable( SFFSApplication.getAppContext().getResources()
                                                .getDrawable( R.drawable.ic_basket_grey600_18dp ) );
        }
    }

    private void restoreToDefaultBasketImage(){
        Drawable defaultBasketImg = SFFSApplication.getAppContext().getResources()
                .getDrawable( R.drawable.ic_basket_grey600_18dp );

        (( ImageView ) (( LinearLayout) findViewById( R.id.addToBasketBiggerAllId ) )
                                            .getChildAt( 0 )).setImageDrawable( defaultBasketImg );
        (( ImageView ) (( LinearLayout) findViewById( R.id.addToBasketLessAllId ) )
                .getChildAt( 0 )).setImageDrawable( defaultBasketImg );
        (( ImageView ) (( LinearLayout) findViewById( R.id.addToBasketMiddleAllId ) )
                .getChildAt( 0 )).setImageDrawable( defaultBasketImg );
    }

    private void setBallSetButton(int buttonId, boolean btnEnable ){
        TextView btn = findViewById( buttonId );
        Resources res = SFFSApplication.getAppContext().getResources();
        if ( btnEnable ){
            btn.setBackground( res.getDrawable( R.drawable.border_btn_orange ) );
            btn.setTextColor( res.getColor( R.color.ballOrange ) );
            btn.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_orange_18dp, 0, 0, 0 );
        } else {
            btn.setBackground( res.getDrawable( R.drawable.border_btn_gray ) );
            btn.setTextColor( res.getColor( R.color.ballGray) );
            btn.setCompoundDrawablesWithIntrinsicBounds( 0, 0, 0, 0 );
        }
    }

    public void populateDrawsPlane() {
        mBallsInfo = GlobalManager.getCachedResponseData().getBallsInfo();
        mBallTable = findViewById( R.id.fiveNineTableId );
        mBallTable.clearTable();
        mBallTable.setBallsInfo( mBallsInfo );
        mTitle.setText( AppUtils.getDraws( GlobalManager.getCachedResponseData().getTotalDraw() ) );

        initTextView( R.id.avgSumValueId, AppConstants.ROTONDA_BOLD ).setText( mBallsInfo.getAvgBallSumm()+"" );

        mBiggerBallSet = findViewById( R.id.biggerBallSetId );
        mBiggerBallSet.setBallSet( mBallsInfo.getMoreOften(), BallSetType.BIGGER );

        mLessBallSet = findViewById( R.id.lessBallSetId );
        mLessBallSet.setBallSet( mBallsInfo.getLessOfter(), BallSetType.LESS );

        mMiddleBallSet = findViewById( R.id.middleBallSetId );
        mMiddleBallSet.setBallSet( mBallsInfo.getMiddleOften(), BallSetType.MIDDLE );

        if ( RequestType.isDateRequest( GlobalManager.getCachedResponseData().getLastRequest() ) ){
            String fromDate = "c "+mBallsInfo.getFirstDrawDate().substring( 0, mBallsInfo.getFirstDrawDate().length()-7 );
            String toDate = "по "+mBallsInfo.getLastDrawDate().substring( 0, mBallsInfo.getLastDrawDate().length()-7 );
            mDrawRange.setTwoCellValue( fromDate,"№ " + mBallsInfo.getFirstDraw(),
                    toDate, "№ " + mBallsInfo.getLastDraw() );
        } else {
            mDrawRange.setTwoCellValue( "с № " + mBallsInfo.getFirstDraw(), mBallsInfo.getFirstDrawDate(),
                    "по № " + mBallsInfo.getLastDraw(), mBallsInfo.getLastDrawDate() );
        }

        Integer drawCount = (Integer.valueOf( mBallsInfo.getLastDraw() ) - Integer.valueOf( mBallsInfo.getFirstDraw() ) )+1 ;

        mEvenOdd.setTwoCellValue(  AppUtils.getFormatedString( Long.valueOf( mBallsInfo.getEvenOdd().getEvenCount() ) ),
                mActivity.getString( R.string.even_label ),
                AppUtils.getFormatedString( Long.valueOf( mBallsInfo.getEvenOdd().getOddCount() ) ),
                mActivity.getString( R.string.odd_label ) );

        mTotalTicket.setTwoCellValue(  AppUtils.getFormatedString( mBallsInfo.getTotalDrawInfo().getPersonCount() ),
                mActivity.getString( R.string.all_draws_label ),
                AppUtils.getFormatedString(
                        mBallsInfo.getTotalDrawInfo().getPersonCount() / drawCount ),
                mActivity.getString( R.string.per_draw_label ) );

        mTotalPaidAmount.setTwoCellValue(  AppUtils.getFormatedString( mBallsInfo.getTotalDrawInfo().getPaidAmount() ),
                mActivity.getString( R.string.all_draws_label ),
                AppUtils.getFormatedString(
                        mBallsInfo.getTotalDrawInfo().getPaidAmount() / drawCount ),
                mActivity.getString( R.string.per_draw_label ) );

        WinTable winTable = findViewById( R.id.winTableAllResultsId );
        winTable.setWinBalls( mBallsInfo.getTotalDrawInfo().getSixGuessedWin(),
                mBallsInfo.getTotalDrawInfo().getFiveGuessedWin(),
                mBallsInfo.getTotalDrawInfo().getFourGuessedWin(),
                mBallsInfo.getTotalDrawInfo().getThreeGuessedWin(),
                mBallsInfo.getTotalDrawInfo().getTwoGuessedWin() );
        initBasketImage();
    }



}
