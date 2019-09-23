package ru.sff.statistic.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;
import ru.sff.statistic.component.AppFooter;
import ru.sff.statistic.component.AppHeader;
import ru.sff.statistic.fragment.AllResultsFragment;
import ru.sff.statistic.fragment.BallSetBasketFragment;
import ru.sff.statistic.fragment.ConsiderFragment;
import ru.sff.statistic.fragment.DigitInfoFragment;
import ru.sff.statistic.fragment.DonateFragment;
import ru.sff.statistic.fragment.DrawDetailsFragment;
import ru.sff.statistic.fragment.LotoDrawsFragment;
import ru.sff.statistic.fragment.StatByDateFragment;
import ru.sff.statistic.fragment.StatByDrawFragment;
import ru.sff.statistic.fragment.StatBySumFragment;
import ru.sff.statistic.fragment.TurnOverFrament;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.model.Ball;
import ru.sff.statistic.model.HeaderModel;
import ru.sff.statistic.utils.AppPreferences;


public class RouteActivity extends BaseActivity implements LotoDrawsFragment.OnDrawDetailsClickListener,
                                TurnOverFrament.OnShowDrawsClickListener, DonateFragment.OnDonateChooseListener{

    private IntentFilter mBallSelectIntentFilter = new IntentFilter( AppConstants.ROUTE_ACTION_MESSAGE );
    private RouteActionReceiver mRouteActionReceiver = new RouteActionReceiver();

    private AppHeader mHeader;
    private AppFooter mFooter;
    private ImageView mBackBtn;
    private String mRouteAction;
    private HeaderModel mPrevHeader;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_route );
        initialize();
    }

    private void initialize() {
        mRouteAction = this.getIntent().getStringExtra( AppConstants.ROUTE_ACTION );
        mMainRouteContainer= findViewById( R.id.routeActivityMainContainerId );
        mActivityContainer = findViewById( R.id.routeActivityLayoutId );
        mHeader = findViewById( R.id.routeHeaderId );
        mFooter = findViewById( R.id.routeFooterId );
        mFooter.setBasketDisabled( GlobalManager.getStoredBallSet().isEmpty() );
        mBackBtn = findViewById( R.id.routeBackBtnId );
        switch ( mRouteAction ) {
            case AppConstants.SHOW_ALL_DRAW_SCREEN:
                addReplaceFragment( AllResultsFragment.newInstance(), R.drawable.emoji_look, R.string.all_draws_label );
                break;
            case AppConstants.SHOW_BY_DRAW_SCREEN:
                addReplaceFragment( StatByDrawFragment.newInstance( AppConstants.FAKE_ID, AppConstants.FAKE_ID ), R.drawable.emoji_look, R.string.by_draws_label );
                break;
            case AppConstants.SHOW_BY_DATE_SCREEN:
                addReplaceFragment( StatByDateFragment.newInstance(), R.drawable.emoji_look, R.string.by_date_header_label );
                break;
            case AppConstants.SHOW_BY_SUM_SCREEN:
                addReplaceFragment( StatBySumFragment.newInstance(), R.drawable.emoji_look, R.string.by_date_header_sum );
                break;
            case AppConstants.SHOW_BY_TURN_SCREEN:
                addReplaceFragment( TurnOverFrament.newInstance(), R.drawable.emoji_look, R.string.turn_header );
                break;
            case AppConstants.SHOW_BY_CONSIDER_SCREEN:
                addReplaceFragment( ConsiderFragment.newInstance(), R.drawable.emoji_look, R.string.consider_header );
                break;
        }

    }

    protected void addReplaceFragment( Fragment newFragment, int emojiId, int titleId ) {
        mPrevHeader = mHeader.getHeaderModel();
        mHeader.setHeader( new HeaderModel( emojiId,
                SFFSApplication.getAppContext().getResources().getString( titleId ) ) );
        addReplaceFragment( newFragment );
    }

    protected void addReplaceFragment( Fragment newFragment, int emojiId, String title ) {
        mPrevHeader = mHeader.getHeaderModel();
        mHeader.setHeader( new HeaderModel( emojiId, title ) );
        addReplaceFragment( newFragment );
    }

    protected void addReplaceFragment( Fragment newFragment ) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations( R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out );
        if ( getSupportFragmentManager().getFragments().size() == 0 ) {
            fragmentTransaction.add( R.id.routeFragmentContainerId, newFragment );
        } else {
            fragmentTransaction.replace( R.id.routeFragmentContainerId, newFragment );
            fragmentTransaction.addToBackStack( null );
        }
        fragmentTransaction.commit();
    }


    public void ballSetBasketShow() {
        addReplaceFragment( BallSetBasketFragment.newInstance(), R.drawable.emoji_look, R.string.basket_view_title );
    }

    public boolean donateShow() {
        if( GlobalManager.isBackendBusy() ){
            return false;
        }
        getFooter().setDonateDisable( true );
        addReplaceFragment( DonateFragment.newInstance(), R.drawable.emoji_donate, "" );
        return true;
    }

    public ImageView getBackBtn() {
        return mBackBtn;
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver( mRouteActionReceiver );
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver( mRouteActionReceiver, mBallSelectIntentFilter );
        initializeDonationScreen();
    }

    @Override
    public void onDrawDetailsClick( Integer draw ) {
        addReplaceFragment( DrawDetailsFragment.newInstance( draw ), R.drawable.emoji_look,
                getResources().getString( R.string.draw_details_title ) + " " + draw.toString() );
    }

    public void showDigitInfoFragment( Ball ball ) {
        addReplaceFragment( DigitInfoFragment.newInstance( ball ) );
    }

    private void initializeDonationScreen(){
        if ( !GlobalManager.getBootstrapModel().getShowDonationsMsg() ){
            return;
        }
        int donationCount = AppPreferences.getPreference( AppConstants.DONATION_PREF, AppConstants.FAKE_ID );
        if ( donationCount < AppConstants.DONATION_TIME ){
            AppPreferences.setPreference( AppConstants.DONATION_PREF, ++donationCount );
            return;
        }
        ( new Handler() ).postDelayed( () -> {
            if ( donateShow() ) {
                AppPreferences.setPreference( AppConstants.DONATION_PREF, AppConstants.FAKE_ID );
            };
        }, 7000 );
    }

    @Override
    public void onBackPressed() {
        if( GlobalManager.isBackendBusy() ){
            return;
        }
        mHeader.setHeader( mPrevHeader );
        mHeader.setVisibility( View.VISIBLE );
        GlobalManager.setShowLastFallBallSet( false );
        mFooter.setBasketDisabled( GlobalManager.getStoredBallSet().isEmpty() );
        mFooter.setDonateDisable( false );
        super.onBackPressed();
    }

    @Override
    public void onShowDrawsClick( int startDraw, int endDraw ) {
        GlobalManager.setCachedResponseData( null );
        addReplaceFragment( StatByDrawFragment.newInstance( startDraw, endDraw ), R.drawable.emoji_look, R.string.by_draws_label );
    }

    @Override
    public void onDonateAction( int amount ) {

    }

    class RouteActionReceiver extends BroadcastReceiver {
        @Override
        public void onReceive( Context context, Intent intent ) {
            String routeAction = intent.getStringExtra( AppConstants.ROUTE_ACTION_TYPE );
            switch ( routeAction ) {
                case AppConstants.BALL_SELECT_ACTION:
                    Ball ball = ( Ball ) intent.getSerializableExtra( AppConstants.BALL_SELECT_ACTION );
                    if ( ball != null ) {
                        showDigitInfoFragment( ball );
                    }
                    break;
                case AppConstants.DRAW_SELECT_ACTION:
                    Integer draw = intent.getIntExtra( AppConstants.DRAW_SELECT_ACTION, AppConstants.FAKE_ID );
                    if ( AppConstants.FAKE_ID != draw ) {
                        mHeader.setVisibility( View.VISIBLE );
                        onDrawDetailsClick( draw );
                    }
                    break;
                case AppConstants.FLOAT_MENU_ACTION:
                    String actionType = intent.getStringExtra( AppConstants.FLOAT_MENU_ACTION_TYPE );
                    if ( AppConstants.FLOAT_MENU_SHOW_BASKET.equals( actionType ) ){
                        ballSetBasketShow();
                    } else if ( AppConstants.FLOAT_MENU_SHOW_DONATE.equals( actionType ) ){
                        donateShow();
                    }
                break;
            }
        }
    }

    public void hidePanelWihMenu() {
        mHeader.setVisibility( View.GONE );
    }

    public AppHeader getAppHeader() {
        return mHeader;
    }

    public AppFooter getFooter() {
        return mFooter;
    }
}
