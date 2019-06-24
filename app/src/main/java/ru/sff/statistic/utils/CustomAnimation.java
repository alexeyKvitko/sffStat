package ru.sff.statistic.utils;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;

import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;

public abstract class CustomAnimation {

    public static void clickAnimation( final View view ) {
        if ( Build.VERSION.SDK_INT >= 26 ) {
            ( ( Vibrator ) SFFSApplication.getAppContext().getSystemService( Context.VIBRATOR_SERVICE ) )
                    .vibrate( VibrationEffect.createOneShot( 50, 10 ) );
        } else {
            ( ( Vibrator ) SFFSApplication.getAppContext().getSystemService( Context.VIBRATOR_SERVICE ) ).vibrate( 50 );
        }
        Animation bounce = android.view.animation.AnimationUtils.loadAnimation( SFFSApplication.getAppContext(), R.anim.short_bounce );
        view.startAnimation( bounce );
    }

    public static void btnClickAnimation( final View view ) {
        if ( Build.VERSION.SDK_INT >= 26 ) {
            ( ( Vibrator ) SFFSApplication.getAppContext().getSystemService( Context.VIBRATOR_SERVICE ) )
                    .vibrate( VibrationEffect.createOneShot( 50, 10 ) );
        } else {
            ( ( Vibrator ) SFFSApplication.getAppContext().getSystemService( Context.VIBRATOR_SERVICE ) ).vibrate( 50 );
        }
        Animation rotation = android.view.animation.AnimationUtils.loadAnimation( SFFSApplication.getAppContext(), R.anim.icon_rotation );
        view.startAnimation( rotation );
    }

    public static void bounceAnimation( final View view ) {
        Animation bounce = android.view.animation.AnimationUtils.loadAnimation( SFFSApplication.getAppContext(), R.anim.bounce );
        view.startAnimation( bounce );
        if ( Build.VERSION.SDK_INT >= 26 ) {
            ( ( Vibrator ) SFFSApplication.getAppContext().getSystemService( Context.VIBRATOR_SERVICE ) )
                    .vibrate( VibrationEffect.createOneShot( 50, 10 ) );
        } else {
            ( ( Vibrator ) SFFSApplication.getAppContext().getSystemService( Context.VIBRATOR_SERVICE ) ).vibrate( 50 );
        }
    }
    
}
