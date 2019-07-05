package ru.sff.statistic.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ru.sff.statistic.R;
import ru.sff.statistic.component.AppHeader;
import ru.sff.statistic.fragment.AllResultsFragment;
import ru.sff.statistic.fragment.BallSetBasketFragment;
import ru.sff.statistic.fragment.DrawDetailsFragment;
import ru.sff.statistic.fragment.LotoDrawsFragment;
import ru.sff.statistic.model.LotoModel;


public class RouteActivity extends BaseActivity implements
        AllResultsFragment.OnMenuOptionSelectListener, LotoDrawsFragment.OnDrawDetailsClickListener {

    private AppHeader mHeader;
    private ImageView mBackBtn;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_route );
        initialize();
    }

    private void initialize(){
        mActivityContainer = findViewById( R.id.routeActivityLayoutId );
        mHeader = findViewById( R.id.routeHeaderId );
        mBackBtn = findViewById( R.id.routeBackBtnId );
        addReplaceFragment( AllResultsFragment.newInstance(), R.drawable.emoji_look, R.string.all_draws_label, null );
    }

    protected void addReplaceFragment( Fragment newFragment, int emojiId, int titleId, String title ) {
        if ( title == null ) {
            mHeader.setHeader( emojiId, titleId );
        } else {
            mHeader.setHeader( emojiId, title );
        }
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
        addReplaceFragment( BallSetBasketFragment.newInstance(),R.drawable.emoji_look, R.string.basket_view_title, null );
    }

    public ImageView getBackBtn() {
        return mBackBtn;
    }


    @Override
    public void onDrawDetailsClick( LotoModel lotoModel ) {
        addReplaceFragment( DrawDetailsFragment.newInstance( lotoModel ),R.drawable.emoji_look, 0,
                getResources().getString( R.string.draw_details_title )+" "+lotoModel.getDraw().toString() );
    }
}
