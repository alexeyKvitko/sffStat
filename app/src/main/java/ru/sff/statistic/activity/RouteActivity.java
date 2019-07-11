package ru.sff.statistic.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.component.AppHeader;
import ru.sff.statistic.component.FloatMenu;
import ru.sff.statistic.fragment.AllResultsFragment;
import ru.sff.statistic.fragment.BallSetBasketFragment;
import ru.sff.statistic.fragment.DigitInfoFragment;
import ru.sff.statistic.fragment.DrawDetailsFragment;
import ru.sff.statistic.fragment.LotoDrawsFragment;
import ru.sff.statistic.model.Ball;
import ru.sff.statistic.model.LotoModel;


public class RouteActivity extends BaseActivity implements
        AllResultsFragment.OnMenuOptionSelectListener, LotoDrawsFragment.OnDrawDetailsClickListener {

    private IntentFilter mBallSelectIntentFilter = new IntentFilter( AppConstants.BALL_SELECT_MESSAGE );
    private BallSelectReceiver mBallSelectReceiver = new BallSelectReceiver();

    private AppHeader mHeader;
    private ImageView mBackBtn;
    private FloatMenu mFloatMenu;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_route );
        initialize();
    }

    private void initialize(){
        mActivityContainer = findViewById( R.id.routeActivityLayoutId );
        mHeader = findViewById( R.id.routeHeaderId );
        mFloatMenu = findViewById( R.id.routeFloatMenuId );
        mBackBtn = findViewById( R.id.routeBackBtnId );
        addReplaceFragment( AllResultsFragment.newInstance(), R.drawable.emoji_look, R.string.all_draws_label );
    }

    protected void addReplaceFragment( Fragment newFragment, int emojiId, int titleId ) {
        mHeader.setHeader( emojiId, titleId );
        addReplaceFragment( newFragment );
    }

    protected void addReplaceFragment( Fragment newFragment, int emojiId, String title ) {
        mHeader.setHeader( emojiId, title );
        addReplaceFragment( newFragment );
    }

    protected void addReplaceFragment( Fragment newFragment  ) {
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

    @Override
    public void onBallSetBasketShow() {
        addReplaceFragment( BallSetBasketFragment.newInstance(),R.drawable.emoji_look, R.string.basket_view_title );
    }

    public ImageView getBackBtn() {
        return mBackBtn;
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver( mBallSelectReceiver );
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver( mBallSelectReceiver, mBallSelectIntentFilter );
    }

    @Override
    public void onDrawDetailsClick( LotoModel lotoModel ) {
        addReplaceFragment( DrawDetailsFragment.newInstance( lotoModel ),R.drawable.emoji_look,
                getResources().getString( R.string.draw_details_title )+" "+lotoModel.getDraw().toString() );
    }

    public void showDigitInfoFragment( Ball ball ){
        mHeader.setVisibility( View.GONE );
        mFloatMenu.setVisibility( View.GONE );
        addReplaceFragment( DigitInfoFragment.newInstance( ball ) );
    }

    @Override
    public void onBackPressed() {
        mHeader.setVisibility( View.VISIBLE );
        mFloatMenu.setVisibility( View.VISIBLE );
        super.onBackPressed();
    }

    class BallSelectReceiver extends BroadcastReceiver {
        @Override
        public void onReceive( Context context, Intent intent ) {
            Ball ball = ( Ball ) intent.getSerializableExtra( AppConstants.BALL_SELECT_ACTION );
                if ( ball != null ){
                    showDigitInfoFragment( ball );
                }
            }
        }


}
