package ru.madest.statistic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import ru.madest.statistic.AppConstants;
import ru.madest.statistic.R;
import ru.madest.statistic.manager.GlobalManager;
import ru.madest.statistic.modal.ModalMessage;
import ru.madest.statistic.model.ApiResponse;
import ru.madest.statistic.model.BootstrapModel;
import ru.madest.statistic.model.PreferenceBasket;
import ru.madest.statistic.rest.RestController;
import ru.madest.statistic.utils.AppPreferences;
import ru.madest.statistic.utils.AppUtils;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    private static int SPLASH_TIME_OUT = 2000;

    private static final int UNIQUE_PERMISSION_CODE = 100;

    private boolean mPermissionGranted;

    private Activity mThis;


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );
        initialize();
    }

    private void checkPermissions( int code ) {
        mPermissionGranted = false;
        String[] permissions_required = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE };
        List notGrantedPermissions = new ArrayList<>();
        for ( String permission : permissions_required ) {
            if ( ActivityCompat.checkSelfPermission( getApplicationContext(), permission ) != PackageManager.PERMISSION_GRANTED ) {
                notGrantedPermissions.add( permission );
            }
        }
        if ( notGrantedPermissions.size() > 0 ) {
            String[] permissions = new String[ notGrantedPermissions.size() ];
            notGrantedPermissions.toArray( permissions );
            ActivityCompat.requestPermissions( this, permissions, code );
        } else {
            initialize();
        }
    }

    @Override
    public void onRequestPermissionsResult( int requestCode, String permissions[], int[] grantResults ) {
        if ( requestCode == UNIQUE_PERMISSION_CODE ) {
            boolean ok = true;
            for ( int i = 0; i < grantResults.length; ++i ) {
                ok = ok && ( grantResults[ i ] == PackageManager.PERMISSION_GRANTED );
            }
            if ( ok ) {
                initialize();
            } else {
                Toast.makeText( this, "Error: required permissions not granted!", Toast.LENGTH_SHORT ).show();
                finish();
            }
        }
    }

    private void initialize() {
        mPermissionGranted = true;
        mThis = this;
        ( ( TextView ) findViewById( R.id.sixDigitId ) ).setTypeface( AppConstants.ROTONDA_BOLD );
        ( ( TextView ) findViewById( R.id.fourFiveDigitId ) ).setTypeface( AppConstants.ROTONDA_BOLD );
        ( ( TextView ) findViewById( R.id.goslotoId ) ).setTypeface( AppConstants.ROBOTO_CONDENCED );

        if ( !AppUtils.isNetworkAvailable() ) {
            finishActivity( "Отсутствует интернет соединение" );
        } else {
            new FeetchBootstrapData().execute();
        }
    }

    private void finishActivity( String toastMsg ) {
        Toast.makeText( this, toastMsg, Toast.LENGTH_LONG ).show();
        new Handler().postDelayed( () -> {
            finish();
        }, SPLASH_TIME_OUT );
    }

    public ConstraintLayout getActivityContainer(){
      return findViewById( R.id.splashActivityContainerId );
    }

    private void startMainActivity() {
        GlobalManager.getInstance().initialize();
        Object basket = AppPreferences.getObjectPreference( AppConstants.BASKET_PREF, PreferenceBasket.class );
        if( basket != null ){
            GlobalManager.setStoredBallSet( ((PreferenceBasket) basket).getSharedPreferenceBasket() );
        }
        Intent intent = new Intent( SplashActivity.this, MainActivity.class );
        intent.addFlags( Intent.FLAG_ACTIVITY_NO_ANIMATION );
        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity( intent );
        overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
        finish();
    }

    @Override
    public void onBackPressed() {
        if( GlobalManager.isBackendBusy() ){
            return;
        }
        super.onBackPressed();
    }

    private class FeetchBootstrapData extends AsyncTask< Void, Void, String > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground( Void... args ) {
            String result = null;
            GlobalManager.setBackendBusy( true );
            try {
                Call< ApiResponse< BootstrapModel > > resultCall = RestController
                        .getApi().getBootstrapModel( AppPreferences.getUniqueId() );
                Response< ApiResponse< BootstrapModel > > resultResponse = resultCall.execute();
                if ( resultResponse.body() != null ) {
                    if ( resultResponse.body().getStatus() == 200 ) {
                        GlobalManager.setBootstrapModel( resultResponse.body().getResult() );
                        if ( resultResponse.body().getResult().isUserBlocked() ){
                            result = "Приложение заблокировано автором";
                        }
                    } else {
                        result = resultResponse.body().getMessage();
                    }
                } else {
                    result = getResources().getString( R.string.internal_error );
                }
            } catch ( Exception e ) {
                result = getResources().getString( R.string.internet_error );
                Log.i( TAG, e.getMessage() );
                e.printStackTrace();
            }
            return result;
        }



        @Override
        protected void onPostExecute( String result ) {
            super.onPostExecute( result );
            GlobalManager.setBackendBusy( false );
            if ( result != null ) {
                ModalMessage.show( mThis, "Сообщение", new String[]{ result } );
                ( new Handler() ).postDelayed( () -> {
                    finish();
                }, 2000 );
                return;
            }
            startMainActivity();
        }
    }


}
