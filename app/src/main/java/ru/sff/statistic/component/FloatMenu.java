package ru.sff.statistic.component;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import ru.sff.statistic.AppConstants;
import ru.sff.statistic.R;
import ru.sff.statistic.SFFSApplication;
import ru.sff.statistic.manager.GlobalManager;


public class FloatMenu extends RelativeLayout {

    private FloatingActionButton mFabSelectedSet;
    private FloatingActionButton mFabCountPercent;
    private FloatingActionsMenu mFloatMenu;
    private boolean mPercentSelect;

    public FloatMenu( Context context ) {
        super( context );
        inflate( context, R.layout.float_menu, this );
    }

    public FloatMenu( Context context, AttributeSet attrs ) {
        super( context, attrs );
        inflate( context, R.layout.float_menu, this );
        init();
    }

    private void init(){
        mFloatMenu =  findViewById( R.id.floatMenuId );
        mPercentSelect = true;
        mFabSelectedSet = findViewById( R.id.fabSelectedSetId );
        mFabCountPercent = findViewById( R.id.fabCountPercentId );
        mFabCountPercent.setOnClickListener( (View view) ->{
            changeCountPercent();
        } );
        mFabSelectedSet.setOnClickListener( (View view) -> {
            sendMenuMessage( AppConstants.FLOAT_MENU_SHOW_BASKET );
        });
    }

    private void changeCountPercent(){
        mPercentSelect = !mPercentSelect;
        GlobalManager.setResultViewType( !mPercentSelect ? AppConstants.VIEW_TYPE_PERCENT
                                                        : AppConstants.VIEW_TYPE_FALLING_COUNT );
        int drawableId = R.drawable.ic_creation_shoko_18dp;
        int titleId = R.string.fab_falling_count;
        if ( mPercentSelect ){
            drawableId = R.drawable.ic_percent_shoko_18dp;
            titleId = R.string.fab_percent;
        }
        mFabCountPercent.setIconDrawable( SFFSApplication.getAppContext()
                .getResources().getDrawable( drawableId ) );
        mFabCountPercent.setTitle( SFFSApplication.getAppContext()
                .getResources().getString( titleId ) );
        sendMenuMessage( AppConstants.FLOAT_MENU_CHANGE_VIEW_TYPE );
    }



    private void sendMenuMessage(String message){
        Intent intent = new Intent( AppConstants.FLOAT_MENU_MESSAGE );
        intent.putExtra( AppConstants.FLOAT_MENU_ACTION, message );
        SFFSApplication.getAppContext().sendBroadcast( intent );
        mFloatMenu.collapse();
    }

}
