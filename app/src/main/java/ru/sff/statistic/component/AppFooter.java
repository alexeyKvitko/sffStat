package ru.sff.statistic.component;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;
import ru.sff.statistic.manager.GlobalManager;
import ru.sff.statistic.utils.CustomAnimation;

public class AppFooter extends LinearLayout {

    private boolean mBasketDisable;
    private boolean mDonateDisable;


    public AppFooter( Context context ) {
        super( context );
        inflate( context, R.layout.footer, this );
        init();
    }

    public AppFooter( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.footer, this );
        init();
    }

    private void init() {
        findViewById( R.id.footerShowBasketId ).setOnClickListener( ( View view ) -> {
            if ( GlobalManager.getSortedStoredBallSet().isEmpty() || mBasketDisable) {
                return;
            }
            CustomAnimation.clickAnimation( view );
            showNeatedFragmentByAction( AppConstants.FLOAT_MENU_SHOW_BASKET  );
        } );
        findViewById( R.id.footerShowDonateId ).setOnClickListener( ( View view ) -> {
            if( mDonateDisable ){
                return;
            }
            CustomAnimation.clickAnimation( view );
            showNeatedFragmentByAction( AppConstants.FLOAT_MENU_SHOW_DONATE  );
        } );
    }

    private void showNeatedFragmentByAction( String action) {
        Intent intent = new Intent( AppConstants.ROUTE_ACTION_MESSAGE );
        intent.putExtra( AppConstants.ROUTE_ACTION_TYPE, AppConstants.FLOAT_MENU_ACTION );
        intent.putExtra( AppConstants.FLOAT_MENU_ACTION_TYPE, action );
        SFFSApplication.getAppContext().sendBroadcast( intent );
    }



    public boolean isBasketDisable() {
        return mBasketDisable;
    }

    public void setBasketDisabled( boolean basketDisable ) {
        this.mBasketDisable = basketDisable;
        Drawable drawable = basketDisable ? SFFSApplication.getAppContext().getResources().getDrawable( R.drawable.ic_basket_grey600_18dp )
                : SFFSApplication.getAppContext().getResources().getDrawable( R.drawable.ic_basket_shoko_menu_18dp );
        (( ImageView) findViewById( R.id.footerShowBasketId )).setImageDrawable( drawable );
    }

    public boolean isDonateDisable() {
        return mDonateDisable;
    }

    public void setDonateDisable(boolean donateDisable) {
        this.mDonateDisable = donateDisable;
        Drawable drawable = donateDisable ? SFFSApplication.getAppContext().getResources().getDrawable( R.drawable.ic_wallet_giftcard_gray_18dp )
                : SFFSApplication.getAppContext().getResources().getDrawable( R.drawable.ic_wallet_giftcard_shoko_18dp );
        (( ImageView) findViewById( R.id.footerShowDonateId )).setImageDrawable( drawable );

    }
}
