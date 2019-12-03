package ru.madest.statistic.component;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import ru.madest.statistic.AppConstants;
import ru.madest.statistic.R;
import ru.madest.statistic.SFFSApplication;
import ru.madest.statistic.manager.GlobalManager;
import ru.madest.statistic.utils.AppUtils;
import ru.madest.statistic.utils.CustomAnimation;

public class AppFooter extends LinearLayout {

    private static final int DEFAULT_WIDTH = -( int ) AppUtils.convertDpToPixel( 164 );
    private static final int DEFAULT_CLOSE = ( int ) AppUtils.convertDpToPixel( -82 );

    private boolean mBasketDisable;
    private boolean mFooterMenuOpen;

    private boolean mSupportDisable;

    private Handler mCloseMenuHandler;
    private Runnable mMenuRunable;

    public AppFooter ( Context context ) {
        super( context );
        inflate( context, R.layout.footer, this );
        init();
    }

    public AppFooter ( Context context, @Nullable AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.footer, this );
        init();
    }

    private void init () {
        mFooterMenuOpen = false;

        findViewById( R.id.footerShowBasketId ).setOnClickListener( ( View view ) -> {
            if ( GlobalManager.getSortedStoredBallSet().isEmpty() || mBasketDisable ) {
                return;
            }
            CustomAnimation.clickAnimation( view );
            showNeatedFragmentByAction( AppConstants.FLOAT_MENU_SHOW_BASKET );
        } );
        findViewById( R.id.footerMenuId ).setOnClickListener( ( View view ) -> {
            CustomAnimation.clickAnimation( view );
            showFooterMenu();
        } );
        mMenuRunable = () -> {
            {
                showFooterMenu();
                mCloseMenuHandler = null;
            }
        };
        findViewById( R.id.footerShowSupportId ).setOnClickListener( ( View view ) -> {
            if ( mSupportDisable ) {
                return;
            }
            showNeatedFragmentByAction( AppConstants.FLOAT_MENU_SHOW_SUPPORT );
        } );

    }

    private void showNeatedFragmentByAction ( String action ) {
        Intent intent = new Intent( AppConstants.ROUTE_ACTION_MESSAGE );
        intent.putExtra( AppConstants.ROUTE_ACTION_TYPE, AppConstants.FLOAT_MENU_ACTION );
        intent.putExtra( AppConstants.FLOAT_MENU_ACTION_TYPE, action );
        SFFSApplication.getAppContext().sendBroadcast( intent );
    }

    private void showFooterMenu () {
        mFooterMenuOpen = !mFooterMenuOpen;
        int start = mFooterMenuOpen ? DEFAULT_WIDTH : DEFAULT_CLOSE;
        int end = mFooterMenuOpen ? DEFAULT_CLOSE : DEFAULT_WIDTH;
        Drawable menuIcon = mFooterMenuOpen ? SFFSApplication.getAppContext().getResources().getDrawable( R.drawable.ic_menu_gray_18dp ) :
                SFFSApplication.getAppContext().getResources().getDrawable( R.drawable.ic_menu_shocko_18dp );
        (( ImageView ) findViewById( R.id.footerMenuId )).setImageDrawable( menuIcon );
        animateRequestContainer( start, end );
        if ( mFooterMenuOpen && mCloseMenuHandler == null ) {
            mCloseMenuHandler = new Handler();
            mCloseMenuHandler.postDelayed( mMenuRunable, 3000 );
        } else {
            mCloseMenuHandler.removeCallbacks( mMenuRunable );
            mCloseMenuHandler = null;
        }

    }

    private void animateRequestContainer ( int start, int end ) {
        final LinearLayout menuContainer = findViewById( R.id.footerBackMenuId );
        FrameLayout.LayoutParams layoutParams = ( FrameLayout.LayoutParams ) menuContainer.getLayoutParams();
        ValueAnimator valAnimator = ValueAnimator.ofObject( new IntEvaluator(), start, end );
        valAnimator.addUpdateListener( ( ValueAnimator animator ) -> {
            int val = ( Integer ) animator.getAnimatedValue();
            layoutParams.rightMargin = val;
            menuContainer.setLayoutParams( layoutParams );
        } );
        valAnimator.setDuration( 300 );
        valAnimator.start();
    }


    public boolean isBasketDisable () {
        return mBasketDisable;
    }

    public void setBasketDisabled ( boolean basketDisable ) {
        if ( this.mBasketDisable == basketDisable ){
            return;
        }
        this.mBasketDisable = basketDisable;
        Drawable drawable = basketDisable ? SFFSApplication.getAppContext().getResources().getDrawable( R.drawable.ic_basket_grey600_18dp )
                : SFFSApplication.getAppContext().getResources().getDrawable( R.drawable.ic_basket_shoko_menu_18dp );
        (( ImageView ) findViewById( R.id.footerShowBasketId )).setImageDrawable( drawable );
    }


    public boolean isSupportDisable () {
        return mSupportDisable;
    }

    public void setSupportDisable ( boolean supportDisable ) {
        this.mSupportDisable = supportDisable;
        Drawable drawable = supportDisable ? SFFSApplication.getAppContext().getResources().getDrawable( R.drawable.ic_mail_outline_gray_18 )
                : SFFSApplication.getAppContext().getResources().getDrawable( R.drawable.ic_mail_outline_shoco_18 );
        (( ImageView ) findViewById( R.id.footerShowSupportId )).setImageDrawable( drawable );
    }

    public void switchButtonToBasket( boolean  showBasketButton ){
        findViewById( R.id.footerShowSupportId ).setVisibility( showBasketButton ? View.GONE : View.VISIBLE );
        findViewById( R.id.footerDeleteAllFromBasketId ).setVisibility( showBasketButton ? View.VISIBLE : View.GONE );
    }

    public ImageView getRemoveAllBtn(){
        return findViewById( R.id.footerDeleteAllFromBasketId );
    }
}
