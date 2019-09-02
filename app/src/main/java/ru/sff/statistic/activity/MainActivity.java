package ru.sff.statistic.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;
import ru.sff.statistic.component.AppHeader;
import ru.sff.statistic.model.HeaderModel;
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
