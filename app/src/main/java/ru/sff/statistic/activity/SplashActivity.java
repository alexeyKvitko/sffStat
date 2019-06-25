package ru.sff.statistic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.model.BallSetType;
import ru.sff.statistic.utils.AppUtils;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initialize();
    }

    private void initialize(){
        ( (TextView) findViewById( R.id.sixDigitId ) ).setTypeface(AppConstants.ROTONDA_BOLD );
        ( (TextView) findViewById( R.id.fourFiveDigitId ) ).setTypeface(AppConstants.ROTONDA_BOLD );
        ( (TextView) findViewById( R.id.goslotoId ) ).setTypeface(AppConstants.ROBOTO_CONDENCED );
        GlobalManager.getInstance().setSelBallType( BallSetType.BIGGER );
        if ( !AppUtils.isNetworkAvailable() ) {
            finishActivity( "Отсутствует интернет соединение" );
        } else {
            new Handler().postDelayed( () -> {
                Intent intent = new Intent( SplashActivity.this, MainActivity.class );
                intent.addFlags( Intent.FLAG_ACTIVITY_NO_ANIMATION );
                intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity( intent );
                overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
                finish();
            }, 600);
        }

    }

    private void finishActivity( String toastMsg ) {
        Toast.makeText( this, toastMsg, Toast.LENGTH_LONG ).show();
        new Handler().postDelayed( () -> {
            finish();
        }, SPLASH_TIME_OUT );
    }
}
