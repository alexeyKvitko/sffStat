package ru.sff.statistic.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;
import ru.sff.statistic.component.AppHeader;
import ru.sff.statistic.component.SixBallSet;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.modal.ModalMessage;
import ru.sff.statistic.model.Ball;
import ru.sff.statistic.model.BallSetType;
import ru.sff.statistic.model.HeaderModel;
import ru.sff.statistic.model.LotoModel;
import ru.sff.statistic.model.PreferenceBasket;
import ru.sff.statistic.utils.AppPreferences;
import ru.sff.statistic.utils.CustomAnimation;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        initialize();
    }

    private void initialize() {
        mActivityContainer = findViewById( R.id.mainActivityLayoutId );
        AppHeader mainHeader = findViewById( R.id.mainHeaderId );
        mainHeader.setHeader( new HeaderModel( R.drawable.emoji_think,
                    SFFSApplication.getAppContext().getResources().getString( R.string.loto_6_45 ) ) );
        ( ( TextView ) findViewById( R.id.queryAllDrawsId ) ).setTypeface( AppConstants.ROBOTO_CONDENCED );
        ( ( TextView ) findViewById( R.id.queryByDrawsId ) ).setTypeface( AppConstants.ROBOTO_CONDENCED );
        ( ( TextView ) findViewById( R.id.queryByDateId ) ).setTypeface( AppConstants.ROBOTO_CONDENCED );
        ( ( TextView ) findViewById( R.id.queryByAmountId ) ).setTypeface( AppConstants.ROBOTO_CONDENCED );
        ( ( TextView ) findViewById( R.id.queryByTurnId ) ).setTypeface( AppConstants.ROBOTO_CONDENCED );
        ( ( TextView ) findViewById( R.id.queryByAnalisId ) ).setTypeface( AppConstants.ROBOTO_CONDENCED );


        findViewById( R.id.requestAllDrawId ).setOnClickListener( this );
        findViewById( R.id.requestByDrawId ).setOnClickListener( this );
        findViewById( R.id.requestByDateId ).setOnClickListener( this );
        findViewById( R.id.requestByAmountId ).setOnClickListener( this );
        findViewById( R.id.requestByTurnId ).setOnClickListener( this );
        findViewById( R.id.ballConsiderId ).setOnClickListener( this );

        initializeDonationScreen();
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
            ModalMessage.show( this, "Сообщение", new String[]{"Дайте денег!"} );
            AppPreferences.setPreference( AppConstants.DONATION_PREF, AppConstants.FAKE_ID );
        }, 5000 );
    }


    protected void startNewActivity( Class< ? > newClass ) {
        startNewActivity( newClass, null );
    }

    protected void startNewActivity( Class< ? > newClass, Map< String, String > params ) {
        Intent intent = new Intent( SFFSApplication.getAppContext(), newClass );
        intent.addFlags( Intent.FLAG_ACTIVITY_NO_ANIMATION );
        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
        if ( params != null ) {
            for ( String key : params.keySet() ) {
                intent.putExtra( key, params.get( key ) );
            }
        }
        startActivity( intent );
        overridePendingTransition( R.anim.act_fade_in, R.anim.act_fade_out );
    }

    @Override
    public void onBackPressed() {
        if ( !GlobalManager.getInstance().getStoredBallSet().isEmpty() ){
            AppPreferences.setPreference( AppConstants.BASKET_PREF, new PreferenceBasket( GlobalManager.getInstance().getStoredBallSet() ).get()  );
        } else {
            AppPreferences.removePreference( AppConstants.BASKET_PREF );
        }
        super.onBackPressed();
    }

    @Override
    public void onClick( View view ) {
        CustomAnimation.bounceAnimation( view );
        Map<String, String> params = new HashMap<>(  );
        switch ( view.getId() ) {
            case R.id.requestAllDrawId:
                params.put( AppConstants.ROUTE_ACTION, AppConstants.SHOW_ALL_DRAW_SCREEN );
                startNewActivity( RouteActivity.class, params );
                break;
            case R.id.requestByDrawId :
                params.put( AppConstants.ROUTE_ACTION, AppConstants.SHOW_BY_DRAW_SCREEN );
                startNewActivity( RouteActivity.class, params );
                break;
            case R.id.requestByDateId :
                params.put( AppConstants.ROUTE_ACTION, AppConstants.SHOW_BY_DATE_SCREEN );
                startNewActivity( RouteActivity.class, params );
                break;
            case R.id.requestByAmountId :
                params.put( AppConstants.ROUTE_ACTION, AppConstants.SHOW_BY_SUM_SCREEN );
                startNewActivity( RouteActivity.class, params );
                break;
            case R.id.requestByTurnId :
                params.put( AppConstants.ROUTE_ACTION, AppConstants.SHOW_BY_TURN_SCREEN );
                startNewActivity( RouteActivity.class, params );
                break;
            case R.id.ballConsiderId :
                params.put( AppConstants.ROUTE_ACTION, AppConstants.SHOW_BY_CONSIDER_SCREEN );
                startNewActivity( RouteActivity.class, params );
                break;
        }
    }


}
